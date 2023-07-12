package com.vitorhilarioapps.modernlinkedin.utils.fakedata

import com.vitorhilarioapps.modernlinkedin.utils.structs.CommentData
import com.vitorhilarioapps.modernlinkedin.utils.structs.PostData
import com.vitorhilarioapps.modernlinkedin.utils.structs.ReplyData
import com.vitorhilarioapps.modernlinkedin.utils.structs.User

object FakeData {

    val users = listOf(
        User(
            0,
            "Vitor Hilário",
            "Android Developer #opentowork",
            "https://avatars.githubusercontent.com/u/81326138?v=4",
            "https://i.pinimg.com/originals/21/9a/09/219a09d5c2d9e50e4c2d20c9a03e09af.gif"
        ),
        User(
            1,
            "Mulan",
            "Android Engineer at @ Disney",
            "https://i.pinimg.com/564x/68/bc/12/68bc12a9a3c076b4550e98ebd63590c7.jpg",
            "https://i.pinimg.com/originals/21/9a/09/219a09d5c2d9e50e4c2d20c9a03e09af.gif"
        ),
        User(
            2,
            "SpiderCat",
            "Software Engineer at @ Marvel",
            "https://i.pinimg.com/564x/58/14/5c/58145c65e5b5515573ef5cde50789289.jpg",
            "https://i.pinimg.com/originals/21/9a/09/219a09d5c2d9e50e4c2d20c9a03e09af.gif"
        ),
        User(
            3,
            "Miles Morales",
            "Developer Student",
            "https://i.pinimg.com/564x/75/c7/8c/75c78c0b525bd74022aaac672f5d96fe.jpg",
            "https://i.pinimg.com/originals/21/9a/09/219a09d5c2d9e50e4c2d20c9a03e09af.gif"
        )
    )

    val posts = listOf(
        PostData(
            id = 1,
            authorId = 2,
            postText = "In software engineering, SOLID is a mnemonic acronym for five design principles intended to make object-oriented designs more understandable, flexible, and maintainable. The principles are a subset of many principles promoted by American software engineer and instructor Robert C. Martin, first introduced in his 2000 paper Design Principles and Design Patterns discussing software rot.",
            model = "https://i.pinimg.com/564x/b0/47/7f/b0477fbbe2a437ad432affdf1539ac30.jpg",
            allComments = listOf(
                CommentData(
                    3, "This is very important! 🚀"
                ),
            ),
            likes = 584,
            reposts = 85,
            shares = 46,
            reply = ReplyData(
                authorId = 3,
                "Comment",
                comment = "This is very important! 🚀"
            ),
            timesAgo = "1d"
        ),

        PostData(
            id = 2,
            authorId = 1,
            postText = "Jetpack Compose is Android’s recommended modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.",
            model = "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/5bb2e531a22c7de0.png?hl=pt-br",
            allComments = listOf(
                CommentData(
                    0, "The power of jetpack compose is amazing!"
                ),
                CommentData(
                    1, "Yess! we can make beautiful apps " + "❤️"
                ),
                CommentData(
                    0, "The future of android development haha 😃"
                ),
                CommentData(
                    1, "Sure!, @vitorhilario"
                ),
            ),
            likes = 467,
            reposts = 67,
            shares = 36,
            reply = ReplyData(
                authorId = 0,
                "Comment",
                comment = "The power of jetpack compose is amazing!"
            ),
            timesAgo = "3d"
        ),

        PostData(
            id = 3,
            authorId = 0,
            postText = "The new concept of the modern linkedin app is now available for testing!",
            model = "https://i.pinimg.com/564x/d8/5a/e6/d85ae6502a5c99a6d9c8c035e238e3b3.jpg",
            allComments = listOf(
                CommentData(
                    0, "This is very Important!"
                ),
                CommentData(
                    1, "Tanks! @vitorhilario"
                )
            ),
            likes = 156,
            reposts = 36,
            shares = 18,
            reply = null,
            timesAgo = "1d"
        ),
    )
}