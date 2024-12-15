package com.griya.griyabugar.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement2
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.NoHandPhoneField
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.TextColor1
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToLogin: () -> Unit
){
    val context = LocalContext.current

    var nama by rememberSaveable { mutableStateOf("") }
    var noTelepon by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var visiblePassword by rememberSaveable { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var visibleConfirmPassword by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isDisabled by rememberSaveable { mutableStateOf(false) }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }

    var errorRegisterMessage by rememberSaveable { mutableStateOf("") }

    if(
        nama.isEmpty()
        ||
        noTelepon.isEmpty()
        ||
        email.isEmpty()
        ||
        password.isEmpty()
        ||
        confirmPassword.isEmpty()
    ){
        isDisabled = true
    } else {
        isDisabled = false
    }


//    val registerState = registerViewModel.registerState.collectAsState().value

    LaunchedEffect(Unit) {
        registerViewModel.registerEvent.collect { event ->
            when (event) {
                is Resource.Loading -> {
                    // Update loading state
                    isLoading = true
                    isDisabled = true
                }
                is Resource.Success -> {
                    // Update loading state dan tampilkan toast
                    isLoading = false
                    isDisabled = false
                    Toast.makeText(context, event.data, Toast.LENGTH_SHORT).show()
                    onNavigateToMain()
                }
                is Resource.Error -> {
                    // Update loading state dan tampilkan toast error
                    isLoading = false
                    isDisabled = false
                    showErrorDialog = true
                    errorRegisterMessage = event.errorMessage
                }
                is Resource.Empty -> {
                    isLoading = false
                    isDisabled = false
                }
            }
        }
    }

    /* Popup dialog */
    if(showErrorDialog){
        ErrorDialog(
            onDismiss = {
                showErrorDialog = false
            },
            title = "Register Gagal",
            description = errorRegisterMessage,
            buttonText = "Coba Lagi",
            buttonOnClick = {
                showErrorDialog = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ){
//            Canvas(
//                modifier = Modifier
//                    .size(150.dp)
//                    .align(Alignment.TopEnd)
//                    .offset(x = 50.dp, y = (-65).dp)
//            ) {
//                drawCircle(
//                    color = Gray, // Warna lingkaran
//                    radius = size.minDimension / 2 // Setengah dari ukuran canvas
//                )
//            }
//
//            Canvas(
//                modifier = Modifier
//                    .size(150.dp)
//                    .align(Alignment.BottomStart)
//                    .offset(x = (-90).dp, y = 80.dp)
//            ){
//                drawCircle(
//                    color = Gray,
//                    radius = size.minDimension / 2
//                )
//            }

//        CircleElement2(
//            modifier = Modifier
//                .size(200.dp)
//                .align(Alignment.TopEnd)
//                .offset(x = 100.dp, y = 100.dp)
//        )
        CircleElement()
        CircleElement2(
            startAngle = 0f,
            endAngle = 360f,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-130).dp, y = 80.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
        ) {
            Column {
                Text(
                    text = "Daftar",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        lineHeight = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        ),
                    text = "Daftar untuk melanjutkan",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400)
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
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
                    placeHolder = "Masukkan Nama"
                )
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
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
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            Column {
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
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            Column {
                Text(
                    text = "Kata Sandi*",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(8.dp))
                PasswordField(
                    placeHolder = "Masukkan Kata Sandi",
                    value = password,
                    onChange = {
                        password = it
                    },
                    isPasswordVisible = visiblePassword,
                    onVisibilityChange = {
                        visiblePassword = it
                    }
                )
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
            Column {
                Text(
                    text = "Konfirmasi Kata Sandi*",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(400),
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(8.dp))
                PasswordField(
                    placeHolder = "Konfirmasi Kata Sandi",
                    value = confirmPassword,
                    onChange = {
                        confirmPassword = it
                    },
                    isPasswordVisible = visibleConfirmPassword,
                    onVisibilityChange = {
                        visibleConfirmPassword = it
                    }
                )
            }
            Spacer(Modifier.height(42.dp))

            /* Daftar atau register */
            ButtonConfirm(
                onClick = {
                    registerViewModel.RegisterCustomer(
                        nama = nama,
                        noTelepon = noTelepon,
                        email = email,
                        password = password,
                        confirmPass = confirmPassword
                    )
                },
                name = "Daftar",
                isLoading = isLoading,
                isDisabled = isDisabled
            )

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.align(CenterHorizontally)) {
                Text(
                    text = "Belum memiliki akun?",
                    fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "Masuk",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextColor1,
                    modifier=Modifier.clickable {
                        onNavigateToLogin()
                    }
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    GriyaBugarTheme {
        RegisterScreen(
            onNavigateToMain = {},
            onNavigateToLogin = {}
        )
    }
}