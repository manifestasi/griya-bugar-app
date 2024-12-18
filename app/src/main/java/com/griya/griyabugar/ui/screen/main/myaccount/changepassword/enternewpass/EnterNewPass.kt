package com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enternewpass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.dialog.SuccessDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EnterNewPassScreen(
    enterNewPassViewModel: EnterNewPassViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    rootNavController: NavHostController = rememberNavController()
){
    var password by rememberSaveable { mutableStateOf("") }
    var visiblePassword by rememberSaveable { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var visibleConfirmPassword by rememberSaveable { mutableStateOf(false) }

    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var successMessage by rememberSaveable { mutableStateOf("") }
    var isLoading  by rememberSaveable { mutableStateOf(false) }
    var isDisabled by rememberSaveable { mutableStateOf(false) }

    if(password.isEmpty() && confirmPassword.isEmpty()){
        isDisabled = true
    } else {
        isDisabled = false
    }

    if(isSuccess){
        SuccessDialog(
            onDismiss = {},
            title = "Sukses",
            description = successMessage,
            buttonText = "Lanjutkan",
            buttonOnClick = {
                isSuccess = false
                rootNavController.navigate(Screen.Main.route){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        )
    }

    if (isError){
        ErrorDialog(
            onDismiss = {},
            title = "Oops..",
            description = errorMessage,
            buttonText = "Cobal lagi",
            buttonOnClick = {
                isError = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(32.dp))

        Column {
            Text(
                text = "Kata Sandi Baru",
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

        Spacer(Modifier.height(21.dp))

        Column {
            Text(
                text = "Konfirmasi Kata Sandi",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            PasswordField(
                placeHolder = "Masukkan Kata Sandi Lama",
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

        Spacer(Modifier.height(36.dp))

        ButtonConfirm(
            onClick = {
                coroutineScope.launch {
                    enterNewPassViewModel.changePassword(password, confirmPassword).collect { event ->
                        when (event){
                            is Resource.Loading -> {
                                isLoading = true
                            }
                            is Resource.Success -> {
                                isLoading = true
                                isSuccess = true
                                successMessage = event.data
                            }
                            is Resource.Error -> {
                                isLoading = true
                                isError = true
                                errorMessage = event.errorMessage
                            }
                            else -> {

                            }
                        }
                    }
                }
            },
            name = "Simpan",
            isDisabled = isLoading || isDisabled,
            isLoading = isLoading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnterNewPassPreview(){
    GriyaBugarTheme {
        EnterNewPassScreen()
    }
}