package com.griya.griyabugar.ui.components.profile.image

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircleImageProfile(
    modifier: Modifier = Modifier,
    url: String
){
    GlideImage(
        model = url,
        contentDescription = "profile",
        modifier = modifier
            .width(66.dp)
            .height(66.dp)
            .clip(RoundedCornerShape(100)),
        contentScale = ContentScale.None,
        loading = placeholder(R.drawable.placeholder_image_2),
        failure = placeholder(R.drawable.placeholder_image_2)
    )
}

@Preview(showBackground = true)
@Composable
fun CircleImageProfilePreview(){
    GriyaBugarTheme {
        CircleImageProfile(
            url = ""
        )
    }
}