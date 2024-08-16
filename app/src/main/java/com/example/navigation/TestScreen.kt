package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navigation.ui.theme.ComposeNavigationWithDataClassArgumentsTheme

@Composable
fun TestScreen(
    screenNumber: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = screenNumber,
            modifier = modifier
        )
        Button(onClick = onNavigate) {
            Text(text = "Nav to 2nd screen")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    ComposeNavigationWithDataClassArgumentsTheme {
        TestScreen("Screen number", {})
    }
}