package com.vitorhilarioapps.modernlinkedin.utils.structs

data class User(
    val id: Int,
    val name: String,
    val resume: String,
    val avatarModel: String,
    val bannerModel: String
)

data class PostData(
    val id: Int,
    val authorId: Int,
    val postText: String,
    val model: String?, // image
    val allComments: List<CommentData>,
    val likes: Int,
    val reposts: Int,
    val shares: Int,
    val reply: ReplyData?,
    val timesAgo: String
)

data class CommentData(
    val authorId: Int,
    val text: String
)

data class ReplyData(
    val authorId: Int,
    val action: String,
    val comment: String?
)

