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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun UploadDialog(
    onDismiss: () -> Unit,
    title: String,
    description: String
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

                Icon(
                    painter = painterResource(R.drawable.ic_image),
                    contentDescription = "icon verify",
                    tint = GreenColor1,
                    modifier = Modifier.size(50.dp)
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

            }
        }
    }
}
@Preview()
@Composable
fun UploadDialogPreview(modifier: Modifier = Modifier) {
    UploadDialog(
        onDismiss = {},
        title = "Proses Upload",
        description = "Proses"
    )
}