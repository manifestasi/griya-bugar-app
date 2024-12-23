package com.griya.griyabugar.ui.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.ui.components.Button.BoxButtonBorderDP
import com.griya.griyabugar.ui.components.Button.GradientBoxButton
import com.griya.griyabugar.ui.screen.main.order.PemesananViewModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.abu
import com.griya.griyabugar.ui.theme.orange
import com.griya.griyabugar.ui.theme.poppins


@Composable
fun RateDialog(
    onDismissRequest: () -> Unit,
    modifier:Modifier = Modifier,
    width:Float = 0.9f,
    onBatalClick : () -> Unit,
    onSimpanClick :  ()-> Unit,
    pemesananViewModel:PemesananViewModel = hiltViewModel(),
    uuid_doc:String?
    ){
    var rating by remember { mutableStateOf(0) }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.fillMaxWidth(width)
                .wrapContentHeight()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding(20.dp)
            ) {
                Text("Beri Penilaian",
                    fontFamily = poppins,
                    fontSize= 16.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier= Modifier.fillMaxWidth()
                ){
                    for(i in 1..5){
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            contentDescription = "str",
                            modifier = Modifier.size(40.dp).clickable {
                                rating = i
                            },
                            tint = if(i <= rating) orange else abu,

                            )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){

                    BoxButtonBorderDP (
                        text = "Batal",
                        onClick = {
                            onBatalClick()
                        },
                        width = 100.dp,
                        borderColor = GreenMain,
                        fontColor = GreenMain, 
                        rounded = 6.dp, 
                        height = 46.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if(rating > 0){
                        GradientBoxButton(
                            text = "Simpan",
                            onClick = {
                                onSimpanClick()
                                pemesananViewModel.updateData(
                                    uuid = uuid_doc!!,
                                    field = "status",
                                    new_value = "SELESAI"
                                )

                                pemesananViewModel.updateData(
                                    uuid = uuid_doc,
                                    field = "rating",
                                    new_value = rating
                                )

                                pemesananViewModel.updateData(
                                    uuid = uuid_doc,
                                    field = "rated",
                                    new_value = true
                                )
                            },
                            fontColor = Color.White,
                            rounded = 6.dp,
                            height = 46.dp
                        )
                    }else{
                        GradientBoxButton(
                            text = "Simpan",
                            onClick = {
//                                onSimpanClick()
                            },
                            fontColor = Color.LightGray,
                            rounded = 6.dp,
                            height = 46.dp,
                            color = listOf(abu, abu)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RateDialogPreview(){
    GriyaBugarTheme {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            RateDialog(
                onDismissRequest = {},
                onBatalClick = {},
                onSimpanClick = {},
                uuid_doc = ""
            )
        }

    }
}