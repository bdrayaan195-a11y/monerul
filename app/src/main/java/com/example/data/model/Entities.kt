package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unions")
data class UnionEntity(
    @PrimaryKey val id: String,
    val nameBn: String,
    val nameEn: String,
    val chairmanName: String,
    val totalWards: Int,
    val totalVillages: Int,
    val description: String,
    val imageResName: String = ""
)

@Entity(tableName = "chairmen")
data class ChairmanEntity(
    @PrimaryKey val id: String,
    val unionId: String,
    val name: String,
    val photoUrl: String,
    val mobile: String,
    val email: String,
    val officeAddress: String,
    val mapsUrl: String
)

@Entity(tableName = "union_members")
data class UnionMemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val unionId: String,
    val name: String,
    val photoUrl: String,
    val wardNumber: Int,
    val villages: String,
    val mobile: String,
    val isFavorite: Boolean = false
)

@Entity(tableName = "village_police")
data class VillagePoliceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val unionId: String,
    val name: String,
    val photoUrl: String,
    val responsibleVillages: String,
    val mobile: String
)

@Entity(tableName = "schools_colleges")
data class SchoolCollegeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val logoUrl: String,
    val headTeacher: String,
    val designation: String,
    val mobile: String,
    val email: String,
    val mapsUrl: String,
    val type: String // "স্কুল" or "কলেজ"
)

@Entity(tableName = "cng_stations")
data class CNGStationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val stationName: String,
    val standName: String,
    val linemanName: String,
    val mobile: String,
    val location: String
)

@Entity(tableName = "important_persons")
data class ImportantPersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String, // "চিকিৎসক", "আইনজীবী", "শিক্ষক", "ব্যবসায়ী", "প্রকৌশলী", "বীর মুক্তিযোদ্ধা", "ধর্মীয় নেতা", "সমাজসেবক"
    val name: String,
    val designation: String,
    val mobile: String,
    val address: String,
    val photoUrl: String = "",
    val isFavorite: Boolean = false
)

@Entity(tableName = "police_officers")
data class PoliceOfficerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val designation: String, // e.g. "ইনচার্জ / পরিদর্শক (তদন্ত)", "উপ-পরিদর্শক (এসআই)", "ডিউটি অফিসার"
    val rank: String = "উপ-পরিদর্শক (এসআই)", // "পরিদর্শক", "এসআই", "এএসআই", "ডিউটি অফিসার"
    val mobile: String,
    val bpNumber: String = "",
    val photoUrl: String = "",
    val dutyArea: String = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র",
    val isFavorite: Boolean = false
)

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val content: String,
    val date: String,
    val isEmergency: Boolean = false
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val senderName: String,
    val senderRole: String, // "এডমিন", "পুলিশ কর্মকর্তা", "নাগরিক"
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isEmergency: Boolean = false
)

@Entity(tableName = "favorite_contacts")
data class FavoriteContactEntity(
    @PrimaryKey val id: String, // unique key e.g. "member_1", "person_2"
    val name: String,
    val designation: String,
    val mobile: String,
    val category: String,
    val photoUrl: String = ""
)

@Entity(tableName = "developer_info")
data class DeveloperInfoEntity(
    @PrimaryKey val id: String = "dev_info",
    val name: String = "ডিজিটাল অ্যাপস ডেভেলপার টিম",
    val title: String = "চাটখিল, নোয়াখালী • বাংলাদেশ",
    val photoUrl: String = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300",
    val email: String = "joynath2135@gmail.com",
    val phone: String = "01700000000",
    val bio: String = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি অ্যাপটি বাংলাদেশ পুলিশের নাগরিক সেবা দ্রুতকরণ, আইনশৃঙ্খলা বজায় রাখা এবং ৩টি ইউনিয়নের (খিলপাড়া, রামনারায়ণপুর, নোয়াখোলা) সর্বস্তরের জনগণের তথ্য সুবিধা প্রদানের জন্য নির্মিত সম্পূর্ণ ডিজিটাল প্লাটফর্ম।",
    val appIconUrl: String = "https://images.unsplash.com/photo-1582139329536-e7284fece509?w=300",
    val appName: String = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র"
)

@Entity(tableName = "hotline_numbers")
data class HotlineNumbersEntity(
    @PrimaryKey val id: String = "hotlines",
    val title1: String = "পুলিশ তদন্ত কেন্দ্র",
    val number1: String = "01769690011",
    val title2: String = "চাটখিল থানা",
    val number2: String = "01713374245",
    val title3: String = "ফায়ার সার্ভিস",
    val number3: String = "01730002233"
)

