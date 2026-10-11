package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.InitialData
import com.example.data.model.*
import com.example.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ScreenRoute(val titleBn: String) {
    SPLASH("স্প্ল্যাশ"),
    HOME("খিলপাড়া পুলিশ তদন্ত কেন্দ্র"),
    POLICE_OFFICERS("পুলিশ তদন্ত কেন্দ্র"),
    UNION_DETAILS("ইউনিয়ন পরিষদ তথ্যাবলি"),
    SCHOOL_COLLEGE("সরকারি স্কুল ও কলেজ ডিরেক্টরি"),
    CNG_STATION("সিএনজি স্টেশন ও চালক ডিরেক্টরি"),
    IMPORTANT_PERSONS("গুরুত্বপূর্ণ ব্যক্তিবর্গ"),
    SEARCH("অনুসন্ধান மையம்"),
    FAVORITES("প্রিয় পরিচিতিসমূহ"),
    CHAT("সিটিজেন হটলাইন ও চ্যাট"),
    NOTICES("পুলিশ বিজ্ঞপ্তি ও আইন-শৃঙ্খলা"),
    ADMIN_PANEL("এডমিন প্যানেল"),
    SETTINGS("অ্যাপ সেটিংস"),
    DEVELOPER_INFO("ডেভেলপার তথ্য"),
    ABOUT_APP("অ্যাপ সম্পর্কে"),
    PRIVACY_POLICY("গোপনীয়তা নীতি"),
    WEB_IMPORT("ওয়েব ডাটা ইমপোর্ট")
}

