package com.example.bankapp.home.presentation.Transactions.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.home.presentation.Transactions.TransactionsViewModel
import com.example.bankapp.home.presentation.Transactions.mvi.HomeThreeeScreensIntent
import com.example.bankapp.home.presentation.Transactions.mvi.TransactionsIntent
import com.example.bankapp.home.presentation.Transactions.mvi.TransactionsState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransactionsFirst(
    transactionsViewModel: TransactionsViewModel,
    whichScreen: String,
    onNavigate: (String) -> Unit
) {
    LaunchedEffect(key1 = whichScreen) {
        transactionsViewModel.handleIntent(TransactionsIntent.SelectScreen(whichScreen))
    }

    Scaffold(
        topBar = { TopUpTopBar{ route -> onNavigate(route) }}
    ) {
        TopUpContent(transactionsViewModel) { onNavigate("") }
    }
}

@Composable
fun TopUpTopBar(onNavigate: (String) -> Unit) {
     TopAppBar(
        title = { Text("Top Up") },
        navigationIcon = {
            IconButton(onClick = { onNavigate("back") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        backgroundColor = Color(0xFF6200EE)
    )
}

@Composable
fun TopUpContent(
    transactionsViewModel: TransactionsViewModel,
    onNavigate: () -> Unit
) {
    val state = transactionsViewModel.state.collectAsState().value

    if (state.titles.isNotEmpty() && state.drawables.isNotEmpty() && state.texts.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDEFF3))
                .padding(16.dp)
        ) {
            TopUpHeader(state.chosenScreen)
            TopUpOptions(state, transactionsViewModel, onNavigate)
        }
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun TopUpHeader(chosenScreen: String?) {
    Column {
        Text(
            text = if (chosenScreen == "Transfer") "Transfer Money" else "Top Up The Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = if (chosenScreen == "Transfer") "Choose your preferred person" else "Choose your preferred method",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun TopUpOptions(
    state: TransactionsState,
    transactionsViewModel: TransactionsViewModel,
    onNavigate: () -> Unit
) {
    state.titles.forEachIndexed { index, title ->
        if (index < state.drawables.size && index < state.texts.size) {
            PaymentOptionSection(title) {
                state.drawables[index].forEachIndexed { drawableIndex, drawable ->
                    PaymentOption(
                        icon = drawable,
                        text = state.texts[index][drawableIndex]
                    ) { method ->
                        if (state.chosenScreen != "Transfer" || index == 0) {
                            transactionsViewModel.handleIntent(TransactionsIntent.SelectMethod(method))
                            onNavigate()
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PaymentOptionSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun PaymentOption(
    icon: Int,
    text: String,
    onNavigate: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onNavigate(text) },
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
