package com.griya.griyabugar.ui.components.dropdown

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.griya.griyabugar.data.model.DataTerapis
import com.griya.griyabugar.ui.theme.GreenColor5
import com.griya.griyabugar.ui.theme.GriyaBugarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTerapis(
    options: List<DataTerapis> = emptyList(),
    selectedText: String,
    onChange: (name: String, id: String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    val customColors = TextFieldDefaults.colors(
        focusedIndicatorColor = GreenColor5,
        unfocusedIndicatorColor = GreenColor5,
        focusedLabelColor = GreenColor5,
        unfocusedLabelColor = GreenColor5,
        cursorColor = GreenColor5,
        focusedTextColor = GreenColor5,
        unfocusedTextColor = GreenColor5
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih Terapis") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor(),
            colors = customColors
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.nama, color = GreenColor5) },
                    onClick = {
                        onChange(item.nama, item.id.toString())
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DropDownTerapisPreview(){
    GriyaBugarTheme {
        DropDownTerapis(
            selectedText = "Rini",
            onChange = {name, id ->}
        )
    }
}