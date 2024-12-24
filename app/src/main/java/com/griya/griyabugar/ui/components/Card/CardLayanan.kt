package com.griya.griyabugar.ui.components.Card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.components.Button.ButtonGradientBorder
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins


@Composable
fun CardLayanan(
    elevation:Dp = 3.dp,
    cornerSize:Dp = 5.dp,
    color: Color = Color.White,
    width:Float = 0.9f,
    text:String,
    modifier: Modifier = Modifier,
    onEditClick: ()->Unit,
    onDeleteClick:()->Unit
){

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        shape = RoundedCornerShape(corner = CornerSize(cornerSize)),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        modifier = modifier.fillMaxWidth(
            width
        ).wrapContentHeight()
    ) {
       Row(
           modifier = Modifier.fillMaxWidth().padding(5.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(text,
               fontFamily = poppins,
               fontSize = 14.sp,
               fontWeight = FontWeight.Bold,
               color = Color.Black,
                modifier = Modifier.padding(start=5.dp)
               )
           Spacer(modifier = Modifier.width(20.dp))

           Row (
               modifier = Modifier.wrapContentWidth().padding(end=10.dp, top=5.dp, bottom=5.dp),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.Center
           ){
               /*
               * button edit
               * */
               ButtonGradientBorder(
                   onClick = {
                       onEditClick()
                   }
               ){
                   Icon(
                       imageVector = Icons.Filled.Create,
                       contentDescription = "SD",
                       tint = GreenMain,
                       modifier = Modifier.size(20.dp)
                   )

               }

               Spacer(modifier = Modifier.width(10.dp))
                /*
                * untuk button delete
                * */
               ButtonGradientBorder(
                   onClick = {
                       onDeleteClick()
                   },
                   borderColor = listOf(Color.Red, Color.Red)
               ){
                   Icon(
                       imageVector = Icons.Filled.Delete,
                       contentDescription = "delete",
                       tint = Color.Red,
                       modifier = Modifier.size(20.dp)
                   )

               }
           }


       }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardLayananPreview(){
    GriyaBugarTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CardLayanan(
                text = "Kerokan",
                onEditClick = {},
                onDeleteClick = {}
            )
        }

    }
}