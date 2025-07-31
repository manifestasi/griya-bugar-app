package com.griya.griyabugar.ui.components.Card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.griya.griyabugar.ui.components.profile.image.CircleImageProfile
import com.griya.griyabugar.ui.theme.GreenMain
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun CardPelanggan(
    elevation: Dp = 3.dp,
    cornerSize: Dp = 5.dp,
    color: Color = Color.White,
    width:Float = 0.9f,
    height:Dp = 100.dp,
    nama:String,
    terapis: String,
    kategori : String,
    tanggal: String,
    jam: String,
    paket:String,
    url_img:String?,
    status:String,
    color_status:Color = GreenMain,
    modifier: Modifier = Modifier,
    onEditClick: ()->Unit,

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
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.wrapContentSize().padding(start=10.dp)
            ) {
                CircleImageProfile(
                    url = url_img,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(nama,
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start=5.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("$paket ($kategori)",
                        fontFamily = poppins,
                        fontSize = 9.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start=5.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Text(tanggal,
                            fontFamily = poppins,
                            fontSize = 9.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start=5.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("$jam WIB",
                            fontFamily = poppins,
                            fontSize = 9.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start=5.dp)
                        )
                    }
                }


            }


            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Row (
                    modifier = Modifier.wrapContentWidth().padding(end=10.dp, top=5.dp, bottom=5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Box(
                        modifier = Modifier.wrapContentSize()
                            .border(border = BorderStroke(
                                width = 1.dp, color = color_status
                            ))
                    ) {
                        Text(
                            status,
                            fontFamily = poppins,
                            fontSize = 10.sp,
                            color = color_status,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

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


                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Terapis: $terapis",
                    fontFamily = poppins,
                    fontSize = 9.sp,
                    color = Color.Black
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardPelangganPreview(){
    GriyaBugarTheme {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CardPelanggan(
                nama = "Bento",
                terapis = "Angela",
                kategori = "Promosi",
                tanggal = "Kamis, 22-12-2024",
                paket = "Paket 2 Jam",
                status = "Menunggu",
                url_img = null,
                jam = "19.00",
                onEditClick = {}
            )
        }
    }
}