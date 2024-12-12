package com.griya.griyabugar.ui.screen.main.myaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.profile.MenuItemProfile
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun MyAccountScreen(
    onNavigateToEditProfile: () -> Unit
){
    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .height(66.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleImageProfile(url = "")
                Spacer(Modifier.width(26.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Messi123",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "messislayer@gmail.com",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            MenuItemProfile(
                name = "Edit Profile",
                icon = R.drawable.placeholder_image_2,
                onClick = onNavigateToEditProfile
            )

            Spacer(Modifier.height(16.dp))

            MenuItemProfile(
                name = "Ubah Kata Sandi",
                icon = R.drawable.placeholder_image_2,
                onClick = {}
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Gray)
            )

            Spacer(Modifier.height(20.dp))

            MenuItemProfile(
                name = "Informasi SPA",
                icon = R.drawable.placeholder_image_2,
                onClick = {}
            )

            Spacer(Modifier.height(16.dp))

            MenuItemProfile(
                name = "Hubungi SPA",
                icon = R.drawable.placeholder_image_2,
                onClick = {}
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Gray)
            )

            Spacer(Modifier.height(20.dp))

            MenuItemProfile(
                name = "Keluar",
                icon = R.drawable.placeholder_image_2,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyAccountScreenPreview(){
    GriyaBugarTheme {
        MyAccountScreen(
            onNavigateToEditProfile = {}
        )
    }
}