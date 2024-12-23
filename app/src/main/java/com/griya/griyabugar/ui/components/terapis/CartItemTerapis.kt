package com.griya.griyabugar.ui.components.terapis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.R
import com.griya.griyabugar.ui.components.promo.ServiceRow
import com.griya.griyabugar.ui.theme.BackgroundColor
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.poppins

@Composable
fun CardItemTerapis(
    items: List<String>,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(146.dp),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Black
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.elevatedCardColors(BackgroundColor)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 16.dp
            )
        ){

            Row(
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    )
                    .align(Alignment.TopEnd)
            ) {

                Image(
                    modifier = Modifier.clickable {
                        onClickEdit()
                    },
                    painter = painterResource(R.drawable.ic_pencil),
                    contentDescription = "edit"
                )

                Spacer(Modifier.width(12.dp))

                Image(
                    modifier = Modifier.clickable {
                        onClickDelete()
                    },
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = "hapus"
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 22.dp)
                    .align(Alignment.TopStart)
            ) {

                Text(
                    text = "Angelica",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(Modifier.height(12.dp))

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_clock),
                        contentDescription = "jam",
                        tint = GreenColor3
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "10.00-12.00",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 12.sp
                        )
                    )
                }

                Spacer(Modifier.height(12.dp))

                ServiceRow(items = items,3)
            }

        }
    }
}