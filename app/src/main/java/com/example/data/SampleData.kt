package com.example.data

import com.example.model.ChatMessage
import com.example.model.DatingProfile
import com.example.model.MatchItem
import com.example.model.ProfilePrompt

object SampleData {

    val sampleProfiles: List<DatingProfile> = listOf(
        DatingProfile(
            id = "profile_1",
            name = "Shenali",
            age = 24,
            occupation = "UI/UX Designer",
            occupationSi = "UI/UX නිර්මාණ ශිල්පිනී",
            location = "Colombo 07",
            distanceKm = 4,
            bio = "Tea lover, weekend surfer, and café hopper in Colombo. Looking for genuine connections & laughter! 🌸🏄‍♀️",
            bioSi = "තේ රසිකාවියක්, සති අන්තයේ වෙරළට සහ කැෆේවලට යන්න කැමතියි. සැබෑ මිත්‍රත්වයක් සහ ආදරයක් සොයනවා! 🌸",
            photos = listOf(
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Art & Design", "Coffee", "Surfing", "Indie Music", "Beaches"),
            interestsSi = listOf("කලා නිර්මාණ", "කෝපි", "සර්ෆින්", "සංගීතය", "වෙරළ"),
            prompts = listOf(
                ProfilePrompt(
                    question = "My ideal Sunday in Colombo...",
                    questionSi = "මගේ පරිපූර්ණ ඉරිදා දවස...",
                    answer = "Iced latte at a cozy café in Cinnamon Gardens, book in hand, followed by Galle Face sunset breeze 🌅"
                ),
                ProfilePrompt(
                    question = "We will get along if...",
                    questionSi = "අපි හොඳින් ගැළපෙන්නේ...",
                    answer = "You love spontaneous road trips to Mirissa or Ella with Spotify playlists on blast!"
                )
            ),
            isVerified = true,
            compatibility = 98,
            height = "5' 5\"",
            zodiac = "Cancer ♋",
            lookingFor = "Long-term relationship",
            lookingForSi = "ස්ථීර සබඳතාවක්",
            education = "University of Colombo"
        ),
        DatingProfile(
            id = "profile_2",
            name = "Dinithi",
            age = 23,
            occupation = "Architecture Student",
            occupationSi = "වාස්තු විද්‍යා ශිෂ්‍යාව",
            location = "Kandy",
            distanceKm = 12,
            bio = "Always sketching old buildings or hunting for spicy kottu! Love rainy evenings and deep conversations 🌧️✨",
            bioSi = "පැරණි ගොඩනැගිලි චිත්‍ර ඇඳීමට සහ සැර කොත්තු කෑමට ප්‍රිය කරමි! වැසි සහිත සවස් කාලයන් සහ ගැඹුරු කතාබස් වලට ආසයි 🌧️✨",
            photos = listOf(
                "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Architecture", "Street Food", "Photography", "Pets", "Hiking"),
            interestsSi = listOf("වාස්තු විද්‍යාව", "ආහාර", "ඡායාරූපකරණය", "සුරතලුන්", "කඳු තරණය"),
            prompts = listOf(
                ProfilePrompt(
                    question = "The key to my heart is...",
                    questionSi = "මගේ හදවත දිනාගැනීමේ රහස...",
                    answer = "Knowing the best tea boutique in the hill country and being kind to stray dogs 🐶"
                )
            ),
            isVerified = true,
            compatibility = 94,
            height = "5' 4\"",
            zodiac = "Taurus ♉",
            lookingFor = "Dating & exploring",
            lookingForSi = "කතාබහ සහ හමුවීම්",
            education = "University of Moratuwa"
        ),
        DatingProfile(
            id = "profile_3",
            name = "Ananya",
            age = 25,
            occupation = "Marketing Manager",
            occupationSi = "අලෙවිකරණ කළමනාකාරිනී",
            location = "Mount Lavinia",
            distanceKm = 8,
            bio = "Beach runs in the morning, rooftop dinners at dusk. Passionate about fitness, good books & podcasts 📖🌴",
            bioSi = "උදෑසන වෙරළේ දිවීම, සවස් කාලයේ සොඳුරු රාත්‍රී ආහාර. යෝග්‍යතාවය, පොත්පත් සහ පෝඩ්කාස්ට් වලට ආදරය කරමි 📖🌴",
            photos = listOf(
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Yoga", "Books", "Travel", "Cooking", "Sunset"),
            interestsSi = listOf("යෝගා", "පොත්පත්", "සංචාර", "ඉවුම් පිහුම්", "ඉර බසින අහස"),
            prompts = listOf(
                ProfilePrompt(
                    question = "Together, we could...",
                    questionSi = "අපිට එකතු වෙලා පුළුවන්...",
                    answer = "Cook a gourmet dinner together and rate each other's culinary skills honestly!"
                )
            ),
            isVerified = true,
            compatibility = 91,
            height = "5' 6\"",
            zodiac = "Libra ♎",
            lookingFor = "Long-term partner",
            lookingForSi = "සැබෑ ආදරය",
            education = "Kelaniya University"
        ),
        DatingProfile(
            id = "profile_4",
            name = "Michelle",
            age = 22,
            occupation = "Digital Artist & Content Creator",
            occupationSi = "ඩිජිටල් චිත්‍ර ශිල්පිනී",
            location = "Negombo",
            distanceKm = 18,
            bio = "A little bit nerdy, very ambitious. Loves anime, game nights, and sea breeze sunsets 🎮🎨",
            bioSi = "ඇනිමෙ, වීඩියෝ ක්‍රීඩා සහ මුහුදු සුළඟට ප්‍රිය කරන නිර්මාණශීලී තරුණියක් 🎮🎨",
            photos = listOf(
                "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Anime", "Gaming", "Cosplay", "Drawing", "Cat Lover"),
            interestsSi = listOf("ඇනිමෙ", "ගේමින්", "චිත්‍ර", "පූසන්", "ඩිජිටල් ආර්ට්"),
            prompts = listOf(
                ProfilePrompt(
                    question = "My simple pleasures in life...",
                    questionSi = "මම ජීවිතයේ සතුටු වන සරල දේවල්...",
                    answer = "A late night cup of hot Milo and good lo-fi music playing in the background."
                )
            ),
            isVerified = false,
            compatibility = 88,
            height = "5' 3\"",
            zodiac = "Pisces ♓",
            lookingFor = "New friends & more",
            lookingForSi = "මිතුරන් සහ විශේෂ කෙනෙක්",
            education = "SLIIT"
        ),
        DatingProfile(
            id = "profile_5",
            name = "Kasuni",
            age = 26,
            occupation = "Doctor / Medical Officer",
            occupationSi = "වෛද්‍යවරිය",
            location = "Galle",
            distanceKm = 25,
            bio = "Saving lives by day, relaxing inside Galle Fort by evening. Looking for someone with a gentle heart & great sense of humor 🩺✨",
            bioSi = "රෝහලේ සේවය කර සවස ගාලු කොටුව මත ඇවිදින්න කැමතියි. කාරුණික හදවතක් සහ හොඳ විහිළු තේරෙන කෙනෙක් සොයනවා 🩺✨",
            photos = listOf(
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Medicine", "Ocean", "History", "Jazz", "Volunteering"),
            interestsSi = listOf("වෛද්‍ය විද්‍යාව", "මුහුද", "ඉතිහාසය", "ජෑස් සංගීතය", "සමාජ සේවා"),
            prompts = listOf(
                ProfilePrompt(
                    question = "Fact about me that surprises people...",
                    questionSi = "මම ගැන බොහෝ දෙනෙක් නොදන්නා කරුණක්...",
                    answer = "I've scuba dived with sea turtles in Pigeon Island and survived night shifts with only black coffee!"
                )
            ),
            isVerified = true,
            compatibility = 95,
            height = "5' 7\"",
            zodiac = "Scorpio ♏",
            lookingFor = "Serious Relationship",
            lookingForSi = "විවාහය / බැරෑරුම් සබඳතාවක්",
            education = "Faculty of Medicine, Colombo"
        ),
        DatingProfile(
            id = "profile_6",
            name = "Sachini",
            age = 24,
            occupation = "Pastry Chef & Baker",
            occupationSi = "පේස්ට්‍රි සහ කේක් නිර්මාණ ශිල්පිනී",
            location = "Gampaha",
            distanceKm = 15,
            bio = "I bake happiness into croissants and cupcakes! Let me make you dessert and see if we match vibes 🥐🍰",
            bioSi = "මම රසවත් කේක් සහ බේකරි කෑම හදන්න ආසයි! ඔයා වෙනුවෙන් රසවත් ඩෙසර්ට් එකක් හදන්නම් 🥐🍰",
            photos = listOf(
                "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80",
                "https://images.unsplash.com/photo-1502823403499-6ccfcf4fb453?auto=format&fit=crop&w=900&q=80"
            ),
            interests = listOf("Baking", "Desserts", "Dogs", "Romantic Movies", "Acoustic"),
            interestsSi = listOf("බේකිං", "ඩෙසර්ට්ස්", "බල්ලන්", "රොමෑන්ටික් චිත්‍රපට", "සංගීතය"),
            prompts = listOf(
                ProfilePrompt(
                    question = "Best way to ask me out...",
                    questionSi = "මට ආරාධනා කිරීමට හොඳම ක්‍රමය...",
                    answer = "Tell me where we can find the flakiest sourdough or best hot chocolate in town."
                )
            ),
            isVerified = true,
            compatibility = 90,
            height = "5' 4\"",
            zodiac = "Gemini ♊",
            lookingFor = "Dating",
            lookingForSi = "ඩේටින්",
            education = "Colombo Culinary Arts"
        )
    )

    val initialMatches: List<MatchItem> = listOf(
        MatchItem(
            id = "match_1",
            profile = sampleProfiles[0],
            matchedAt = "Today, 10:24 AM",
            lastMessage = "Hey! Loved your bio about beach trips 😊",
            lastMessageTime = "10:30 AM",
            unreadCount = 1,
            isOnline = true
        ),
        MatchItem(
            id = "match_2",
            profile = sampleProfiles[1],
            matchedAt = "Yesterday",
            lastMessage = "That café in Kandy really has great tea!",
            lastMessageTime = "Yesterday",
            unreadCount = 0,
            isOnline = false
        ),
        MatchItem(
            id = "match_3",
            profile = sampleProfiles[4],
            matchedAt = "2 days ago",
            lastMessage = "Thanks for the sweet rose! 🌹",
            lastMessageTime = "2d ago",
            unreadCount = 0,
            isOnline = true
        )
    )

    val starterMessages: Map<String, List<ChatMessage>> = mapOf(
        "match_1" to listOf(
            ChatMessage(
                id = "m1",
                senderId = "profile_1",
                text = "Hi Tharindu! 👋 Great to match with you!",
                timestamp = "10:25 AM",
                isFromMe = false
            ),
            ChatMessage(
                id = "m2",
                senderId = "user",
                text = "Hey Shenali! Thanks, your beach photo looks breathtaking! Where was it taken?",
                timestamp = "10:28 AM",
                isFromMe = true
            ),
            ChatMessage(
                id = "m3",
                senderId = "profile_1",
                text = "Hey! Loved your bio about beach trips 😊 That was near Hiriketiya bay!",
                timestamp = "10:30 AM",
                isFromMe = false
            )
        ),
        "match_2" to listOf(
            ChatMessage(
                id = "m2_1",
                senderId = "profile_2",
                text = "Hello! Love that you like rainy Kandy weather too 🌧️",
                timestamp = "Yesterday",
                isFromMe = false
            ),
            ChatMessage(
                id = "m2_2",
                senderId = "user",
                text = "Nothing beats Kandy hills with a warm cup of Ceylon tea!",
                timestamp = "Yesterday",
                isFromMe = true
            ),
            ChatMessage(
                id = "m2_3",
                senderId = "profile_2",
                text = "That café in Kandy really has great tea!",
                timestamp = "Yesterday",
                isFromMe = false
            )
        ),
        "match_3" to listOf(
            ChatMessage(
                id = "m3_1",
                senderId = "user",
                text = "Sent you a virtual rose! 🌹",
                timestamp = "2d ago",
                isFromMe = true,
                giftType = "rose"
            ),
            ChatMessage(
                id = "m3_2",
                senderId = "profile_5",
                text = "Thanks for the sweet rose! 🌹 How is your week going?",
                timestamp = "2d ago",
                isFromMe = false
            )
        )
    )

    val icebreakersEn = listOf(
        "Hey! Loved your photos 🌊",
        "What's your go-to coffee order? ☕",
        "Beach sunset or mountain sunrise? 🌅",
        "If we could go on an island road trip tomorrow, where first? 🚗",
        "What song is currently on repeat for you? 🎶"
    )

    val icebreakersSi = listOf(
        "හායි! ඔයාගේ ෆොටෝස් හරිම ලස්සනයි 🌊",
        "ඔයා වඩාත්ම කැමති කෝපි වර්ගය මොකක්ද? ☕",
        "මුහුදු වෙරළේ ඉර බහින එකද, කඳුකරේ ඉර පායන එකද කැමති? 🌅",
        "හෙට අපි ට්‍රිප් එකක් යනවා නම් මුලින්ම කොහෙද යන්නේ? 🚗",
        "මේ දවස්වල වැඩිපුරම අහන සිංදුව මොකක්ද? 🎶"
    )
}