data class SearchResultItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val mobile: String,
    val locationOrWard: String = "",
    val photoUrl: String = ""
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    val repository = AppRepository(db.appDao())

    // Settings & State
    private val _currentRoute = MutableStateFlow(ScreenRoute.HOME)
    val currentRoute: StateFlow<ScreenRoute> = _currentRoute.asStateFlow()

    private val _language = MutableStateFlow("bn") // "bn" or "en"
    val language: StateFlow<String> = _language.asStateFlow()

    val isBengali: StateFlow<Boolean> = _language.map { it == "bn" }.stateIn(viewModelScope, SharingStarted.Lazily, true)

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _selectedUnionId = MutableStateFlow("khilpara")
    val selectedUnionId: StateFlow<String> = _selectedUnionId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _adminLoggedIn = MutableStateFlow(false)
    val adminLoggedIn: StateFlow<Boolean> = _adminLoggedIn.asStateFlow()

    private val _adminPassword = MutableStateFlow("admin123")
    val adminPassword: StateFlow<String> = _adminPassword.asStateFlow()

    // Data Flows
    val unions = repository.unions.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.unions)
    val schoolsColleges = repository.schoolsColleges.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.schoolsColleges)
    val cngStations = repository.cngStations.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.cngStations)
    val importantPersons = repository.importantPersons.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.importantPersons)
    val policeOfficers = repository.policeOfficers.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.policeOfficers)
    val notices = repository.notices.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.notices)
    val chatMessages = repository.chatMessages.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val favorites = repository.favorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentChairman = _selectedUnionId.flatMapLatest { unionId ->
        repository.getChairman(unionId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.chairmen.firstOrNull { it.unionId == "khilpara" })

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentMembers = _selectedUnionId.flatMapLatest { unionId ->
        repository.getMembers(unionId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.members.filter { it.unionId == "khilpara" })

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentVillagePolice = _selectedUnionId.flatMapLatest { unionId ->
        repository.getVillagePolice(unionId)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.villagePolice.filter { it.unionId == "khilpara" })

    val allMembers = repository.allMembers.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.members)
    val allVillagePolice = repository.allVillagePolice.stateIn(viewModelScope, SharingStarted.Eagerly, com.example.data.local.InitialData.villagePolice)

    // Search Results Engine
    val searchResults: StateFlow<List<SearchResultItem>> = combine(
        _searchQuery,
        policeOfficers,
        schoolsColleges,
        cngStations,
        combine(importantPersons, allMembers, allVillagePolice) { p, m, vp -> Triple(p, m, vp) }
    ) { query, officers, schools, cngs, (persons, members, policeList) ->
        if (query.trim().isEmpty()) return@combine emptyList()
        val q = query.trim().lowercase()
        val list = mutableListOf<SearchResultItem>()

        // Search Police Officers (Investigation Center)
        officers.filter {
            it.name.lowercase().contains(q) ||
            it.designation.lowercase().contains(q) ||
            it.rank.lowercase().contains(q) ||
            it.dutyArea.lowercase().contains(q) ||
            it.mobile.contains(q)
        }.forEach {
            list.add(SearchResultItem("officer_${it.id}", it.name, "${it.designation} (${it.dutyArea})", "পুলিশ কর্মকর্তা", it.mobile, it.dutyArea, it.photoUrl))
        }

        // Search Members
        members.filter {
            it.name.lowercase().contains(q) ||
            it.villages.lowercase().contains(q) ||
            it.mobile.contains(q) ||
            "ওয়ার্ড ${it.wardNumber}".contains(q)
        }.forEach {
            list.add(SearchResultItem("member_${it.id}", it.name, "ওয়ার্ড নং ${it.wardNumber} (${it.villages})", "ইউনিয়ন মেম্বার", it.mobile, "ওয়ার্ড ${it.wardNumber}", it.photoUrl))
        }

        // Search Police
        policeList.filter {
            it.name.lowercase().contains(q) ||
            it.responsibleVillages.lowercase().contains(q) ||
            it.mobile.contains(q)
        }.forEach {
            list.add(SearchResultItem("police_${it.id}", it.name, "দায়িত্বপ্রাপ্ত গ্রাম: ${it.responsibleVillages}", "গ্রাম পুলিশ / চৌকিদার", it.mobile, it.responsibleVillages, it.photoUrl))
        }

        // Search Schools
        schools.filter {
            it.name.lowercase().contains(q) ||
            it.headTeacher.lowercase().contains(q) ||
            it.mobile.contains(q)
        }.forEach {
            list.add(SearchResultItem("school_${it.id}", it.name, "${it.designation}: ${it.headTeacher}", it.type, it.mobile, it.email, it.logoUrl))
        }

        // Search CNG
        cngs.filter {
            it.stationName.lowercase().contains(q) ||
            it.standName.lowercase().contains(q) ||
            it.linemanName.lowercase().contains(q) ||
            it.mobile.contains(q)
        }.forEach {
            list.add(SearchResultItem("cng_${it.id}", it.stationName, "লাইনম্যান: ${it.linemanName} (${it.standName})", "CNG স্টেশন", it.mobile, it.location))
        }

        // Search Important Persons
        persons.filter {
            it.name.lowercase().contains(q) ||
            it.category.lowercase().contains(q) ||
            it.address.lowercase().contains(q) ||
            it.mobile.contains(q)
        }.forEach {
            list.add(SearchResultItem("person_${it.id}", it.name, "${it.category} - ${it.designation}", "গুরুত্বপূর্ণ ব্যক্তি", it.mobile, it.address, it.photoUrl))
        }

        list
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Navigation & Actions
    fun navigateTo(route: ScreenRoute) {
        _currentRoute.value = route
    }

    fun selectUnion(unionId: String) {
        _selectedUnionId.value = unionId
        _currentRoute.value = ScreenRoute.UNION_DETAILS
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleLanguage() {
        _language.value = if (_language.value == "bn") "en" else "bn"
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(dark: Boolean) {
        _isDarkMode.value = dark
    }

    fun resetInitialData() {
        viewModelScope.launch {
            repository.resetDatabaseToInitialState()
        }
    }

    fun toggleFavorite(id: String, name: String, designation: String, mobile: String, category: String, photoUrl: String = "") {
        viewModelScope.launch {
            repository.toggleFavorite(id, name, designation, mobile, category, photoUrl)
        }
    }

    fun getChairmanFlow(unionId: String): Flow<ChairmanEntity?> {
        return repository.getChairman(unionId)
    }

    // Admin Authentication
    fun loginAdmin(pass: String): Boolean {
        return if (pass == _adminPassword.value) {
            _adminLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun logoutAdmin() {
        _adminLoggedIn.value = false
    }

    fun changeAdminPassword(newPass: String) {
        if (newPass.isNotBlank()) {
            _adminPassword.value = newPass
        }
    }

    fun resetAdminPassword() {
        _adminPassword.value = "admin123"
    }


    // Admin CRUD Operations
    fun updateChairman(unionId: String, name: String, photoUrl: String, mobile: String, email: String, officeAddress: String) {
        viewModelScope.launch {
            val chairmanId = "chairman_$unionId"
            repository.addOrUpdateChairman(
                ChairmanEntity(
                    id = chairmanId,
                    unionId = unionId,
                    name = name,
                    photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300" },
                    mobile = mobile,
                    email = email,
                    officeAddress = officeAddress,
                    mapsUrl = ""
                )
            )
        }
    }

    val hotlineNumbers: StateFlow<HotlineNumbersEntity?> = repository.hotlineNumbers.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        InitialData.defaultHotlineNumbers
    )

    fun updateHotlineNumbers(title1: String, number1: String, title2: String, number2: String, title3: String, number3: String) {
        viewModelScope.launch {
            repository.updateHotlineNumbers(
                HotlineNumbersEntity(
                    id = "hotlines",
                    title1 = title1,
                    number1 = number1,
                    title2 = title2,
                    number2 = number2,
                    title3 = title3,
                    number3 = number3
                )
            )
        }
    }

    fun addMember(unionId: String, name: String, ward: Int, villages: String, mobile: String, photoUrl: String = "", id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdateMember(
                UnionMemberEntity(
                    id = id,
                    unionId = unionId,
                    name = name,
                    photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200" },
                    wardNumber = ward,
                    villages = villages,
                    mobile = mobile
                )
            )
        }
    }

    fun deleteMember(id: Long) {
        viewModelScope.launch { repository.deleteMember(id) }
    }

    fun addVillagePolice(unionId: String, name: String, villages: String, mobile: String, photoUrl: String = "", id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdateVillagePolice(
                VillagePoliceEntity(
                    id = id,
                    unionId = unionId,
                    name = name,
                    photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200" },
                    responsibleVillages = villages,
                    mobile = mobile
                )
            )
        }
    }

    fun deleteVillagePolice(id: Long) {
        viewModelScope.launch { repository.deleteVillagePolice(id) }
    }

    fun addSchool(name: String, headTeacher: String, designation: String, mobile: String, email: String, type: String, logoUrl: String = "", id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdateSchool(
                SchoolCollegeEntity(
                    id = id,
                    name = name,
                    logoUrl = logoUrl.ifBlank { "https://images.unsplash.com/photo-1580582932707-520aed937b7b?w=300" },
                    headTeacher = headTeacher,
                    designation = designation,
                    mobile = mobile,
                    email = email,
                    mapsUrl = "",
                    type = type
                )
            )
        }
    }

    fun deleteSchool(id: Long) {
        viewModelScope.launch { repository.deleteSchool(id) }
    }

    fun addCNGStation(stationName: String, standName: String, linemanName: String, mobile: String, location: String, id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdateCNGStation(
                CNGStationEntity(id = id, stationName = stationName, standName = standName, linemanName = linemanName, mobile = mobile, location = location)
            )
        }
    }

    fun deleteCNGStation(id: Long) {
        viewModelScope.launch { repository.deleteCNGStation(id) }
    }

    fun addImportantPerson(category: String, name: String, designation: String, mobile: String, address: String, photoUrl: String = "", id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdateImportantPerson(
                ImportantPersonEntity(
                    id = id,
                    category = category,
                    name = name,
                    designation = designation,
                    mobile = mobile,
                    address = address,
                    photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200" }
                )
            )
        }
    }

    fun deleteImportantPerson(id: Long) {
        viewModelScope.launch { repository.deleteImportantPerson(id) }
    }

    fun addPoliceOfficer(name: String, designation: String, rank: String, mobile: String, bpNumber: String = "", photoUrl: String = "", dutyArea: String = "", id: Long = 0L) {
        viewModelScope.launch {
            repository.addOrUpdatePoliceOfficer(
                PoliceOfficerEntity(
                    id = id,
                    name = name,
                    designation = designation,
                    rank = rank.ifBlank { "উপ-পরিদর্শক (এসআই)" },
                    mobile = mobile,
                    bpNumber = bpNumber,
                    photoUrl = photoUrl.ifBlank { "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200" },
                    dutyArea = dutyArea.ifBlank { "খিলপাড়া পুলিশ তদন্ত কেন্দ্র" }
                )
            )
        }
    }

    fun deletePoliceOfficer(id: Long) {
        viewModelScope.launch { repository.deletePoliceOfficer(id) }
    }

    fun addNotice(title: String, category: String, content: String, date: String, isEmergency: Boolean, id: Long = 0L) {
        viewModelScope.launch {
            repository.addNotice(NoticeEntity(id = id, title = title, category = category, content = content, date = date, isEmergency = isEmergency))
        }
    }

    fun deleteNotice(id: Long) {
        viewModelScope.launch { repository.deleteNotice(id) }
    }

    fun sendChatMessage(text: String, senderName: String = "নাগরিক", isEmergency: Boolean = false) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendChatMessage(
                ChatMessageEntity(senderName = senderName, senderRole = if (_adminLoggedIn.value) "এডমিন" else "নাগরিক", messageText = text, isEmergency = isEmergency)
            )
        }
    }

    fun importWebData(jsonString: String, onComplete: (Int) -> Unit) {
        viewModelScope.launch {
            val count = repository.importDataFromJson(jsonString)
            onComplete(count)
        }
    }

    fun syncFromRemoteUrl(url: String, onResult: (Boolean, String) -> Unit) {
        if (url.isBlank()) {
            onResult(false, "ইউআরএল (URL) দিন")
            return
        }
        viewModelScope.launch {
            try {
                val count = repository.syncFromRemoteUrl(url)
                if (count > 0) {
                    onResult(true, "$count টি আইটেম সফলভাবে অনলাইন থেকে সিঙ্ক করা হয়েছে!")
                } else {
                    onResult(false, "কোন নতুন ডাটা পাওয়া যায়নি বা ফরম্যাট সঠিক নয়")
                }
            } catch (e: Exception) {
                onResult(false, "অনলাইন সিঙ্ক ব্যর্থ: ${e.localizedMessage}")
            }
        }
    }

    val developerInfo: StateFlow<DeveloperInfoEntity?> = repository.getDeveloperInfo().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        InitialData.defaultDeveloperInfo
    )

    fun updateDeveloperInfo(
        name: String,
        title: String,
        photoUrl: String,
        email: String,
        phone: String,
        bio: String,
        appIconUrl: String,
        appName: String
    ) {
        viewModelScope.launch {
            repository.addOrUpdateDeveloperInfo(
                DeveloperInfoEntity(
                    id = "dev_info",
                    name = name,
                    title = title,
                    photoUrl = photoUrl,
                    email = email,
                    phone = phone,
                    bio = bio,
                    appIconUrl = appIconUrl,
                    appName = appName
                )
            )
        }
    }

    fun exportDatabase(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportDatabaseAsJson()
            onResult(json)
        }
    }
}
