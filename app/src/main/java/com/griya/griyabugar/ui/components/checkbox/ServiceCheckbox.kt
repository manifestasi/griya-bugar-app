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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.griya.griyabugar.data.model.DataService
import com.griya.griyabugar.ui.theme.GriyaBugarTheme
import com.griya.griyabugar.ui.theme.poppins

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceCheckBox(
    maxItem: Int,
    selectedService: List<String>,
    items: List<DataService>,
    onSelectionChange: (List<String>) -> Unit
){
    val checkedStates: MutableMap<String, Boolean> by rememberSaveable(selectedService) {
        mutableStateOf(
            items.associate { item ->
                item.id to (item.id in selectedService)
            }.toMutableMap()
        )
    }

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(58.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = maxItem
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {

                Row(
                    modifier = Modifier.width(140.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomCheckbox(
                        checked = checkedStates[item.id] ?: false,
                        onCheckedChange = { isChecked ->
                            checkedStates[item.id] = isChecked

                            onSelectionChange(checkedStates.filterValues { it }.keys.toList())
                        }
                    )

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = item.nama,
                        style = TextStyle(
                            fontFamily = poppins,
                            fontSize = 16.sp,
                        )
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultipleCheckboxPreview(){
    val data = listOf(
        DataService(
            id = "1",
            nama = "kerokan"
        ),
        DataService(
            id = "2",
            nama = "shiatsu2"
        ),
        DataService(
            id = "3",
            nama = "shiatsu3"
        ),
        DataService(
            id = "4",
            nama = "shiatsu4"
        ),
        DataService(
            id = "5",
            nama = "shiatsu5"
        ),
    )
    GriyaBugarTheme {
        ServiceCheckBox(
            maxItem = 2,
            selectedService = listOf("1", "2"),
            items = data,
            onSelectionChange = {}
        )
    }
}