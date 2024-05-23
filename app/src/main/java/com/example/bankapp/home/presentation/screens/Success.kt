package com.example.bankapp.home.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.example.bankapp.R


@Composable
fun TopUpSuccessScreen(
    selectedMethod: String,
    chosenAmount: Float,
    ifWorks: String,
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
        IconButton(
            onClick = { onNavigate("back") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .zIndex(1f)
        ){
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            if (ifWorks == "works") {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success Icon",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Great!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00C853)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Top Up Success",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Failure Icon",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Oops!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Top Up Failed",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            TopUpSummary(chosenAmount)
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
            PaymentMethod(selectedMethod)
        }
    }
}

@Composable
fun TopUpSummary(chosenAmount: Float) {
    Column {
        SummaryRow(label = "Amount", value = "$$chosenAmount")
        SummaryRow(label = "Admin Fee", value = "$1.00")
        SummaryRow(label = "Total", value = "$${chosenAmount+1.00f}", fontWeight = FontWeight.Bold)
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
fun PaymentMethod(selectedMethod: String) {
    val image = when(selectedMethod){
        "Paypal" -> R.drawable.ic_paypal
        "Google Pay" -> R.drawable.ic_google_pay
        "Trustly" -> R.drawable.ic_trustly
        "Other E-Payment" -> R.drawable.ic_other_payment
        "Mastercard" -> R.drawable.ic_mastercard
        "Union Pay" -> R.drawable.ic_unionpay
        else -> R.drawable.ic_google_pay
    }

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
            Text(text = selectedMethod, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "1803 1887 0623", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

