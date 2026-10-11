package com.example.repository

import com.example.data.local.AppDao
import com.example.data.local.InitialData
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class AppRepository(private val dao: AppDao) {

    // Seed database if empty or re-synchronize
    suspend fun resetDatabaseToInitialState() {
        dao.deleteAllMembers()
        dao.deleteAllVillagePolice()
        dao.deleteAllChairmen()
        dao.deleteAllUnions()

        dao.insertUnions(InitialData.unions)
        dao.insertChairmen(InitialData.chairmen)
        dao.insertMembers(InitialData.members)
        dao.insertVillagePolice(InitialData.villagePolice)
        dao.insertSchoolsColleges(InitialData.schoolsColleges)
        dao.insertCNGStations(InitialData.cngStations)
        dao.insertImportantPersons(InitialData.importantPersons)
        dao.deleteAllPoliceOfficers()
        dao.insertPoliceOfficers(InitialData.policeOfficers)
        dao.insertNotices(InitialData.notices)
        InitialData.chatMessages.forEach { dao.insertChatMessage(it) }
        dao.insertOrUpdateDeveloperInfo(InitialData.defaultDeveloperInfo)
        dao.insertOrUpdateHotlineNumbers(InitialData.defaultHotlineNumbers)
    }

    init {
        // Seed database ONLY if empty on first install; persist user changes on subsequent launches
        CoroutineScope(Dispatchers.IO).launch {
            if (dao.getAllUnions().first().isEmpty()) {
                resetDatabaseToInitialState()
            } else {
                if (dao.getAllPoliceOfficers().first().isEmpty()) {
                    dao.insertPoliceOfficers(InitialData.policeOfficers)
                }
                if (dao.getDeveloperInfo().first() == null) {
                    dao.insertOrUpdateDeveloperInfo(InitialData.defaultDeveloperInfo)
                }
                if (dao.getHotlineNumbers().first() == null) {
                    dao.insertOrUpdateHotlineNumbers(InitialData.defaultHotlineNumbers)
                }
            }
        }
    }

    suspend fun addOrUpdateChairman(chairman: ChairmanEntity) {
        dao.insertChairman(chairman)
    }

    // Unions & Details
    val unions: Flow<List<UnionEntity>> = dao.getAllUnions()

    fun getChairman(unionId: String): Flow<ChairmanEntity?> = dao.getChairmanByUnion(unionId)
    fun getMembers(unionId: String): Flow<List<UnionMemberEntity>> = dao.getMembersByUnion(unionId)
    val allMembers: Flow<List<UnionMemberEntity>> = dao.getAllMembers()
    fun getVillagePolice(unionId: String): Flow<List<VillagePoliceEntity>> = dao.getVillagePoliceByUnion(unionId)
    val allVillagePolice: Flow<List<VillagePoliceEntity>> = dao.getAllVillagePolice()

    // Schools, CNG, Important Persons
    val schoolsColleges: Flow<List<SchoolCollegeEntity>> = dao.getAllSchoolsColleges()
    val cngStations: Flow<List<CNGStationEntity>> = dao.getAllCNGStations()
    val importantPersons: Flow<List<ImportantPersonEntity>> = dao.getAllImportantPersons()
    val policeOfficers: Flow<List<PoliceOfficerEntity>> = dao.getAllPoliceOfficers()
    val notices: Flow<List<NoticeEntity>> = dao.getAllNotices()
    val chatMessages: Flow<List<ChatMessageEntity>> = dao.getAllChatMessages()
    val favorites: Flow<List<FavoriteContactEntity>> = dao.getAllFavorites()
    val hotlineNumbers: Flow<HotlineNumbersEntity?> = dao.getHotlineNumbers()

    fun getDeveloperInfo(): Flow<DeveloperInfoEntity?> = dao.getDeveloperInfo()

    suspend fun updateHotlineNumbers(hotline: HotlineNumbersEntity) {
        dao.insertOrUpdateHotlineNumbers(hotline)
    }

    // Admin CRUD Operations
    suspend fun addOrUpdateMember(member: UnionMemberEntity) {
        dao.insertMember(member)
    }

    suspend fun deleteMember(id: Long) {
        dao.deleteMember(id)
    }

    suspend fun addOrUpdateVillagePolice(police: VillagePoliceEntity) {
        dao.insertSingleVillagePolice(police)
    }

    suspend fun deleteVillagePolice(id: Long) {
        dao.deleteVillagePolice(id)
    }

    suspend fun addOrUpdateSchool(school: SchoolCollegeEntity) {
        dao.insertSchoolCollege(school)
    }

    suspend fun deleteSchool(id: Long) {
        dao.deleteSchoolCollege(id)
    }

    suspend fun addOrUpdateCNGStation(station: CNGStationEntity) {
        dao.insertCNGStation(station)
    }

    suspend fun deleteCNGStation(id: Long) {
        dao.deleteCNGStation(id)
    }

    suspend fun addOrUpdateImportantPerson(person: ImportantPersonEntity) {
        dao.insertImportantPerson(person)
    }

    suspend fun deleteImportantPerson(id: Long) {
        dao.deleteImportantPerson(id)
    }

    suspend fun addOrUpdatePoliceOfficer(officer: PoliceOfficerEntity) {
        dao.insertPoliceOfficer(officer)
    }

    suspend fun deletePoliceOfficer(id: Long) {
        dao.deletePoliceOfficer(id)
    }

    suspend fun addNotice(notice: NoticeEntity) {
        dao.insertNotice(notice)
    }

    suspend fun deleteNotice(id: Long) {
        dao.deleteNotice(id)
    }

    suspend fun sendChatMessage(message: ChatMessageEntity) {
        dao.insertChatMessage(message)
    }

    // Favorites
    suspend fun toggleFavorite(id: String, name: String, designation: String, mobile: String, category: String, photoUrl: String = "") {
        if (dao.isFavorite(id)) {
            dao.removeFavorite(id)
        } else {
            dao.addFavorite(FavoriteContactEntity(id = id, name = name, designation = designation, mobile = mobile, category = category, photoUrl = photoUrl))
        }
    }

    suspend fun isFavorite(id: String): Boolean = dao.isFavorite(id)

    // Web & Cloud Data Import (JSON format)
    suspend fun importDataFromJson(jsonString: String): Int {
        var count = 0
        runCatching {
            val root = JSONObject(jsonString)

            if (root.has("chairmen")) {
                val array = root.getJSONArray("chairmen")
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    val unionId = obj.getString("unionId")
                    dao.insertChairman(
                        ChairmanEntity(
                            id = obj.optString("id", "${unionId}_chairman"),
                            unionId = unionId,
                            name = obj.getString("name"),
                            photoUrl = obj.optString("photoUrl", ""),
                            mobile = obj.optString("mobile", ""),
                            email = obj.optString("email", ""),
                            officeAddress = obj.optString("officeAddress", ""),
                            mapsUrl = obj.optString("mapsUrl", "")
                        )
                    )
                    count++
                }
            }

            if (root.has("members")) {
                val array = root.getJSONArray("members")
                val list = mutableListOf<UnionMemberEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        UnionMemberEntity(
                            unionId = obj.getString("unionId"),
                            name = obj.getString("name"),
                            photoUrl = obj.optString("photoUrl", ""),
                            wardNumber = obj.optInt("wardNumber", 1),
                            villages = obj.optString("villages", ""),
                            mobile = obj.optString("mobile", "")
                        )
                    )
                }
                if (list.isNotEmpty()) {
                    dao.insertMembers(list)
                    count += list.size
                }
            }

            if (root.has("village_police")) {
                val array = root.getJSONArray("village_police")
                val list = mutableListOf<VillagePoliceEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        VillagePoliceEntity(
                            unionId = obj.getString("unionId"),
                            name = obj.getString("name"),
                            photoUrl = obj.optString("photoUrl", ""),
                            responsibleVillages = obj.optString("responsibleVillages", obj.optString("village", "")),
                            mobile = obj.optString("mobile", "")
                        )
                    )
                }
                if (list.isNotEmpty()) {
                    dao.insertVillagePolice(list)
                    count += list.size
                }
            }

            if (root.has("important_persons")) {
                val array = root.getJSONArray("important_persons")
                val list = mutableListOf<ImportantPersonEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        ImportantPersonEntity(
                            category = obj.optString("category", "সমাজসেবক"),
                            name = obj.getString("name"),
                            designation = obj.optString("designation", ""),
                            mobile = obj.getString("mobile"),
                            address = obj.optString("address", ""),
                            photoUrl = obj.optString("photoUrl", "")
                        )
                    )
                }
                dao.insertImportantPersons(list)
                count += list.size
            }

            if (root.has("schools")) {
                val array = root.getJSONArray("schools")
                val list = mutableListOf<SchoolCollegeEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        SchoolCollegeEntity(
                            name = obj.getString("name"),
                            logoUrl = obj.optString("logoUrl", ""),
                            headTeacher = obj.optString("headTeacher", ""),
                            designation = obj.optString("designation", "প্রধান শিক্ষক"),
                            mobile = obj.getString("mobile"),
                            email = obj.optString("email", ""),
                            mapsUrl = obj.optString("mapsUrl", ""),
                            type = obj.optString("type", "স্কুল")
                        )
                    )
                }
                dao.insertSchoolsColleges(list)
                count += list.size
            }

            if (root.has("cng_stations")) {
                val array = root.getJSONArray("cng_stations")
                val list = mutableListOf<CNGStationEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        CNGStationEntity(
                            stationName = obj.optString("stationName", ""),
                            standName = obj.optString("standName", ""),
                            linemanName = obj.optString("linemanName", ""),
                            mobile = obj.optString("mobile", ""),
                            location = obj.optString("location", "")
                        )
                    )
                }
                dao.insertCNGStations(list)
                count += list.size
            }

            if (root.has("notices")) {
                val array = root.getJSONArray("notices")
                val list = mutableListOf<NoticeEntity>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        NoticeEntity(
                            title = obj.getString("title"),
                            category = obj.optString("category", "সাধারণ"),
                            content = obj.optString("content", ""),
                            date = obj.optString("date", ""),
                            isEmergency = obj.optBoolean("isEmergency", false)
                        )
                    )
                }
                dao.insertNotices(list)
                count += list.size
            }

            if (root.has("developer_info")) {
                val devObj = root.getJSONObject("developer_info")
                dao.insertOrUpdateDeveloperInfo(
                    DeveloperInfoEntity(
                        id = "dev_info",
                        name = devObj.optString("name", "ডিজিটাল অ্যাপস ডেভেলপার টিম"),
                        title = devObj.optString("title", "চাটখিল, নোয়াখালী • বাংলাদেশ"),
                        photoUrl = devObj.optString("photoUrl", ""),
                        email = devObj.optString("email", ""),
                        phone = devObj.optString("phone", ""),
                        bio = devObj.optString("bio", ""),
                        appIconUrl = devObj.optString("appIconUrl", ""),
                        appName = devObj.optString("appName", "খিলপাড়া পুলিশ তদন্ত কেন্দ্র")
                    )
                )
                count++
            }
        }
        return count
    }

    // Fetch and Sync from a remote JSON Web URL
    suspend fun syncFromRemoteUrl(urlString: String): Int {
        return kotlinx.coroutines.withContext(Dispatchers.IO) {
            val url = java.net.URL(urlString)
            val connection = url.openConnection() as java.net.HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            val jsonText = connection.inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()
            importDataFromJson(jsonText)
        }
    }

    // Export Database as JSON string
    suspend fun exportDatabaseAsJson(): String {
        val root = JSONObject()

        // 1. Chairmen
        val chairmenList = dao.getAllChairmen().first()
        val chairmenArr = JSONArray()
        chairmenList.forEach { c ->
            val obj = JSONObject()
            obj.put("id", c.id)
            obj.put("unionId", c.unionId)
            obj.put("name", c.name)
            obj.put("photoUrl", c.photoUrl)
            obj.put("mobile", c.mobile)
            obj.put("email", c.email)
            obj.put("officeAddress", c.officeAddress)
            obj.put("mapsUrl", c.mapsUrl)
            chairmenArr.put(obj)
        }
        root.put("chairmen", chairmenArr)

        // 2. Members
        val membersList = dao.getAllMembers().first()
        val membersArr = JSONArray()
        membersList.forEach { m ->
            val obj = JSONObject()
            obj.put("unionId", m.unionId)
            obj.put("name", m.name)
            obj.put("photoUrl", m.photoUrl)
            obj.put("wardNumber", m.wardNumber)
            obj.put("villages", m.villages)
            obj.put("mobile", m.mobile)
            membersArr.put(obj)
        }
        root.put("members", membersArr)

        // 3. Village Police
        val policeList = dao.getAllVillagePolice().first()
        val policeArr = JSONArray()
        policeList.forEach { p ->
            val obj = JSONObject()
            obj.put("unionId", p.unionId)
            obj.put("name", p.name)
            obj.put("photoUrl", p.photoUrl)
            obj.put("responsibleVillages", p.responsibleVillages)
            obj.put("mobile", p.mobile)
            policeArr.put(obj)
        }
        root.put("village_police", policeArr)

        // 4. Schools
        val schools = dao.getAllSchoolsColleges().first()
        val schoolsArr = JSONArray()
        schools.forEach {
            val obj = JSONObject()
            obj.put("name", it.name)
            obj.put("logoUrl", it.logoUrl)
            obj.put("headTeacher", it.headTeacher)
            obj.put("designation", it.designation)
            obj.put("mobile", it.mobile)
            obj.put("email", it.email)
            obj.put("mapsUrl", it.mapsUrl)
            obj.put("type", it.type)
            schoolsArr.put(obj)
        }
        root.put("schools", schoolsArr)

        // 5. CNG Stations
        val cngs = dao.getAllCNGStations().first()
        val cngArr = JSONArray()
        cngs.forEach {
            val obj = JSONObject()
            obj.put("stationName", it.stationName)
            obj.put("standName", it.standName)
            obj.put("linemanName", it.linemanName)
            obj.put("mobile", it.mobile)
            obj.put("location", it.location)
            cngArr.put(obj)
        }
        root.put("cng_stations", cngArr)

        // 6. Important Persons
        val persons = dao.getAllImportantPersons().first()
        val personsArr = JSONArray()
        persons.forEach {
            val obj = JSONObject()
            obj.put("category", it.category)
            obj.put("name", it.name)
            obj.put("designation", it.designation)
            obj.put("mobile", it.mobile)
            obj.put("address", it.address)
            obj.put("photoUrl", it.photoUrl)
            personsArr.put(obj)
        }
        root.put("important_persons", personsArr)

        // 7. Notices
        val noticesList = dao.getAllNotices().first()
        val noticesArr = JSONArray()
        noticesList.forEach {
            val obj = JSONObject()
            obj.put("title", it.title)
            obj.put("category", it.category)
            obj.put("content", it.content)
            obj.put("date", it.date)
            obj.put("isEmergency", it.isEmergency)
            noticesArr.put(obj)
        }
        root.put("notices", noticesArr)

        // 8. Developer Info
        val devInfo = dao.getDeveloperInfo().first()
        if (devInfo != null) {
            val devObj = JSONObject()
            devObj.put("name", devInfo.name)
            devObj.put("title", devInfo.title)
            devObj.put("photoUrl", devInfo.photoUrl)
            devObj.put("email", devInfo.email)
            devObj.put("phone", devInfo.phone)
            devObj.put("bio", devInfo.bio)
            devObj.put("appIconUrl", devInfo.appIconUrl)
            devObj.put("appName", devInfo.appName)
            root.put("developer_info", devObj)
        }

        return root.toString(2)
    }

    // Developer Info
    suspend fun addOrUpdateDeveloperInfo(devInfo: DeveloperInfoEntity) {
        dao.insertOrUpdateDeveloperInfo(devInfo)
    }
}
