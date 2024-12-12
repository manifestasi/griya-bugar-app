package com.griya.griyabugar.ui.components.Card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.MainColor

@Composable
fun TagCardPemesanan(
    title: String,
    label:String,
    color: Color= MainColor
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(title,
            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
            colors = CardColors(
                containerColor = color,
                contentColor = color,
                disabledContainerColor = color,
                disabledContentColor = color,
            )
        ){
            Column (
                modifier = Modifier.fillMaxHeight().padding(2.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text(label,
                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }

}

@Composable
fun CardItemPemesanan(
    modifier: Modifier,
    color: Color = Color.White,
    cornerSize: Dp = 10.dp,
    isBorder:Boolean = true,
    elevation: Dp = 5.dp,
    borderColor: Color = Color.Black,
    borderWidth:Dp = 1.dp,
    height:Dp = 150.dp,
    width:Float = 0.9f,
    title:String,
    harga:String,
    tanggal:String,
    jam:String,
    isTagged:Boolean = false,
    items_servis:List<String>
){
        Card(
         shape = RoundedCornerShape(cornerSize),
         elevation = CardDefaults.cardElevation(
             defaultElevation = elevation
         ),
         colors = CardColors(
             containerColor = color,
             contentColor = color,
             disabledContainerColor = color,
             disabledContentColor = color
         ),
         border = if (isBorder) BorderStroke(width = borderWidth, color = borderColor) else null,
        modifier = modifier.height(height).fillMaxWidth(width)

        ) {

            /*
            *  untuk layout item
            * */
            Row (
              modifier = Modifier.fillMaxSize()
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxHeight().padding(5.dp)
                ){
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "icon",
                        tint = MainColor,
                        modifier = Modifier.size(30.dp)
                    )

                    Text("Promosi",
                        fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                        )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(color = Color.Black)
                )

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(10.dp)
                ) {
                    if(isTagged){
                        TagCardPemesanan(title = title, label = "Selesai")
                    }else{
                        Text(title,
                            fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(
                        modifier=Modifier.height(5.dp)
                    )
                    Text(harga,
                        fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(
                        modifier=Modifier.height(5.dp)
                    )
                   Row(
                       verticalAlignment = Alignment.Top,
                       horizontalArrangement = Arrangement.Start,
                   ){
                       Text(tanggal,
                           fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                           color = Color.Black,
                           fontSize = 12.sp,
                           fontWeight = FontWeight.Bold,
                       )
                        Spacer(
                            modifier=Modifier.width(5.dp)
                        )
                       Text(jam,
                           fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                           color = Color.Black,
                           fontSize = 12.sp,
                           fontWeight = FontWeight.Bold,
                       )
                   }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        items(items_servis) {
                           item ->
                            Row(
                                modifier = Modifier.wrapContentSize(
                                    align = Alignment.CenterStart
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "asdad",
                                    modifier = Modifier.size(10.dp),
                                    tint = MainColor
                                )

                                Text("$item",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily((listOf((Font(R.font.poppins_regular))))),
                                    textAlign = TextAlign.Start
                                )
                            }

                    }
                    }
                }


            }

        }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CardItemPreview(){
    GriyaBugarTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CardItemPemesanan(modifier = Modifier.clickable { 
                
            },
                title = "Paket 2 Jam",
                harga = "Rp.200,000",
                tanggal = "12 Desember 2024",
                jam = "10.00-12.00",
                items_servis = listOf("Spa", "Body Scrum", "Tradisional",
                    "Kerokan", "Lulur Badan"
                    )
                )

        }
    }
}