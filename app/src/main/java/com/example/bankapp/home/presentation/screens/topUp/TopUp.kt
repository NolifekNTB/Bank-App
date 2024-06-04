package com.example.bankapp.home.presentation.screens.topUp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.R
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpIntent
import com.example.bankapp.home.presentation.screens.topUp.mvi.TopUpState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopUpScreen(topUpViewModel: TopUpViewModel, whichScreen: String, onNavigate: (String) -> Unit) {
    LaunchedEffect(key1 = whichScreen) {
        topUpViewModel.handleIntent(TopUpIntent.SelectScreen(whichScreen))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Up") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("back") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = Color(0xFF6200EE)
            )
        }
    ) {
        TopUpContent(topUpViewModel) { onNavigate("") }
    }
}

@Composable
fun TopUpContent(topUpViewModel: TopUpViewModel, onNavigate: () -> Unit) {
    val state = topUpViewModel.state.collectAsState().value
    val titles = state.titles
    val drawableList = state.drawables
    val textList = state.texts

    topUpViewModel.titlesDrawablesTexts()

    if (titles.isNotEmpty() && drawableList.isNotEmpty() && textList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDEFF3))
                .padding(16.dp)
        ) {
            Text(
                text = if(state.chosenScreen == "Transfer") "Transfer Money" else "Top Up The Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = if(state.chosenScreen == "Transfer") "Choose your preferred person" else "Choose your preferred method",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            PaymentOptionSection(titles[0]) {
                repeat(drawableList[0].size) { index ->
                    PaymentOption(
                        icon = drawableList[0][index],
                        text = textList[0][index]
                    ) { method ->
                        if(state.chosenScreen != "Transfer") {
                            topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                            onNavigate()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PaymentOptionSection(titles[1]) {
                repeat(drawableList[1].size) { index ->
                    PaymentOption(
                        icon = drawableList[1][index],
                        text = textList[1][index]
                    ) { method ->
                        topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                        onNavigate()
                    }
                }
            }
        }
    } else {
        CircularProgressIndicator()
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
fun PaymentOption(icon: Int, text: String, onNavigate: (String) -> Unit) {
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
