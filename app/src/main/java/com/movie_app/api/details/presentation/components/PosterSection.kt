package com.movie_app.api.details.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.movie_app.api.R
import com.movie_app.api.main.data.remote.api.MediaApi
import com.movie_app.api.main.domain.model.Media
import com.movie_app.api.ui.theme.SmallRadius


@Composable
fun PosterSection(
    media: Media
) {

    val imageUrl = "${MediaApi.IMAGE_BASE_URL}${media.posterPath}"

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Card(
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
            .padding(start = 16.dp),
        shape = RoundedCornerShape(SmallRadius),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        MovieImage(
            imageState = imageState,
            description = stringResource(R.string.poster, media.title),
            icon = Icons.Rounded.ImageNotSupported
        )

    }

}






















