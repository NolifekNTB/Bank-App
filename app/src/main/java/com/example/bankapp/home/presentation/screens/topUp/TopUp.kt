package com.example.bankapp.home.presentation.screens.topUp

import android.annotation.SuppressLint
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


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopUpScreen(topUpViewModel: TopUpViewModel, onNavigate: (String) -> Unit) {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEFF3))
            .padding(16.dp)
    ) {
        Text(
            text = "Top Up The Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Choose your preferred method",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        PaymentOptionSection(title = "E-Payment") {
            PaymentOption(icon = R.drawable.ic_paypal, text = "PayPal") { method ->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
            PaymentOption(icon = R.drawable.ic_google_pay, text = "Google Pay") { method->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
            PaymentOption(icon = R.drawable.ic_trustly, text = "Trustly") { method ->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
            PaymentOption(icon = R.drawable.ic_other_payment, text = "Other E-Payment") { method ->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PaymentOptionSection(title = "Credit Card") {
            PaymentOption(icon = R.drawable.ic_mastercard, text = "MasterCard"){ method ->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
            PaymentOption(icon = R.drawable.ic_unionpay, text = "Union Pay") { method ->
                topUpViewModel.handleIntent(TopUpIntent.SelectMethod(method))
                onNavigate()
            }
        }
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