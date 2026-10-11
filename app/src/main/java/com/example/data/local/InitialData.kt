package com.example.data.local

import com.example.data.model.*

object InitialData {
    val unions = listOf(
        UnionEntity(
            id = "khilpara",
            nameBn = "১. খিলপাড়া ইউনিয়ন",
            nameEn = "Khilpara Union",
            chairmanName = "জনাব মো: আলমগীর হোসেন",
            totalWards = 9,
            totalVillages = 14,
            description = "খিলপাড়া ইউনিয়ন চাটখিল থানার একটি ঐতিহ্যবাহী সমৃদ্ধ ইউনিয়ন। খিলপাড়া পুলিশ তদন্ত কেন্দ্র এই ইউনিয়নে অবস্থিত।"
        ),
        UnionEntity(
            id = "ramnarayanpur",
            nameBn = "২. রামনারায়ণপুর ইউনিয়ন",
            nameEn = "Ramnarayanpur Union",
            chairmanName = "মোঃ হারুন অর রশিদ (বাহার)",
            totalWards = 9,
            totalVillages = 12,
            description = "রামনারায়ণপুর ইউনিয়ন কৃষি ও শিক্ষা সংস্কৃতিতে সমৃদ্ধ চাটখিলের অন্যতম গুরুত্বপূর্ণ ইউনিয়ন।"
        ),
        UnionEntity(
            id = "noakhola",
            nameBn = "৩. নোয়াখোলা ইউনিয়ন",
            nameEn = "Noakhola Union",
            chairmanName = "হাজী মোঃ মানিক",
            totalWards = 9,
            totalVillages = 16,
            description = "নোয়াখোলা ইউনিয়ন চাটখিল উপজেলার অন্যতম প্রাচীন ও জনবহুল ইউনিয়ন।"
        )
    )

    val chairmen = listOf(
        ChairmanEntity(
            id = "chairman_khilpara",
            unionId = "khilpara",
            name = "জনাব মো: আলমগীর হোসেন",
            photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300",
            mobile = "01751839620",
            email = "chairman.khilpara@union.gov.bd",
            officeAddress = "৯ নং খিলপাড়া ইউনিয়ন পরিষদ ভবন, খিলপাড়া বাজার, চাটখিল, নোয়াখালী",
            mapsUrl = "https://maps.google.com/?q=Khilpara+Union+Parishad"
        ),
        ChairmanEntity(
            id = "chairman_ramnarayanpur",
            unionId = "ramnarayanpur",
            name = "মোঃ হারুন অর রশিদ (বাহার)",
            photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=300",
            mobile = "01714175714",
            email = "chairman.ramnarayanpur@union.gov.bd",
            officeAddress = "দক্ষিণ রামনারায়নপুর, চাটখিল, নোয়াখালী (ওয়ার্ড নং ৬)",
            mapsUrl = "https://maps.google.com/?q=Ramnarayanpur+Union+Parishad"
        ),
        ChairmanEntity(
            id = "chairman_noakhola",
            unionId = "noakhola",
            name = "হাজী মোঃ মানিক",
            photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=300",
            mobile = "01712625576",
            email = "chairman.noakhola@union.gov.bd",
            officeAddress = "নোয়াখোলা ইউনিয়ন পরিষদ ভবন, প্রধান সড়ক, চাটখিল, নোয়াখালী",
            mapsUrl = "https://maps.google.com/?q=Noakhola+Union+Parishad"
        )
    )

