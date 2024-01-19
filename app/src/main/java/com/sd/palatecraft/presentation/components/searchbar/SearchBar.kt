package com.sd.palatecraft.presentation.components.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SearchBar(label: String, onSearchQueryChanged: (String) -> Unit, onClearClicked: () -> Unit) {
    var query by remember { mutableStateOf("") }
    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            onSearchQueryChanged.invoke(it)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Button",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Clear,
                contentDescription = "Clear Button",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onClearClicked()
                    query = ""
                })
        },
        label = {
            Text(text = label, color = MaterialTheme.colorScheme.primary)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {

            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(50),
        textStyle = MaterialTheme.typography.titleLarge
    )
}