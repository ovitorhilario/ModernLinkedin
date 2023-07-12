package com.vitorhilarioapps.modernlinkedin.components.feed

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vitorhilarioapps.modernlinkedin.R
import com.vitorhilarioapps.modernlinkedin.ui.theme.Typography
import com.vitorhilarioapps.modernlinkedin.ui.theme.onBackground
import com.vitorhilarioapps.modernlinkedin.ui.theme.primaryTextColor
import com.vitorhilarioapps.modernlinkedin.ui.theme.secondaryBackground
import com.vitorhilarioapps.modernlinkedin.ui.theme.secondaryTextColor
import com.vitorhilarioapps.modernlinkedin.utils.extensions.toInteractionValue
import com.vitorhilarioapps.modernlinkedin.utils.fakedata.FakeData
import com.vitorhilarioapps.modernlinkedin.utils.structs.PostData

@Composable
fun Post(
    postData: PostData,
    onShowBottomSheet: () -> Unit,
    onRepost: () -> Unit,
    onOpenComment: (Int) -> Unit,
    overview: Boolean
) {
    Column(
      modifier = Modifier
          .padding(horizontal = 16.dp, vertical = if(overview) 0.dp else 8.dp)
          .background(onBackground, RoundedCornerShape(16.dp))
          .fillMaxWidth()
          .wrapContentHeight()
          .padding(8.dp)
    ) {

        val author = FakeData.users.first { it.id == postData.authorId }

        // Reply

        postData.reply?.let { reply ->
            val replyAuthor = FakeData.users.first { reply.authorId == it.id }

            Replied(
                name = replyAuthor.name,
                avatarModel = replyAuthor.avatarModel,
                action = reply.action
            )
        }

        // Post Author

        PostAuthor(
            name = author.name,
            resume = author.resume,
            avatarModel = author.avatarModel,
            timesAgo = postData.timesAgo,
            grade = 1
        )

        // Post Text

        PostText(text = postData.postText)

        // Post Content / Media

        postData.model?.let {
            PostContent(it)
        }

        // Interactions

        Interactions(
            likesCont = postData.likes,
            commentsCont = postData.allComments.size,
            repostsCont = postData.reposts,
            sharesCont = postData.shares,
            onShowBottomSheet = { onShowBottomSheet() },
            onRepost = { onRepost() },
            onOpenComment = { onOpenComment(postData.id) }
        )

        // CommentPreview

        if (overview) {
            postData.reply?.comment?.let { comment ->
                val replyAuthor = FakeData.users.first { postData.reply.authorId == it.id }

                CommentPreview(
                    name = replyAuthor.name,
                    resume = replyAuthor.resume,
                    avatarModel = replyAuthor.avatarModel,
                    text = comment
                )
            }
        } else {
            postData.allComments.forEach {
                val currentAuthor = FakeData.users.first { user -> user.id == it.authorId }

                CommentPreview(
                    name = currentAuthor.name,
                    resume = currentAuthor.resume,
                    avatarModel = currentAuthor.avatarModel,
                    text = it.text
                )
            }
        }
    }
}

@Composable
fun PostAuthor(
    name: String,
    resume: String,
    avatarModel: String,
    timesAgo: String,
    grade: Int,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarModel)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )

            Canvas(
                Modifier
                    .size(8.dp)
                    .align(Alignment.BottomEnd)
            ) {
                drawCircle(
                    color = Color.Green,
                    radius = size.width / 2f)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ){
            Column (
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(name)
                        withStyle(SpanStyle(fontWeight = FontWeight.Normal, color = secondaryTextColor)) {
                            append(" • $grade°")
                        }
                    },
                    fontStyle = Typography.titleLarge.fontStyle,
                    fontSize = Typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = primaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = resume,
                    fontStyle = Typography.bodyLarge.fontStyle,
                    fontSize = Typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = primaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ){
                    Text(
                        text = timesAgo,
                        fontStyle = Typography.bodyLarge.fontStyle,
                        fontSize = Typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.Normal,
                        color = secondaryTextColor
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(90f),
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = null,
                        tint = primaryTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun Replied(
    name: String,
    avatarModel: String,
    action: String
) {
    Column {
        // Replied
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarModel)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append(name)
                    }
                    append(" $action")
                },
                fontStyle = Typography.labelSmall.fontStyle,
                fontSize = Typography.labelSmall.fontSize,
                color = primaryTextColor
            )
        }

        Box (
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(Dp(1f))
                .background(secondaryBackground)
        )
    }
}

