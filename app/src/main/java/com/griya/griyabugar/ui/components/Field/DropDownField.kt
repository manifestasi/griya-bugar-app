package com.griya.griyabugar.ui.components.Field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
            label = { Text(label) },
            readOnly = true, // Membuat input menjadi hanya baca agar hanya bisa dipilih dari menu
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            }
        )

        // Menu Dropdown
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(false)
            }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelected(item)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}