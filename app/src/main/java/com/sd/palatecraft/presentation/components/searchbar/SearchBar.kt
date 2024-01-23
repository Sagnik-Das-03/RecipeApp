package com.sd.palatecraft.presentation.components.searchbar

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.sd.palatecraft.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchBar(
    label: String,
    viewModel: MainViewModel = getViewModel(),
    onSearchQueryChanged: (String) -> Unit, onClearClicked: () -> Unit,
) {
    val currentQuery by viewModel.currentQuery.collectAsState()

    OutlinedTextField(
        value = currentQuery,
        onValueChange = {
            viewModel.updateQuery(it)
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
                    viewModel.updateQuery("")
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