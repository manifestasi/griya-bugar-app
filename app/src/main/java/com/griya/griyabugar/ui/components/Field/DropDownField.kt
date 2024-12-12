package com.griya.griyabugar.ui.components.Field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(
    selectedValue: String = "",
    label: String,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    onSelected: (selected: String) -> Unit
){
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onExpandedChange(expanded)
        }
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {}, // Tidak digunakan karena nilai hanya diubah saat memilih item
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true, // Membuat input menjadi hanya baca agar hanya bisa dipilih dari menu
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            placeholder = { Text(label) }
        )

        // Menu Dropdown
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(expanded)
            }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelected(item)
                        onExpandedChange(expanded)
                    }
                )
            }
        }
    }
}