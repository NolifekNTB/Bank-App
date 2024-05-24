package com.example.bankapp.home.presentation.screens.topUp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.R


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SecondTopUpScreen(selectedMethod: String, onNavigate: (Float) -> Unit) {
    val amount = remember { mutableFloatStateOf(50.00f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Up") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(-1.0f) }) {
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
        TopUpScreenContent(selectedMethod, amount, onNavigate)

    }
}

@Composable
fun TopUpScreenContent(selectedMethod: String, amount: MutableState<Float>, onNavigate: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDEFF3))
            .padding(16.dp)
    ) {
        BalanceSection()
        Spacer(modifier = Modifier.height(16.dp))
        AmountSection(amount)
        Spacer(modifier = Modifier.height(16.dp))
        PaymentMethodSection(selectedMethod)
        Spacer(modifier = Modifier.weight(1f))
        ContinueButton(){onNavigate(amount.value) }
    }
}

@Composable
fun BalanceSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Amount Balance",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "$9,876.52",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle refresh */ }
            )
        }
    }
}

@Composable
fun AmountSection(amount: MutableState<Float>) {
    val amounts = listOf(10.00, 50.00, 100.00)
    var selectedAmount by remember { mutableStateOf(amounts[1]) }
    amount.value = selectedAmount.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Set amount",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "How much would you like to top up?",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        Text(
            text = "$$selectedAmount",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            amounts.forEach { amount ->
                AmountOption(amount = amount, isSelected = amount == selectedAmount) {
                    selectedAmount = amount
                }
            }
        }
    }
}

@Composable
fun AmountOption(amount: Double, isSelected: Boolean, onAmountSelected: () -> Unit) {
    Text(
        text = "$$amount",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .background(if (isSelected) Color.Blue else Color.Transparent, RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onAmountSelected() }
    )
}

@Composable
fun PaymentMethodSection(selectedMethod: String) {
    val image = when (selectedMethod){
        "PayPal" -> R.drawable.ic_paypal
        "Google Pay" -> R.drawable.ic_google_pay
        "Trustly" -> R.drawable.ic_trustly
        "Other E-Payment" -> R.drawable.ic_other_payment
        "MasterCard" -> R.drawable.ic_mastercard
        "Union Pay" -> R.drawable.ic_unionpay
        else -> R.drawable.ic_paypal
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Top up method",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PaymentMethodCard(image, selectedMethod)
    }
}

@Composable
fun PaymentMethodCard(image: Int, selectedMethod: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* Handle click */ },
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "MasterCard",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "$selectedMethod\n1803 1887 0623",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ContinueButton(onNavigate: (String) -> Unit) {
    Button(
        onClick = { onNavigate("") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
    ) {
        Text(
            text = "Continue",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
