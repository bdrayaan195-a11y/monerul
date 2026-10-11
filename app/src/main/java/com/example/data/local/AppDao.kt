package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Unions
    @Query("SELECT * FROM unions")
    fun getAllUnions(): Flow<List<UnionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnions(unions: List<UnionEntity>)

    @Query("DELETE FROM unions")
    suspend fun deleteAllUnions()

    // Chairmen
    @Query("SELECT * FROM chairmen")
    fun getAllChairmen(): Flow<List<ChairmanEntity>>

    @Query("SELECT * FROM chairmen WHERE unionId = :unionId")
    fun getChairmanByUnion(unionId: String): Flow<ChairmanEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChairman(chairman: ChairmanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChairmen(chairmen: List<ChairmanEntity>)

    @Query("DELETE FROM chairmen")
    suspend fun deleteAllChairmen()

    // Members
    @Query("SELECT * FROM union_members WHERE unionId = :unionId ORDER BY wardNumber ASC")
    fun getMembersByUnion(unionId: String): Flow<List<UnionMemberEntity>>

    @Query("SELECT * FROM union_members ORDER BY wardNumber ASC")
    fun getAllMembers(): Flow<List<UnionMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(members: List<UnionMemberEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: UnionMemberEntity)

    @Query("DELETE FROM union_members WHERE id = :id")
    suspend fun deleteMember(id: Long)

    @Query("DELETE FROM union_members")
    suspend fun deleteAllMembers()

    // Village Police
    @Query("SELECT * FROM village_police WHERE unionId = :unionId")
    fun getVillagePoliceByUnion(unionId: String): Flow<List<VillagePoliceEntity>>

    @Query("SELECT * FROM village_police")
    fun getAllVillagePolice(): Flow<List<VillagePoliceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVillagePolice(police: List<VillagePoliceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleVillagePolice(police: VillagePoliceEntity)

    @Query("DELETE FROM village_police WHERE id = :id")
    suspend fun deleteVillagePolice(id: Long)

    @Query("DELETE FROM village_police")
    suspend fun deleteAllVillagePolice()

    // Schools & Colleges
    @Query("SELECT * FROM schools_colleges")
    fun getAllSchoolsColleges(): Flow<List<SchoolCollegeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchoolsColleges(schools: List<SchoolCollegeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchoolCollege(school: SchoolCollegeEntity)

    @Query("DELETE FROM schools_colleges WHERE id = :id")
    suspend fun deleteSchoolCollege(id: Long)

    // CNG Stations
    @Query("SELECT * FROM cng_stations")
    fun getAllCNGStations(): Flow<List<CNGStationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCNGStations(stations: List<CNGStationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCNGStation(station: CNGStationEntity)

    @Query("DELETE FROM cng_stations WHERE id = :id")
    suspend fun deleteCNGStation(id: Long)

    // Important Persons
    @Query("SELECT * FROM important_persons")
    fun getAllImportantPersons(): Flow<List<ImportantPersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImportantPersons(persons: List<ImportantPersonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImportantPerson(person: ImportantPersonEntity)

    @Query("DELETE FROM important_persons WHERE id = :id")
    suspend fun deleteImportantPerson(id: Long)

    // Police Officers
    @Query("SELECT * FROM police_officers ORDER BY id ASC")
    fun getAllPoliceOfficers(): Flow<List<PoliceOfficerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoliceOfficers(officers: List<PoliceOfficerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoliceOfficer(officer: PoliceOfficerEntity)

    @Query("DELETE FROM police_officers WHERE id = :id")
    suspend fun deletePoliceOfficer(id: Long)

    @Query("DELETE FROM police_officers")
    suspend fun deleteAllPoliceOfficers()

    // Notices
    @Query("SELECT * FROM notices ORDER BY id DESC")
    fun getAllNotices(): Flow<List<NoticeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: NoticeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<NoticeEntity>)

    @Query("DELETE FROM notices WHERE id = :id")
    suspend fun deleteNotice(id: Long)

    // Chat Messages
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllChatMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    // Favorites
    @Query("SELECT * FROM favorite_contacts")
    fun getAllFavorites(): Flow<List<FavoriteContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteContactEntity)

    @Query("DELETE FROM favorite_contacts WHERE id = :id")
    suspend fun removeFavorite(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_contacts WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean

    // Developer Info
    @Query("SELECT * FROM developer_info WHERE id = 'dev_info' LIMIT 1")
    fun getDeveloperInfo(): Flow<DeveloperInfoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDeveloperInfo(devInfo: DeveloperInfoEntity)

    // Hotlines
    @Query("SELECT * FROM hotline_numbers WHERE id = 'hotlines' LIMIT 1")
    fun getHotlineNumbers(): Flow<HotlineNumbersEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateHotlineNumbers(hotlines: HotlineNumbersEntity)
}
