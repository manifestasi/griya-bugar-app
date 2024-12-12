package com.griya.griyabugar.ui.screen.main.myaccount.editprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.ui.components.Field.DropDownField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.NoHandPhoneField
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun EditProfileScreen(
    rootNavControll: NavHostController = rememberNavController()
){

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedGender by rememberSaveable { mutableStateOf("") }
    val genderOptions = listOf("Laki-laki", "Perempuan")
    var nama by rememberSaveable { mutableStateOf("") }
    var noTelepon by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var imageUrl by rememberSaveable { mutableStateOf<String>("") }

    // Membuat launcher untuk membuka galeri
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUrl = uri.toString() // Simpan URI gambar yang dipilih
    }

    Scaffold(
        topBar = {
            AppBarWithBackButton(
                title = "Edit Profile",
                onClickBack = {
                    rootNavControll.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.clickable { launcher.launch("image/*") }) {
                    CircleImageProfile(
                        size = 96,
                        url = imageUrl
                    )

                    Canvas(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .offset(
                                x = (-4).dp,
                            )
                    ){
                        drawCircle(
                            color = Gray,
                            radius = size.minDimension / 2
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Column {
                Text(
                    text = "Nama*",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    onChange = {
                        nama = it
                    },
                    value = nama,
                    placeHolder = ""
                )
            }

            Spacer(Modifier.height(18.dp))

            Column {
                Text(
                    text = "Nomor Telepon*",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(8.dp))
                NoHandPhoneField(
                    onChange = {
                        noTelepon = it
                    },
                    value = noTelepon
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Email*",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                onChange = {
                    email = it
                },
                placeHolder = "Masukkan Email",
                value = email
            )

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Jenis Kelamin*",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            DropDownField(
                label = "Pilih jenis kelamin",
                selectedValue = selectedGender,
                expanded = expanded,
                onExpandedChange = {
                    expanded = !it
                },
                options = genderOptions,
                onSelected = {
                    selectedGender = it
                }
            )

            Spacer(Modifier.height(36.dp))

            ButtonConfirm(
                onClick = {},
                name = "Simpan"
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview(){
    GriyaBugarTheme {
        EditProfileScreen()
    }
}