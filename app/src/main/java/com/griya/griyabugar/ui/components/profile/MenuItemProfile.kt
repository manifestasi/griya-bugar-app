package com.griya.griyabugar.ui.components.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun MenuItemProfile(
    name: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                painter = painterResource(icon),
                contentDescription = "logo"
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = name,
                style = TextStyle(
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )

        }

        Image(
            painter = painterResource(R.drawable.forward_arrow),
            contentDescription = "arrow",
        )
    }
}