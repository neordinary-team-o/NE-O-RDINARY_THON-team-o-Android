package com.example.hackerton.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.BaseScreen
import com.example.hackerton.ui.theme.HackertonTheme

@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var queryText by rememberSaveable { mutableStateOf("") }

    BaseScreen(
        title = "Hackathon",
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onErrorShown = viewModel::clearError,
        actions = {
            TextButton(onClick = viewModel::load) {
                Text("Refresh")
            }
        },
    ) { modifier ->
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AppTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        placeholder = "예: 온라인 교육 플랫폼 만족도 조사",
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (queryText.isNotEmpty()) {
                        Text(
                            text = "입력값: $queryText",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            items(uiState.items, key = { it }) { id ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(id) },
                ) {
                    Text(
                        text = "Item #$id",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HackertonTheme {
        HomeScreen(onItemClick = {})
    }
}
