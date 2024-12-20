package com.griya.griyabugar.ui.screen.main.myaccount.editprofile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.UploadResult
import com.griya.griyabugar.ui.components.Field.DropDownField
import com.griya.griyabugar.ui.components.appbar.AppBarWithBackButton
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.loading.LoadingAnimation
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.NoHandPhoneField
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun EditProfileScreen(
    rootNavControll: NavHostController = rememberNavController(),
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
){
    /* context from activity */
    val context = LocalContext.current

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedGender by rememberSaveable { mutableStateOf("") }
    val genderOptions = listOf("Laki-laki", "Perempuan")
    var nama by rememberSaveable { mutableStateOf("") }
    var noTelepon by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var isDisabled by rememberSaveable { mutableStateOf(true) }

    var imageUrl by rememberSaveable { mutableStateOf<String>("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    var isLoading by rememberSaveable { mutableStateOf(false) }

    var isLoadingUpload by rememberSaveable { mutableStateOf(false) }
    var isErrorUpload by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    // Membuat launcher untuk membuka galeri
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null){
            imageUrl = uri.toString() // Simpan URI gambar yang dipilih
        }
        imageUri = uri
    }

    if(
        nama.isEmpty()
        ||
        noTelepon.isEmpty()
        ||
        email.isEmpty()
        ||
        selectedGender.isEmpty()
    ){
        isDisabled = true
    } else {
        isDisabled = false
    }

    LaunchedEffect(Unit) {
        editProfileViewModel.dataProfile.collect { event ->
            when (event){
                is Resource.Loading -> {
                    isLoading = true
                }
                is Resource.Success -> {
                    isLoading = false
                    val data = event.data
                    data?.let {
                        nama = it.nama ?: ""
                        email = it.email ?: ""
                        noTelepon = it.noTelepon ?: ""
                        selectedGender = it.kelamin ?: ""
                        imageUrl = it.foto ?: ""
                    }
                }
                is Resource.Error -> {
                    isLoading = false
                    Toast.makeText(context, event.errorMessage, Toast.LENGTH_LONG).show()
                }
                is Resource.Empty -> {
                    isLoading = false
                }
            }
        }

        editProfileViewModel.uploadDataProfileEvent.collect { event ->
            when (event){
                is UploadResult.Loading -> {
                    isLoadingUpload = true
                }
                is UploadResult.Success -> {
                    isLoadingUpload = false
                    Toast.makeText(
                        context,
                        event.data,
                        Toast.LENGTH_SHORT
                    ).show()

                    rootNavControll.navigate(
                        Screen.Main.route
                    ){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
                is UploadResult.Error -> {
                    isLoadingUpload = false
                    isErrorUpload = true
                    errorMessage = event.errorMessage
                }
                is UploadResult.Reschedule -> {
                    isLoadingUpload = false
                    isErrorUpload = true
                    errorMessage = "Rescheduled upload"
                }
                else ->{}
            }
        }
    }

    if (isErrorUpload){
        ErrorDialog(
            onDismiss = {

            },
            title = "Oops..",
            description = errorMessage,
            buttonText = "Oke",
            buttonOnClick = {
                isErrorUpload = false
            }
        )
    }

    /* Loading */
    if(isLoading){
        LoadingAnimation()
    } else {
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
                    .background(Color.White)
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

                        Image(
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = (-4).dp),
                            painter = painterResource(R.drawable.icon_pencil_profile),
                            contentDescription = "pencil"
                        )

                    }
                }

                Spacer(Modifier.height(32.dp))

                Column {
                    Text(
                        text = "Nama",
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
                        text = "Nomor Telepon",
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
                    text = "Email",
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
                    value = email,
                    readOnly = true
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "Jenis Kelamin",
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
                    onClick = {
                        editProfileViewModel.uploadDataProfile(
                            uri = imageUri,
                            email = email,
                            noTelepon = noTelepon,
                            nama = nama,
                            kelamin = selectedGender
                        )
                    },
                    name = "Simpan",
                    isDisabled = isDisabled || isLoadingUpload,
                    isLoading = isLoadingUpload
                )
            }
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