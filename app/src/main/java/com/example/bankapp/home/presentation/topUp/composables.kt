package com.example.bankapp.home.presentation.Transactions

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TopUpAppBar(onNavigate: () -> Unit) {
    TopAppBar(
        title = { Text("Top Up") },
        navigationIcon = {
            IconButton(onClick = { onNavigate() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        backgroundColor = Color(0xFF6200EE)
    )
}