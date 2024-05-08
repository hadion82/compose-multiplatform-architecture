package com.example.kmp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.data.model.CharacterItem
import composemultiplatform.app.generated.resources.Res
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CharacterContent(
    character: CharacterItem,
    onThumbnailClick: (String?) -> Unit,
    onBookmarkClick: (CharacterItem) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CharacterThumbnail(
            character = character,
            onClick = onThumbnailClick
        )
        CharacterInformation(item = character, onBookmarkClick = onBookmarkClick)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CharacterThumbnail(
    character: CharacterItem, onClick: (String?) -> Unit
) = with(character) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(thumbnail).crossfade(true).build(),
        placeholder = painterResource(Res.drawable.no_image),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(10.dp)
            .clickable { onClick(thumbnail) },
        onError = { Napier.d("[image] error : ${it.result.throwable}") }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CharacterInformation(item: CharacterItem, onBookmarkClick: (CharacterItem) -> Unit) =
    with(item) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(text = name ?: stringResource(Res.string.label_unknown))
                Text(text = stringResource(Res.string.url_count, urlCount))
                Text(text = stringResource(Res.string.comic_count, comicCount))
                Text(text = stringResource(Res.string.story_count, storyCount))
                Text(text = stringResource(Res.string.event_count, eventCount))
                Text(text = stringResource(Res.string.series_count, seriesCount))
            }
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = {
                    onBookmarkClick(this@with.copy(mark = !mark))
                }) {
                Icon(
                    painter = painterResource(
                        if (mark) Res.drawable.bookmark_activate
                        else Res.drawable.bookmark_deactivate
                    ),
                    contentDescription = stringResource(Res.string.label_bookmark)
                )
            }
        }
    }