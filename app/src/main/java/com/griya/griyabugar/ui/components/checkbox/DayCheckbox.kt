package com.griya.griyabugar.ui.components.checkbox

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins
import com.griya.griyabugar.util.Days

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DayCheckBox(
    selectedDays: List<String>,
    onSelectionChange: (List<String>) -> Unit
){

    val items = Days.getData

    val checkedStates: MutableMap<String, Boolean> by rememberSaveable {
        mutableStateOf(
            items.associate { item ->
                item to (item in selectedDays)
            }.toMutableMap()
        )
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth().padding(end = 43.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {

                CustomCheckbox(
                    checked = checkedStates[item] ?: false,
                    onCheckedChange = { isChecked ->
                        checkedStates[item] = isChecked

                        onSelectionChange(checkedStates.filterValues { it }.keys.toList())
                    }
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = item,
                    style = TextStyle(
                        fontFamily = poppins,
                        fontSize = 16.sp
                    )
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayCheckBoxPreview(){
    GriyaBugarTheme {
        DayCheckBox(
            selectedDays = listOf("Kamis", "Sabtu"),
            onSelectionChange = {}
        )
    }
}