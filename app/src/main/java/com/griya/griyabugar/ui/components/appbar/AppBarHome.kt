package com.griya.griyabugar.ui.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.TextColorWhite
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun AppBarHome(
    modifier: Modifier,
    rootNavController: NavHostController = rememberNavController(),
    username: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 50.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column {
            Text(
                text = "Hai, $username",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextColorWhite,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Ayo rawat tubuhmu dengan terapi",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = TextColorWhite,
                modifier = Modifier
            )
        }

        IconButton(
            onClick = {
                rootNavController.navigate(Screen.Notifikasi.route)
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(40.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                painterResource(R.drawable.ic_notif),
                contentDescription = "Icon",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)

            )
        }
    }
}