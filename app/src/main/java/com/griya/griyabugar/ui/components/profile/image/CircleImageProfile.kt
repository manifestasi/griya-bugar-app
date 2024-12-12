package com.griya.griyabugar.ui.components.profile.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import com.griya.griyabugar.ui.theme.Brown
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircleImageProfile(
    modifier: Modifier = Modifier,
    size: Int = 66,
    url: String
){
    GlideImage(
        model = url,
        contentDescription = "profile",
        modifier = modifier
            .size((size).dp)
            .clip(RoundedCornerShape(100))
            .background(Brown),
        contentScale = ContentScale.None,
        loading = placeholder(R.drawable.baseline_person_24),
        failure = placeholder(R.drawable.baseline_person_24)
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