    val members = listOf(
        // Khilpara Union Members (9 No. Khilpara Union)
        UnionMemberEntity(unionId = "khilpara", name = "জনাবা আয়েশা আক্তার", photoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200", wardNumber = 1, villages = "সংরক্ষিত মেম্বার (১, ২, ৩ নং ওয়ার্ড)", mobile = "01726694361"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাবা নাজমা বেগম", photoUrl = "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=200", wardNumber = 4, villages = "সংরক্ষিত মেম্বার (৪, ৫, ৬ নং ওয়ার্ড)", mobile = "01722567584"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাবা মমতাজ বেগম রানী", photoUrl = "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=200", wardNumber = 7, villages = "সংরক্ষিত মেম্বার (৭, ৮, ৯ নং ওয়ার্ড)", mobile = "01888745261"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মোঃ মোস্তফা মোল্লা", photoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200", wardNumber = 1, villages = "মেম্বার - ১ নং ওয়ার্ড", mobile = "01915399460"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মোঃ ফারুকের রহমান", photoUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200", wardNumber = 2, villages = "মেম্বার - ২ নং ওয়ার্ড", mobile = "01881130552"),
        UnionMemberEntity(unionId = "khilpara", name = "মোঃ ছিদ্দিক উল্যা", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", wardNumber = 3, villages = "মেম্বার - ৩ নং ওয়ার্ড", mobile = "01735670611"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব সিরাজুল ইসলাম", photoUrl = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=200", wardNumber = 4, villages = "মেম্বার - ৪ নং ওয়ার্ড", mobile = "01883298784"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মোঃ জসিম উদ্দিন", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", wardNumber = 5, villages = "মেম্বার - ৫ নং ওয়ার্ড", mobile = "01715531833"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মোঃ মামুন রশিদ", photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200", wardNumber = 6, villages = "মেম্বার - ৬ নং ওয়ার্ড", mobile = "01719196648"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব সাদ্দাম হোসেন", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", wardNumber = 7, villages = "মেম্বার - ৭ নং ওয়ার্ড", mobile = "01851714654"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মোঃ শাহজাহান", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", wardNumber = 8, villages = "মেম্বার - ৮ নং ওয়ার্ড", mobile = "01840381469"),
        UnionMemberEntity(unionId = "khilpara", name = "জনাব মামুনুর রশিদ মানিক", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", wardNumber = 9, villages = "মেম্বার - ৯ নং ওয়ার্ড", mobile = "01827122911"),

        // Ramnarayanpur Union Members
        UnionMemberEntity(unionId = "ramnarayanpur", name = "মনোয়ারা বেগম", photoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200", wardNumber = 1, villages = "সংরক্ষিত ইউপি সদস্যা (১, ২, ৩ নং ওয়ার্ড) | গ্রাম: ধর্মপুর", mobile = "01867601371"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "বকুল বেগম", photoUrl = "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=200", wardNumber = 4, villages = "সংরক্ষিত ইউপি সদস্যা (৪, ৫, ৬ নং ওয়ার্ড) | গ্রাম: পশ্চিম রামনারায়নপুর", mobile = "01906497666"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "খোদেজা বেগম", photoUrl = "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=200", wardNumber = 7, villages = "সংরক্ষিত ইউপি সদস্যা (৭, ৮, ৯ নং ওয়ার্ড) | গ্রাম: কালিকাপুর", mobile = "01813955839"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "নুর নবী", photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", wardNumber = 1, villages = "মেম্বার (১ নং ওয়ার্ড) | গ্রাম: ধর্মপুর", mobile = "01813955839"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "শাহ জামাল", photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", wardNumber = 2, villages = "মেম্বার (২ নং ওয়ার্ড) | গ্রাম: ছোবহানপুর/পাঁচঘরিয়া", mobile = "01710087762"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "মনজুর হোসেন", photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", wardNumber = 3, villages = "মেম্বার (৩ নং ওয়ার্ড) | গ্রাম: রুহিতখালী/মাধবপুর", mobile = "01777672235"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "আবু তাহের", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", wardNumber = 4, villages = "মেম্বার (৪ নং ওয়ার্ড) | গ্রাম: উত্তর রামনারায়নপুর", mobile = "01720698391"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "মোঃ দেলোয়ার হোসেন", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", wardNumber = 5, villages = "মেম্বার (৫ নং ওয়ার্ড) | গ্রাম: পশ্চিম রামনারায়নপুর", mobile = "01818290560"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "আলমগীর হোসেন", photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200", wardNumber = 6, villages = "মেম্বার (৬ নং ওয়ার্ড) | গ্রাম: দক্ষিণ রামনারায়নপুর", mobile = "01819941784"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "মিজানুর রহমান", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", wardNumber = 7, villages = "মেম্বার (৭ নং ওয়ার্ড) | গ্রাম: বৈকুন্ঠপুর", mobile = "01763684835"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "নাসির আহমেদ", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", wardNumber = 8, villages = "মেম্বার (৮ নং ওয়ার্ড) | গ্রাম: উত্তর গোমাতলী/কালিকাপুর", mobile = "01822855812"),
        UnionMemberEntity(unionId = "ramnarayanpur", name = "মোবারক উল্যা", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", wardNumber = 9, villages = "মেম্বার (৯ নং ওয়ার্ড) | গ্রাম: দক্ষিণ গোমাতলী/রাজারামঘোষ", mobile = "01822942585"),

        // Noakhola Union Members
        UnionMemberEntity(unionId = "noakhola", name = "আমেনা বেগম", photoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200", wardNumber = 1, villages = "সংরক্ষিত ইউপি সদস্যা (১, ২, ৩ নং ওয়ার্ড)", mobile = "01736367899"),
        UnionMemberEntity(unionId = "noakhola", name = "নুরু আক্তার", photoUrl = "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=200", wardNumber = 4, villages = "সংরক্ষিত ইউপি সদস্যা (৪, ৫, ৬ নং ওয়ার্ড)", mobile = "01928790854"),
        UnionMemberEntity(unionId = "noakhola", name = "রহিমা আক্তার", photoUrl = "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=200", wardNumber = 7, villages = "সংরক্ষিত ইউপি সদস্যা (৭, ৮, ৯ নং ওয়ার্ড)", mobile = "01887483082"),
        UnionMemberEntity(unionId = "noakhola", name = "মোঃ ফারুক পাটোয়ারী", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", wardNumber = 1, villages = "মেম্বার (১ নং ওয়ার্ড) | গ্রাম: নোয়াখালা", mobile = "01717024207"),
        UnionMemberEntity(unionId = "noakhola", name = "দেলোয়ার হোসেন", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", wardNumber = 2, villages = "মেম্বার (২ নং ওয়ার্ড) | গ্রাম: নোয়াখালা", mobile = "01728810838"),
        UnionMemberEntity(unionId = "noakhola", name = "মোঃ নাছির উদ্দিন", photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200", wardNumber = 3, villages = "মেম্বার (৩ নং ওয়ার্ড) | গ্রাম: নোয়াখালা", mobile = "01726313531"),
        UnionMemberEntity(unionId = "noakhola", name = "ফারুক আহাম্মদ", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", wardNumber = 4, villages = "মেম্বার (৪ নং ওয়ার্ড) | গ্রাম: সিংহবাহুড়া", mobile = "01819168224"),
        UnionMemberEntity(unionId = "noakhola", name = "মোঃ শাহ আলম", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", wardNumber = 5, villages = "মেম্বার (৫ নং ওয়ার্ড) | গ্রাম: শ্রীনগর", mobile = "01941077572"),
        UnionMemberEntity(unionId = "noakhola", name = "মোঃ ইয়াস হোসেন মুরাদ", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", wardNumber = 6, villages = "মেম্বার (৬ নং ওয়ার্ড) | গ্রাম: সালোনখালী", mobile = "01823051632"),
        UnionMemberEntity(unionId = "noakhola", name = "আবদুল আলী", photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", wardNumber = 7, villages = "মেম্বার (৭ নং ওয়ার্ড) | গ্রাম: সাদুরখিল", mobile = "01831285263"),
        UnionMemberEntity(unionId = "noakhola", name = "শহীদ উল্ল্যা", photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", wardNumber = 8, villages = "মেম্বার (৮ নং ওয়ার্ড) | গ্রাম: কড়িহাটি", mobile = "01794848473"),
        UnionMemberEntity(unionId = "noakhola", name = "জাফর ইকবাল", photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", wardNumber = 9, villages = "মেম্বার (৯ নং ওয়ার্ড) | গ্রাম: সারাপাড়া", mobile = "0182621596")
    )

    val villagePolice = listOf(
        // Khilpara Union Village Police
        VillagePoliceEntity(unionId = "khilpara", name = "মো: রুবেল হোসেন", photoUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200", responsibleVillages = "গ্রামপুলিশ - ১ নং ওয়ার্ড", mobile = "01870341751"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: নুর আলম", photoUrl = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=200", responsibleVillages = "গ্রামপুলিশ - ২ নং ওয়ার্ড", mobile = "01853582465"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: শাহ-আলম", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", responsibleVillages = "গ্রামপুলিশ - ৩ নং ওয়ার্ড", mobile = "01779402942"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: মহিন উদ্দিন", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", responsibleVillages = "গ্রামপুলিশ - ৪ নং ওয়ার্ড", mobile = "01727874303"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: সেলিম", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", responsibleVillages = "গ্রামপুলিশ - ৫ নং ওয়ার্ড", mobile = "01710090663"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: মিজান", photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", responsibleVillages = "গ্রামপুলিশ - ৬ নং ওয়ার্ড", mobile = "01862138480"),
        VillagePoliceEntity(unionId = "khilpara", name = "মোঃ হানিফ", photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", responsibleVillages = "গ্রামপুলিশ - ৭ নং ওয়ার্ড", mobile = "01763447919"),
        VillagePoliceEntity(unionId = "khilpara", name = "মিজানুর রহমান", photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", responsibleVillages = "গ্রামপুলিশ - ৮ নং ওয়ার্ড", mobile = "01860795172"),
        VillagePoliceEntity(unionId = "khilpara", name = "মো: কানু মিয়া", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", responsibleVillages = "গ্রামপুলিশ - ৯ নং ওয়ার্ড", mobile = "01746885795"),
        VillagePoliceEntity(unionId = "khilpara", name = "মোঃ নুর হোসেন", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", responsibleVillages = "দফাদার", mobile = "01836593083"),

        // Ramnarayanpur Union Village Police
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "ইসমাইল হোসেন রনি", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", responsibleVillages = "দফাদার | গ্রামঃ দঃ রামনারায়নপুর, পোঃ মল্লিকা দিঘীর পাড় (NID: 8710678056)", mobile = "01813650504"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "নুর মোহাম্মদ", photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ পঃ রামনারায়ানপুর, পোঃ ছোবানপুর (NID: 7511076661496)", mobile = "01818528191"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "আমেনা আক্তার", photoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ পঃ রামনারায়ানপুর, পোঃ কল্যান নগর", mobile = "01879206249"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "মোঃ রাসেল", photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ ধর্মপুর, পোঃ সোমপাড়া (NID: 7511076654605)", mobile = "01742053598"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "মাসুদ", photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ মাধবপুর, পোঃ ছোবানপুর (NID: 7511076658305)", mobile = "01739143952"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "মোঃ শাহজাহান", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ দ: রামনারায়ানপুর, পোঃ মল্লিকা দিঘীর পাড় (NID: 7511076662588)", mobile = "01815125387"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "মোকলেছ", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ পঃ রামনারায়ানপুর, পোঃ কল্যান নগর (NID: 7511076660423)", mobile = "01812550195"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "আব্দুল মালেক", photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ পঃ রামনারায়ানপুর, পোঃ কল্যান নগর (NID: 4634033210)", mobile = "01882146702"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "মোঃ জাকির", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ ছোবানপুর, পোঃ ছোবানপুর (NID: 750637656288)", mobile = "01837943612"),
        VillagePoliceEntity(unionId = "ramnarayanpur", name = "আল আমিন", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", responsibleVillages = "গ্রামপুলিশ | গ্রামঃ বৈকুণ্ঠপুর, পোঃ সুধারামী (NID: 7511076663112)", mobile = "01824948905"),

        // Noakhola Union Village Police
        VillagePoliceEntity(unionId = "noakhola", name = "গোলাম মোস্তফা", photoUrl = "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200", responsibleVillages = "চৌকিদার (১ নং ওয়ার্ড, গ্রাম: নোয়াখালা)", mobile = "01863003128"),
        VillagePoliceEntity(unionId = "noakhola", name = "আবু তালেব", photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", responsibleVillages = "চৌকিদার (২ নং ওয়ার্ড, গ্রাম: নোয়াখালা)", mobile = "01840234695"),
        VillagePoliceEntity(unionId = "noakhola", name = "বদিজ্জামান", photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", responsibleVillages = "চৌকিদার (৩ নং ওয়ার্ড, গ্রাম: নোয়াখালা)", mobile = "01752569139"),
        VillagePoliceEntity(unionId = "noakhola", name = "দিদার হোসেন জসিম", photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", responsibleVillages = "চৌকিদার (৪ নং ওয়ার্ড, গ্রাম: সিংহবাহুড়া)", mobile = "01831980488"),
        VillagePoliceEntity(unionId = "noakhola", name = "দিদার হোসেন হেলাল", photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200", responsibleVillages = "চৌকিদার (৫ নং ওয়ার্ড, গ্রাম: শ্রীনগর)", mobile = "01749080127"),
        VillagePoliceEntity(unionId = "noakhola", name = "আরমান", photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=200", responsibleVillages = "চৌকিদার (৬ নং ওয়ার্ড, গ্রাম: সালোনখালী)", mobile = "01813166603"),
        VillagePoliceEntity(unionId = "noakhola", name = "সোহাগ", photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=200", responsibleVillages = "চৌকিদার (৭ নং ওয়ার্ড, গ্রাম: সাদুরখিল)", mobile = "01859579264"),
        VillagePoliceEntity(unionId = "noakhola", name = "আবুল বশর", photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200", responsibleVillages = "চৌকিদার (৮ নং ওয়ার্ড, গ্রাম: কড়িহাটি)", mobile = "01742986363"),
        VillagePoliceEntity(unionId = "noakhola", name = "আনামত", photoUrl = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=200", responsibleVillages = "চৌকিদার (৯ নং ওয়ার্ড, গ্রাম: সারাপাড়া)", mobile = "01830301917")
    )

    val schoolsColleges = listOf(
        SchoolCollegeEntity(
            name = "খিলপাড়া বহুমুখী উচ্চ বিদ্যালয়",
            logoUrl = "https://images.unsplash.com/photo-1580582932707-520aed937b7b?w=300",
            headTeacher = "মুহাম্মদ বেলায়েত হোসেন",
            designation = "প্রধান শিক্ষক",
            mobile = "01716001122",
            email = "khilparahighschool@gmail.com",
            mapsUrl = "https://maps.google.com/?q=Khilpara+High+School",
            type = "স্কুল"
        ),
        SchoolCollegeEntity(
            name = "খিলপাড়া বালিকা উচ্চ বিদ্যালয় ও কলেজ",
            logoUrl = "https://images.unsplash.com/photo-1523050854058-8df90110c9f1?w=300",
            headTeacher = "বেগম রোকসানা আক্তার",
            designation = "অধ্যক্ষ",
            mobile = "01816002233",
            email = "khilparagirlscollege@gmail.com",
            mapsUrl = "https://maps.google.com/?q=Khilpara+Girls+School",
            type = "কলেজ"
        ),
        SchoolCollegeEntity(
            name = "রামনারায়ণপুর সরকারি প্রাথমিক বিদ্যালয়",
            logoUrl = "https://images.unsplash.com/photo-1509062522246-3755977927d7?w=300",
            headTeacher = "মোসাম্মাৎ সুলতানা রাজিয়া",
            designation = "প্রধান শিক্ষিকা",
            mobile = "01916003344",
            email = "ramnarayanpurprimary@gmail.com",
            mapsUrl = "https://maps.google.com/?q=Ramnarayanpur+Primary+School",
            type = "স্কুল"
        ),
        SchoolCollegeEntity(
            name = "নোয়াখোলা ফাজিল ডিগ্রি মাদ্রাসা",
            logoUrl = "https://images.unsplash.com/photo-1562774053-701939374585?w=300",
            headTeacher = "অধ্যক্ষ মাওলানা মুফতি আব্দুল আলিম",
            designation = "অধ্যক্ষ",
            mobile = "01716004455",
            email = "noakholamadrasa@gmail.com",
            mapsUrl = "https://maps.google.com/?q=Noakhola+Madrasa",
            type = "কলেজ"
        )
    )

    val cngStations = listOf(
        CNGStationEntity(
            stationName = "খিলপাড়া বাজার সিএনজি ফিলিং স্টেশন ও ফিল্ড",
            standName = "খিলপাড়া কেন্দ্রীয় বাজার স্ট্যান্ড",
            linemanName = "মোঃ রফিক লাইনম্যান",
            mobile = "01717001122",
            location = "খিলপাড়া বাজার সংলগ্ন, বাইপাস রোড, চাটখিল"
        ),
        CNGStationEntity(
            stationName = "রামনারায়ণপুর চৌরাস্তা সিএনজি স্ট্যান্ড",
            standName = "রামনারায়ণপুর স্ট্যান্ড",
            linemanName = "মোঃ সোহেল লাইনম্যান",
            mobile = "01817002233",
            location = "রামনারায়ণপুর প্রধান চৌরাস্তা"
        ),
        CNGStationEntity(
            stationName = "নোয়াখোলা অটোরিকশা ও সিএনজি স্ট্যান্ড",
            standName = "নোয়াখোলা বাজার স্ট্যান্ড",
            linemanName = "মোঃ মানিক মিয়া",
            mobile = "01917003344",
            location = "নোয়াখোলা বাজার মোর"
        )
    )

    val importantPersons = listOf(
        ImportantPersonEntity(
            category = "চিকিৎসক",
            name = "ডাঃ মোঃ আহসান হাবীব (এমবিবিএস, এফসিপিএস)",
            designation = "মেডিসিন বিশেষজ্ঞ, চাটখিল উপজেলা স্বাস্থ্য কমপ্লেক্স",
            mobile = "01718001122",
            address = "খিলপাড়া সেন্ট্রাল হাসপাতাল, খিলপাড়া বাজার"
        ),
        ImportantPersonEntity(
            category = "চিকিৎসক",
            name = "ডাঃ রেজওয়ানা পারভীন",
            designation = "স্ত্রী রোগ ও প্রসূতি বিশেষজ্ঞ",
            mobile = "01818002233",
            address = "খিলপাড়া ডিজিটাল ডায়াগনস্টিক সেন্টার"
        ),
        ImportantPersonEntity(
            category = "আইনজীবী",
            name = "এডভোকেট মোস্তফা কামাল ভূঁইয়া",
            designation = "জজ কোর্ট এডভোকেট, নোয়াখালী জজ কোর্ট",
            mobile = "01918003344",
            address = "খিলপাড়া উত্তর পাড়া, চাটখিল"
        ),
        ImportantPersonEntity(
            category = "প্রকৌশলী",
            name = "প্রকৌশলী সাব্বির আহমেদ",
            designation = "সহকারী প্রকৌশলী, এলজিইডি চাটখিল",
            mobile = "01718004455",
            address = "রামনারায়ণপুর, চাটখিল"
        ),
        ImportantPersonEntity(
            category = "শিক্ষক",
            name = "অধ্যাপক সিরাজুল ইসলাম",
            designation = "সাবেক উপাধ্যক্ষ, চাটখিল সরকারি কলেজ",
            mobile = "01818005566",
            address = "খিলপাড়া মাস্টার পাড়া"
        ),
        ImportantPersonEntity(
            category = "বীর মুক্তিযোদ্ধা",
            name = "বীর মুক্তিযোদ্ধা আলহাজ্ব আব্দুল মজিদ",
            designation = "সাবেক কমান্ডার, খিলপাড়া ইউনিয়ন মুক্তিযোদ্ধা সংসদ",
            mobile = "01918006677",
            address = "খিলপাড়া দেওপাড়া"
        ),
        ImportantPersonEntity(
            category = "ধর্মীয় নেতা",
            name = "মাওলানা কারী আব্দুর রহমান",
            designation = "খতীব, খিলপাড়া কেন্দ্রীয় জামে মসজিদ",
            mobile = "01718007788",
            address = "খিলপাড়া বাজার রোড"
        ),
        ImportantPersonEntity(
            category = "সমাজসেবক",
            name = "হাজী মোহাম্মদ হোসেন ভূঁইয়া",
            designation = "প্রতিষ্ঠাতা, খিলপাড়া সমাজকল্যাণ ফাউন্ডেশন",
            mobile = "01818008899",
            address = "খিলপাড়া পূর্ব বাজার"
        )
    )

    val notices = listOf(
        NoticeEntity(
            title = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র জরুরি হটলাইন ও পুলিশি সেবা",
            category = "জরুরি আইন-শৃঙ্খলা",
            content = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র থেকে খিলপাড়া, রামনারায়ণপুর ও নোয়াখোলা ইউনিয়নের সম্মানিত নাগরিকদের আইন-শৃঙ্খলা সংক্রান্ত যেকোনো জরুরি প্রয়োজনে সরাসরি ইনচার্জ সাহেবের সাথে যোগাযোগ করতে অনুরোধ করা যাচ্ছে। জরুরি কল: ৯৯৯ অথবা ০১৭৬৯-৬৯০০১১।",
            date = "২০২৬-০৭-৩০",
            isEmergency = true
        ),
        NoticeEntity(
            title = "আইন-শৃঙ্খলা রক্ষা ও বিট পুলিশিং সভা",
            category = "বিট পুলিশিং",
            content = "আগামীকাল বেলা ৩:০০ ঘটিকায় খিলপাড়া ইউনিয়ন পরিষদে বিট পুলিশিং সচেতনতামূলক সভা অনুষ্ঠিত হবে। মাদক, ইভটিজিং ও সাইবার অপরাধ প্রতিরোধে সমাজের সর্বস্তরের মানুষকে উপস্থিত থাকার আহ্বান জানানো হচ্ছে।",
            date = "২০২৬-০৭-২৮",
            isEmergency = false
        ),
        NoticeEntity(
            title = "স্মার্ট জাতীয় পরিচয়পত্র ও নাগরিক তথ্য হালনাগাদ সংক্রান্ত",
            category = "ইউনিয়ন সেবা",
            content = "ইউনিয়ন পরিষদের মাধ্যমে নাগরিক সনদের আবেদন সহজীকরণ করা হয়েছে। সাধারণ তথ্য সংশোধনের জন্য ভোটার আইডি কার্ডের মূল কপি নিয়ে ইউনিয়ন পরিষদ সেবা কেন্দ্রে যোগাযোগ করুন।",
            date = "২০২৬-০৭-২৫",
            isEmergency = false
        )
    )

    val chatMessages = listOf(
        ChatMessageEntity(
            senderName = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র (ইনচার্জ)",
            senderRole = "পুলিশ কর্মকর্তা",
            messageText = "আসসালামু আলাইকুম। খিলপাড়া পুলিশ তদন্ত কেন্দ্র সিটিজেন চ্যাট গ্রুপে স্বাগতম। যেকোনো তথ্য বা সহায়তা পেতে এখানে বার্তা পাঠাতে পারেন।",
            isEmergency = false
        ),
        ChatMessageEntity(
            senderName = "সিস্টেম এডমিন",
            senderRole = "এডমিন",
            messageText = "জরুরি অপরাধ বা দুর্ঘটনা সম্পর্কিত তথ্য দ্রুত জানিয়ে আইনশৃঙ্খলা রক্ষায় পুলিশকে সহযোগিতা করুন।",
            isEmergency = true
        )
    )

    val defaultDeveloperInfo = DeveloperInfoEntity(
        id = "dev_info",
        name = "ডিজিটাল অ্যাপস ডেভেলপার টিম",
        title = "চাটখিল, নোয়াখালী • বাংলাদেশ",
        photoUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=300",
        email = "joynath2135@gmail.com",
        phone = "01700000000",
        bio = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র ও ইউনিয়ন তথ্যাবলি অ্যাপটি বাংলাদেশ পুলিশের নাগরিক সেবা দ্রুতকরণ, আইনশৃঙ্খলা বজায় রাখা এবং ৩টি ইউনিয়নের (খিলপাড়া, রামনারায়ণপুর, নোয়াখোলা) সর্বস্তরের জনগণের তথ্য সুবিধা প্রদানের জন্য নির্মিত সম্পূর্ণ ডিজিটাল প্লাটফর্ম।",
        appIconUrl = "https://images.unsplash.com/photo-1582139329536-e7284fece509?w=300",
        appName = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র"
    )

    val defaultHotlineNumbers = HotlineNumbersEntity(
        id = "hotlines",
        title1 = "পুলিশ তদন্ত কেন্দ্র",
        number1 = "01769690011",
        title2 = "চাটখিল থানা",
        number2 = "01713374245",
        title3 = "ফায়ার সার্ভিস",
        number3 = "01730002233"
    )

    val policeOfficers = listOf(
        PoliceOfficerEntity(
            name = "মোঃ ইকবাল হোসেন",
            designation = "ইনচার্জ / পরিদর্শক (তদন্ত)",
            rank = "পরিদর্শক (ইনচার্জ)",
            mobile = "01769690011",
            bpNumber = "BP-8201123456",
            photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300",
            dutyArea = "খিলপাড়া পুলিশ তদন্ত কেন্দ্র (প্রধান ইনচার্জ)"
        ),
        PoliceOfficerEntity(
            name = "মোঃ রফিকুল ইসলাম",
            designation = "ডিউটি অফিসার (উপ-পরিদর্শক)",
            rank = "উপ-পরিদর্শক (এসআই)",
            mobile = "01713374245",
            bpNumber = "BP-8502234567",
            photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=300",
            dutyArea = "ডিউটি ডেস্ক (২৪ ঘণ্টা জরুরি সেবা)"
        ),
        PoliceOfficerEntity(
            name = "মোঃ জহিরুল ইসলাম",
            designation = "বিট অফিসার - খিলপাড়া ইউনিয়ন (এসআই)",
            rank = "উপ-পরিদর্শক (এসআই)",
            mobile = "01819876543",
            bpNumber = "BP-8803345678",
            photoUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=300",
            dutyArea = "বিট নং ১ (খিলপাড়া ইউনিয়ন পরিষদ এলাকা)"
        ),
        PoliceOfficerEntity(
            name = "মোঃ আলমগীর হোসেন",
            designation = "বিট অফিসার - রামনারায়ণপুর ইউনিয়ন (এসআই)",
            rank = "উপ-পরিদর্শক (এসআই)",
            mobile = "01712987654",
            bpNumber = "BP-8904456789",
            photoUrl = "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=300",
            dutyArea = "বিট নং ২ (রামনারায়ণপুর ইউনিয়ন)"
        ),
        PoliceOfficerEntity(
            name = "মোঃ মোস্তফা কামাল",
            designation = "বিট অফিসার - নোয়াখোলা ইউনিয়ন (এসআই)",
            rank = "উপ-পরিদর্শক (এসআই)",
            mobile = "01816543210",
            bpNumber = "BP-9005567890",
            photoUrl = "https://images.unsplash.com/photo-1501196354995-cbb51c65aaea?w=300",
            dutyArea = "বিট নং ৩ (নোয়াখোলা ইউনিয়ন)"
        ),
        PoliceOfficerEntity(
            name = "আব্দুর রাজ্জাক",
            designation = "সহকারী উপ-পরিদর্শক (এএসআই)",
            rank = "সহকারী উপ-পরিদর্শক (এএসআই)",
            mobile = "01730123456",
            bpNumber = "BP-9206678901",
            photoUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=300",
            dutyArea = "টহল দল ও স্পেশাল ডিউটি"
        ),
        PoliceOfficerEntity(
            name = "মোঃ সাজ্জাদ হোসেন",
            designation = "সহকারী উপ-পরিদর্শক (এএসআই)",
            rank = "সহকারী উপ-পরিদর্শক (এএসআই)",
            mobile = "01911223344",
            bpNumber = "BP-9307789012",
            photoUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=300",
            dutyArea = "টহল দল ও আইনশৃঙ্খলা মোবাইল টিম"
        )
    )
}
