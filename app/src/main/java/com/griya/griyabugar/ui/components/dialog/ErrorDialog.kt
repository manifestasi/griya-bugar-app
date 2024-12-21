package com.griya.griyabugar.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.GreenColor1
import com.griya.griyabugar.ui.theme.GreenColor2
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    title: String,
    description: String,
    buttonText: String,
    buttonOnClick: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .width(346.dp)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(Modifier.height(37.dp))

                Image(
                    painter = painterResource(R.drawable.icon_x),
                    contentDescription = "icon x"
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = description,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        buttonOnClick()
                    },
                    modifier = Modifier
                        .height(46.dp)
                        .width(260.dp)
                        .drawBehind {
                            val shadowColor = Color(0x28000000)
                            val shadowRadius = 5.dp.toPx()
                            val offsetX = 0.dp.toPx()
                            val offsetY = (2.5).dp.toPx()
                            drawRoundRect(
                                color = shadowColor,
                                topLeft = Offset(x = offsetX, y = offsetY),
                                size = Size(size.width + 2.dp.toPx(), size.height),
                                cornerRadius = CornerRadius(x = shadowRadius, y = shadowRadius),
                                style = Fill,
                                alpha = 1f,
                                blendMode = BlendMode.SrcOver,
                            )
                        },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        GreenColor1,
                                        GreenColor2
                                    )
                                )
                            )
                    ) {
                        Text(
                            text = buttonText,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontFamily = poppins,
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }

                Spacer(Modifier.height(30.dp))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ErrorDialogPreview(){
//    GriyaBugarTheme {
//        ErrorDialog(
//            onDismiss = {},
//            title = "Masuk akun gagal",
//            description = "pastikan kamu aman",
//            buttonText = "Coba lagi",
//            buttonOnClick = {}
//        )
//    }
//}