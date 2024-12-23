package com.griya.griyabugar.ui.screen.cms.terapis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.ui.components.terapis.CardItemTerapis
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun TerapisScreen(
    innerPadding: PaddingValues,
    rootNavController: NavHostController = rememberNavController()
){

    val items = listOf(
        "Traditional",
        "Shiatsu",
        "Kerokan",
        "Lulur Badan",
        "Body Scrum"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {

            CardItemTerapis(
                items,
                onClickEdit = {
                    rootNavController.navigate(Screen.EditTerapis.route)
                },
                onClickDelete = {}
            )

            Spacer(Modifier.height(20.dp))

            CardItemTerapis(
                items,
                onClickEdit = {},
                onClickDelete = {}
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TerapisPreview(){
    GriyaBugarTheme {
        TerapisScreen(
            innerPadding = PaddingValues(0.dp)
        )
    }
}