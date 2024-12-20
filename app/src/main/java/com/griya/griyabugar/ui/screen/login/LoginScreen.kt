package com.griya.griyabugar.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.R
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.data.respository.AuthRepository
import com.griya.griyabugar.ui.components.CircleElemen.CircleElement
import com.griya.griyabugar.ui.components.Field.EmailTextField
import com.griya.griyabugar.ui.components.Field.PasswordTextField
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.components.register.TextField
import com.griya.griyabugar.ui.theme.Gray
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.TextColor1
import com.griya.griyabugar.ui.theme.TextColor2

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToCms: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Surface(
        modifier = modifier
        .fillMaxSize()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            CircleElement()
            CircleElement(
                alignment = Alignment.BottomStart,
                startAngle = 0f,
                endAngle = 360f,
                offsetX = -130,
                offsetY = 80
            )
            Column(
                modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
            ) {
                Spacer(Modifier.height(40.dp))
                HeaderSection(modifier = Modifier .padding(16.dp))
                MainSection(
                    modifier = Modifier.padding(16.dp),
                    onNavigateToRegister = onNavigateToRegister,
                    onNavigateToForgotPassword = onNavigateToForgotPassword,
                    onNavigateToMain = onNavigateToMain,
                    onNavigateToCms = onNavigateToCms,
                    loginViewModel = loginViewModel,
                    context = context
                )
            }
        }
    }
}

@Composable
private fun HeaderSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Masuk",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_bold))),
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Masuk untuk melanjutkan",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 24.sp,
            color = Color.Black
        )

    }
}

@Composable
private fun MainSection (
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToCms: () -> Unit,
    loginViewModel: LoginViewModel,
    context: Context
) {
//    val emailState = remember { mutableStateOf("") }
//    val state = remember { mutableStateOf("") }
//    val passwordVisible = remember { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isVisiblePassword by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    var isDisabled by rememberSaveable { mutableStateOf(false) }

    if (
        email.isEmpty() ||
        password.isEmpty()
    ){
        isDisabled = true
    } else {
        isDisabled = false
    }

    LaunchedEffect(Unit) {
        loginViewModel.loginEvent.collect { event ->
            when (event){

                is Resource.Loading -> {
                    isLoading = true
                }

                is Resource.Success -> {
                    isLoading = false
                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()

                    if (event.data?.role == AuthRepository.CUSTOMER) {
                        onNavigateToMain()
                    } else if (event.data?.role == AuthRepository.ADMIN) {
                        onNavigateToCms()
                    }
                }

                is Resource.Empty -> {
                    isLoading = false
                }

                is Resource.Error -> {
                    isLoading = false
                    isError = true
                    errorMessage = event.errorMessage
                }
            }
        }
    }

    if (isError){
        ErrorDialog(
            onDismiss = {
                isError = false
            },
            title = "Login gagal",
            buttonText = "Coba lagi",
            description = errorMessage,
            buttonOnClick = {
                isError = false
            }
        )
    }

    Column(modifier = modifier) {
        Text(
            text = "Email",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))

//        EmailTextField(
//            state = emailState
//        )

        TextField(
            onChange = {
                email = it
            },
            value = email,
            placeHolder = "Masukkan Email"
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Kata Sandi",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(10.dp))

//        PasswordTextField(
//            state = state,
//            passwordVisible = passwordVisible,
//        )
        PasswordField(
            onChange = {
                password = it
            },
            placeHolder = "Masukkan kata sandi",
            isPasswordVisible = isVisiblePassword,
            onVisibilityChange = {
                isVisiblePassword = it
            },
            value = password
        )

        Spacer(Modifier.height(10.dp))
        Text(
            text = "Lupa Kata Sandi",
            fontFamily = FontFamily(listOf(Font(R.font.poppins_medium))),
            fontSize = 16.sp,
            color = TextColor1,
            modifier = Modifier.align(Alignment.End).clickable {
                onNavigateToForgotPassword()
            }
        )
        Spacer(Modifier.height(30.dp))
        ButtonConfirm(
            onClick = {
                loginViewModel.loginAccunt(email, password)
            },
            name = "Masuk",
            isLoading = isLoading,
            isDisabled = isLoading || isDisabled
        )
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.align(CenterHorizontally)) {
            Text(
                text = "Belum memiliki akun?",
                fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = "Daftar",
                fontFamily = FontFamily(listOf(Font(R.font.poppins_semibold))),
                fontSize = 16.sp,
                color = TextColor2,
                modifier=Modifier.clickable {
                    onNavigateToRegister()
                }
            )

        }
    }
}
@Preview(showBackground = true)
@Composable
fun HeaderPreview(modifier: Modifier = Modifier) {
    GriyaBugarTheme {
        LoginScreen(
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {},
            onNavigateToMain = {},
            onNavigateToCms = {}
        )
    }
}