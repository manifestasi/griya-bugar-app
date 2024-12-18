package com.griya.griyabugar.ui.screen.main.myaccount.changepassword.enteroldpass

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.data.Resource
import com.griya.griyabugar.ui.components.dialog.ErrorDialog
import com.griya.griyabugar.ui.components.register.ButtonConfirm
import com.griya.griyabugar.ui.components.register.PasswordField
import com.griya.griyabugar.ui.theme.poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EnterOldPassScreen(
    onNavigateToEnterEmail: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    enterOldPassViewModel: EnterOldPassViewModel = hiltViewModel(),
    onNavigateToEnterNewPass: () -> Unit
){
    val context = LocalContext.current

    var oldPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isDisabled by rememberSaveable { mutableStateOf(false) }
    var passVisible by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    if(isError){
        ErrorDialog(
            onDismiss = {},
            title = "Oops...",
            description = errorMessage,
            buttonText = "Ok",
            buttonOnClick = {
                isError = false
            }
        )
    }

    if (oldPassword.isEmpty()){
        isDisabled = true
    } else {
        isDisabled = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Spacer(Modifier.height(32.dp))

        Column {
            Text(
                text = "Kata Sandi Lama",
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    fontSize = 20.sp
                )
            )
            Spacer(Modifier.height(8.dp))
            PasswordField(
                placeHolder = "Masukkan Kata Sandi Lama",
                value = oldPassword,
                onChange = {
                    oldPassword = it
                },
                isPasswordVisible = passVisible,
                onVisibilityChange = {
                    passVisible = it
                }
            )
        }

        Spacer(Modifier.height(36.dp))

        ButtonConfirm(
            onClick = {
                coroutineScope.launch {
                    enterOldPassViewModel.verifyOldPassword(oldPassword).collect { event ->
                        when (event){
                            is Resource.Loading -> {
                                isLoading = true
                            }
                            is Resource.Success -> {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    event.data,
                                    Toast.LENGTH_SHORT
                                )
                                onNavigateToEnterNewPass()
                            }
                            is Resource.Error -> {
                                isLoading = false
                                isError = true
                                errorMessage = event.errorMessage
                            }
                            else -> {}
                        }
                    }
                }
            },
            name = "Selanjutnya",
            isDisabled = isLoading || isDisabled,
            isLoading = isLoading,
        )
    }
}