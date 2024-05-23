package com.example.bankapp.home.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankapp.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ThirdTopUpScreen(selectedMethod: String, chosenAmount: Float, onNavigate: (String, String) -> Unit) {
    val topUpViewModel: TopUpViewModel = getViewModel()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Up") },
                navigationIcon = {
                    IconButton(onClick = { onNavigate("back", "") }) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDEFF3))
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ConfirmationSection(selectedMethod, chosenAmount)
            Spacer(modifier = Modifier.weight(1f))
            ContinueButton(){ route ->
                scope.launch {
                    topUpViewModel.updateUserAccount(chosenAmount.toDouble()) { success ->
                        if (success) {
                            onNavigate(route, "works")
                        } else {
                            onNavigate(route, "not")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ChangeAmountButton()
        }
    }
}

@Composable
fun ConfirmationSection(selectedMethod: String, chosenAmount: Float) {
    val image = when(selectedMethod){
        "Paypal" -> R.drawable.ic_paypal
        "Google Pay" -> R.drawable.ic_google_pay
        "Trustly" -> R.drawable.ic_trustly
        "Other E-Payment" -> R.drawable.ic_other_payment
        "Mastercard" -> R.drawable.ic_mastercard
        "Union Pay" -> R.drawable.ic_unionpay
        else -> R.drawable.ic_google_pay
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Blue, CircleShape)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Top up Money",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "$$chosenAmount",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        DetailRow(label = "Account", value = chosenAmount)
        DetailRow(label = "Admin Fee", value = 1.00f)
        DetailRow(label = "Total", value = chosenAmount+1.00f)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Top Up Method",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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
}

@Composable
fun DetailRow(label: String, value: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Gray)
        Text(text = "$$value", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ContinueButton(onNavigate: (String) -> Unit) {
    Button(
        onClick = { onNavigate("topUp4") },
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

@Composable
fun ChangeAmountButton() {
    Text(
        text = "Change Amount",
        fontSize = 16.sp,
        color = Color.Blue,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle change amount */ }
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}
