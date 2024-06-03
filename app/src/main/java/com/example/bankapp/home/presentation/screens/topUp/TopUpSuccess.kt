package com.example.bankapp.home.presentation.screens.topUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex


@Composable
fun TopUpSuccessScreen(
    topUpViewModel: TopUpViewModel,
    onNavigate: (String) -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF7B61FF), Color(0xFF5699FF))
                )
            )
            .padding(16.dp)
    ) {
        CloseButton(Modifier.align(Alignment.TopStart), onNavigate)
        SuccessContent(topUpViewModel)
    }
}

@Composable
fun CloseButton(modifier: Modifier, onNavigate: (String) -> Unit) {
    IconButton(
        onClick = { onNavigate("back") },
        modifier = modifier
            .padding(20.dp)
            .zIndex(1f)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Icon",
            tint = Color.Black,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun SuccessContent(topUpViewModel: TopUpViewModel) {
    val state = topUpViewModel.state.collectAsState()

    val ifWorks = state.value.ifWorks
    val chosenAmount = state.value.chosenAmount
    val selectedMethod = state.value.selectedMethodOrPerson

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        SuccessOrFailureIcon(ifWorks)
        Spacer(modifier = Modifier.height(16.dp))
        SuccessOrFailureText(ifWorks)
        Spacer(modifier = Modifier.height(8.dp))
        DateAndReference()
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        TopUpSummary(chosenAmount)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        PaymentMethod(topUpViewModel = topUpViewModel, selectedMethod = selectedMethod)
    }
}

@Composable
fun SuccessOrFailureIcon(ifWorks: Boolean?) {
    val icon = if (ifWorks != null && ifWorks) Icons.Default.Check else Icons.Default.Error
    Icon(
        imageVector = icon,
        contentDescription = if (ifWorks != null && ifWorks) "Success Icon" else "Failure Icon",
        modifier = Modifier.size(64.dp)
    )
}

@Composable
fun SuccessOrFailureText(ifWorks: Boolean?) {
    val (message, color) = if (ifWorks != null && ifWorks) {
        "Great!" to Color(0xFF00C853)
    } else {
        "Oops!" to Color(0xFFD32F2F)
    }
    Text(
        text = message,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = if (ifWorks != null && ifWorks) "Top Up Success" else "Top Up Failed",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DateAndReference() {
    Text(
        text = "March 03, 2024  ·  09:30:06 AM",
        fontSize = 14.sp,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "No. Ref: 03032024123456789324",
        fontSize = 14.sp,
        color = Color.Gray
    )
}

@Composable
fun TopUpSummary(chosenAmount: Float?) {
    Column {
        SummaryRow(label = "Amount", value = "$$chosenAmount")
        SummaryRow(label = "Admin Fee", value = "$1.00")
        SummaryRow(label = "Total", value = "$${chosenAmount!!+1.00f}", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SummaryRow(label: String, value: String, fontWeight: FontWeight = FontWeight.Normal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp)
        Text(text = value, fontSize = 16.sp, fontWeight = fontWeight)
    }
}

@Composable
fun PaymentMethod(topUpViewModel: TopUpViewModel, selectedMethod: String?) {
    val image = topUpViewModel.getPaymentMethodIcon()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F1F1), shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Card Icon",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = selectedMethod!!, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "1803 1887 0623", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

