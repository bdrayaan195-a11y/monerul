package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.*

@Database(
    entities = [
        UnionEntity::class,
        ChairmanEntity::class,
        UnionMemberEntity::class,
        VillagePoliceEntity::class,
        SchoolCollegeEntity::class,
        CNGStationEntity::class,
        ImportantPersonEntity::class,
        PoliceOfficerEntity::class,
        NoticeEntity::class,
        ChatMessageEntity::class,
        FavoriteContactEntity::class,
        DeveloperInfoEntity::class,
        HotlineNumbersEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "khilpara_police_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