@Composable
fun PostText(
    text: String,
) {
    val style: TextStyle = LocalTextStyle.current
    val collapsedMaxLine = 2
    val showMoreText = "... Show More"
    val showLessText = " Show Less"
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    
    Box(modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        .clickable(clickable) {
            isExpanded = !isExpanded
        }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            text = buildAnnotatedString {
                if (clickable) {
                    if (isExpanded) {
                        append(text)
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) { append(showLessText) }
                    } else {
                        val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                            .dropLast(showMoreText.length)
                            .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                        append(adjustText)
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) { append(showMoreText) }
                    }
                } else {
                    append(text)
                }
            },
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
            fontStyle = Typography.bodyLarge.fontStyle,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    clickable = true
                    lastCharIndex = if (textLayoutResult.lineCount >= 1) {
                        textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                    } else {
                        textLayoutResult.getLineEnd(0)
                    }
                }
            },
            style = style,
            textAlign = TextAlign.Start,
            color = primaryTextColor
        )
    }
}

@Composable
fun PostContent(
    model: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(model)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun LottieIcon(
    modifier: Modifier,
    isEnable: Boolean,
    cont: Int,
    onClick: () -> Unit
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.like_icon))
    val animTimer by animateFloatAsState(
        targetValue = if (isEnable) 0.5f else 0f,
        animationSpec = tween(1000), label = ""
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            progress = animTimer,
            contentScale = ContentScale.Crop,
            composition = composition,
        )

        Text(
            text = cont.toInteractionValue(),
            fontStyle = Typography.bodyLarge.fontStyle,
            fontSize = Typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Normal,
            color = secondaryTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun Interactions(
    likesCont: Int,
    commentsCont: Int,
    repostsCont: Int,
    sharesCont: Int,
    onShowBottomSheet: () -> Unit,
    onRepost: () -> Unit,
    onOpenComment: () -> Unit
) {
    var liked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        LottieIcon(
            cont = likesCont,
            modifier = Modifier.weight(1f),
            isEnable = liked,
            onClick =  { liked = !liked }
        )

        InteractionIcon(
            cont = commentsCont,
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_comment),
            onClick = { onOpenComment() }
        )

        InteractionIcon(
            cont = repostsCont,
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_repost),
            onClick = { onRepost() }
        )

        InteractionIcon(
            cont = sharesCont,
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.ic_send),
            onClick = { onShowBottomSheet() }
        )
    }
}

@Composable
fun InteractionIcon(
    modifier: Modifier,
    painter: Painter,
    cont: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { onClick() },
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center),
                painter = painter,
                contentDescription = null,
                tint = primaryTextColor
            )
        }

        Text(
            text = cont.toInteractionValue(),
            fontStyle = Typography.labelSmall.fontStyle,
            fontSize = Typography.labelSmall.fontSize,
            fontWeight = FontWeight.Normal,
            color = secondaryTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CommentPreview(
    name: String,
    resume: String,
    avatarModel: String,
    text: String
) {
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(secondaryBackground, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box {
                AsyncImage(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarModel)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                )

                Canvas(
                    Modifier
                        .size(5.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    drawCircle(
                        color = Color.Green,
                        radius = size.width / 2f)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    fontStyle = Typography.bodyLarge.fontStyle,
                    fontSize = Typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = primaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = resume,
                    fontStyle = Typography.labelSmall.fontStyle,
                    fontSize = Typography.labelSmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = primaryTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        PostText(text = text)
    }
}

