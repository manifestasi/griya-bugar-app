package com.griya.griyabugar.ui.screen.main.myaccount

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.QuestionDialog
import com.griya.griyabugar.ui.components.profile.MenuItemProfile
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.finishAffinity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyAccountScreen(
    rootNavController: NavHostController = rememberNavController(),
    myAccountViewModel: MyAccountViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    isLoading: Boolean = false,
    onLoadingChange: (Boolean) -> Unit
//    onNavigateToEditProfile: () -> Unit
){

    val context = LocalContext.current

    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var showQuestionDialog by rememberSaveable { mutableStateOf(false) }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var emaiL by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        myAccountViewModel.getDataProfile.collect { event ->
            when (event){
                is Resource.Loading -> {
                    emaiL = "Loading..."
                    name = "Loading..."
                }
                is Resource.Success -> {
                    emaiL = event.data?.email ?: ""
                    name = event.data?.nama ?: ""
                    imageUrl = event.data?.foto ?: ""
                }
                is Resource.Error -> {
                    isError = true
                    errorMessage = event.errorMessage
                }
                else -> {}
            }
        }
    }

    if (isError){
        ErrorDialog(
            onDismiss = {},
            buttonText = "Oke",
            buttonOnClick = {
                isError = false
            },
            title = "Oops",
            description = errorMessage
        )
    }

    if (showQuestionDialog){
        QuestionDialog(
            onDismiss = {},
            title = "Keluar Akun",
            description = "Apakah anda yakin keluar akun?",
            btnClickYes = {
                coroutineScope.launch {
                    myAccountViewModel.logoutAccount().collect { event ->
                        when (event){
                            is Resource.Loading -> {
                                onLoadingChange(true)
                            }

                            is Resource.Success -> {
                                onLoadingChange(false)

                                /* Keluar dari aplikasi */
                                finishAffinity(context)
                            }

                            is Resource.Error -> {
                                onLoadingChange(false)
                                isError = true
                                errorMessage = event.errorMessage
                                showQuestionDialog = false
                            }

                            else -> {
                                onLoadingChange(false)
                            }
                        }
                    }
                }
                showQuestionDialog = false
            },
            btnClickNo = {
                showQuestionDialog = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .height(66.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /* image profile */
            CircleImageProfile(url = imageUrl)

            Spacer(Modifier.width(26.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = emaiL,
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
            icon = R.drawable.ic_profil,
            onClick = {
                rootNavController.navigate(Screen.EditProfile.route)
            }
        )

        Spacer(Modifier.height(16.dp))

        MenuItemProfile(
            name = "Ubah Kata Sandi",
            icon = R.drawable.ic_key,
            onClick = {
                rootNavController.navigate(Screen.ChangePassword2.route)
            }
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
            icon = R.drawable.ic_house,
            onClick = {
                rootNavController.navigate(Screen.InformasiGriya.route)
            }
        )

        Spacer(Modifier.height(16.dp))

        MenuItemProfile(
            name = "Hubungi SPA",
            icon = R.drawable.ic_headset,
            onClick = {
                val url = "https://wa.me/62882005002819"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
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
            icon = R.drawable.ic_exit,
            onClick = {
                showQuestionDialog = true
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyAccountScreenPreview(){
    GriyaBugarTheme {
        MyAccountScreen(
//            onNavigateToEditProfile = {},
            onLoadingChange = {}
        )
    }
}