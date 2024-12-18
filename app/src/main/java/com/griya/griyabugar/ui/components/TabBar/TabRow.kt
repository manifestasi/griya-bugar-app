package com.griya.griyabugar.ui.components.TabBar

import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color

@Composable
@UiComposable
fun TabRow(
    tabSelectedIndex:Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    contentColor : Color = Color.Black,
    indicator: @Composable @UiComposable (tabPosition : List<TabPosition>) -> Unit =
        @Composable {
            tabPosition ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(
                    tabPosition[tabSelectedIndex]
                ),
            )
        },
    tabs: @Composable @UiComposable () -> Unit

):Unit{
    tabs()
}