package com.griya.griyabugar.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.griya.griyabugar.ui.components.Button.BoxButtonBorderDP
import com.griya.griyabugar.ui.components.Button.GradientBoxButton
import com.griya.griyabugar.ui.screen.cms.layanan_cms.LayananViewModel
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.abu
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun LayananInsertDialog(
    title:String,
    onDismiss:()->Unit,
    modifier: Modifier = Modifier,
    width:Float = 0.9f,
    onBatalClick : () -> Unit,
    onSimpanClick:()->Unit,
    layananViewModel: LayananViewModel
){
    val context = LocalContext.current
    var value_text by rememberSaveable { mutableStateOf("") }



    Dialog(
        onDismissRequest = onDismiss
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
                Text(title,
                    fontFamily = poppins,
                    fontSize= 16.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                TxtField(
                    value = value_text,
                    onChange = {
                        value_text = it
                    }
                )


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
                    if(value_text.isNotBlank() || value_text.isNotEmpty() ){
                        GradientBoxButton(
                            text = "Simpan",
                            onClick = {
                                onSimpanClick()

                                layananViewModel.insertData(
                                    field = "nama",
                                    new_value = value_text
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


@Composable
fun LayananUpdateDialog(
    title:String,
    onDismiss:()->Unit,
    modifier: Modifier = Modifier,
    width:Float = 0.9f,
    onBatalClick : () -> Unit,
    onSimpanClick:()->Unit,
    uuid_doc:String,
    name_to_edit:String,
    layananViewModel: LayananViewModel = hiltViewModel()
){

    val context = LocalContext.current

    var value_text by rememberSaveable { mutableStateOf(name_to_edit) }



    Dialog(
        onDismissRequest = onDismiss
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
                Text(title,
                    fontFamily = poppins,
                    fontSize= 16.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                TxtField(
                    value = value_text,
                    onChange = {
                        value_text = it
                    }
                )


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
                    if(value_text.isNotBlank() || value_text.isNotEmpty() ){
                        GradientBoxButton(
                            text = "Simpan",
                            onClick = {
                                onSimpanClick()

                                layananViewModel.updateData (
                                    uuid_doc = uuid_doc,
                                    field = "nama",
                                    new_value = value_text
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


@Composable
private fun TxtField(
    value :String,
    onChange:(String)->Unit,
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            onChange(it)
        },
        placeholder = { Text("Layanan") },
        modifier = Modifier
            .fillMaxWidth(0.6f),

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}