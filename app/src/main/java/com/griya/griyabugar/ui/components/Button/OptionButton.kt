package com.griya.griyabugar.ui.components.Button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.MainColor

/** Opsi Button yang dapat digunakan di screen, set isBorder = true
jika ingin menggunakan border
 **/

@Composable
fun OptionButton(
    color:Color=Color.White,
    fontColor:Color=Color.Black,
    fontSize:TextUnit = 10.sp,
    isBorder:Boolean = false,
    borderColor:Color=Color.Black,
    width:Float = 0.2f,
    height: Dp = 40.dp,
    cornerSize:Dp=10.dp,
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
    items_options:List<Any>,
    selected_value:Any,
    button_value:Any,
    disableColor:Color = Color.LightGray,
    state : MutableState<String>,

){
    LazyVerticalGrid(
        columns = GridCells.Fixed(items_options.size),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier.fillMaxWidth(0.8f)
    ) {
         items(items_options){
             item ->
             Button (
                 onClick = {},
                 shape = RoundedCornerShape(cornerSize),
                 colors = ButtonDefaults.buttonColors(MainColor),
                border = if (isBorder) BorderStroke(2.dp, Color.Black) else null,
                 modifier = modifier
//                     .fillMaxWidth(width)
                     .height(height).wrapContentHeight()

             ){
                 Text(
                     "$item",
                     color = Color.Black,
                     textAlign = TextAlign.Center,
                     fontSize = 12.sp,
                     fontWeight = FontWeight.Bold,
                     fontFamily = FontFamily(listOf(Font(R.font.poppins_regular))),
                 )
             }
         }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OptionButtonPreview(
    modifier: Modifier = Modifier
) {
    val state_option =  remember { mutableStateOf("") }

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        OptionButton(
            color = MainColor,
            fontColor = Color.Black,
            width = 0.08f,
//            height = 0.05f,
            onClick = {},
            items_options = listOf("MENUNGGU", "SELESAI", "BATAL"),
            selected_value = "MENUNGGU",
            state = state_option,
            button_value = "MENUNGGU",
        )
    }

}