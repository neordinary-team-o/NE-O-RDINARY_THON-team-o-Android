package com.example.hackerton.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.ui.components.BaseScreen
import com.example.hackerton.ui.theme.HackertonTheme

@Composable
fun DetailScreen(
    itemId: String,
    onBack: () -> Unit,
) {
    BaseScreen(
        title = "Detail",
        onBack = onBack,
    ) { modifier ->
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Item #$itemId",
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = "Selected from Home",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    HackertonTheme {
        DetailScreen(itemId = "42", onBack = {})
    }
}
