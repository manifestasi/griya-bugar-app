package com.griya.griyabugar.ui.components.bottomnavbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.griya.griyabugar.R
import com.griya.griyabugar.data.model.BottomNavItem
import com.griya.griyabugar.ui.navigation.Screen
import com.griya.griyabugar.ui.theme.GreenColor3
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@Composable
fun BottomNavigationBar(
    navController: NavHostController = rememberNavController(),
    currentRoute: String? = Screen.Home.route
){
    NavigationBar (
        modifier = Modifier
            .drawBehind {
                val shadowColor = Color(0x33000000) // Warna bayangan
                val shadowHeight = 5.dp.toPx() // Tinggi bayangan
                val gradientBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, // Transparan di akhir
                        shadowColor, // Warna solid di awal
                    ),
                    startY = -shadowHeight,
                    endY = 0f
                )
                drawRect(
                    brush = gradientBrush,
                    topLeft = Offset(0f, -shadowHeight),
                    size = Size(size.width, shadowHeight)
                )
            },
        containerColor = Color.White,
    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
        val bottomNavItems = listOf(
            BottomNavItem(Screen.Home.route, R.drawable.ic_home, "Beranda"),
            BottomNavItem(Screen.Order.route, R.drawable.ic_order, "Pemesanan"),
            BottomNavItem(Screen.MyAccount.route, R.drawable.ic_acc, "Akun Saya")
        )

        bottomNavItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label,
                    )
                },
                label = {
                    Text(item.label)
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenColor3,
                    selectedTextColor = GreenColor3,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview(){
    GriyaBugarTheme {
        BottomNavigationBar()
    }
}