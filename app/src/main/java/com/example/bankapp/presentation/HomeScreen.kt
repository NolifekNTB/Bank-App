package com.example.bankapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import com.example.bankapp.R


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF514eac))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GreetingHeader()
        AccountBalanceSection()
        QuickSendSection()
        LastTransactionSection()
    }
}

@Composable
fun GreetingHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Good Morning",
                fontSize = 12.sp,
                color = Color.White
            )
            Text(
                text = "Autumn Phillips \uD83D\uDD90\uFE0F",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        NotificationIcon()
    }
}

@Composable
fun NotificationIcon() {
    Icon(
        imageVector = Icons.Default.Notifications,
        contentDescription = "Profile Icon",
        tint = Color.White,
        modifier = Modifier.size(30.dp)
    )
}

@Composable
fun AccountBalanceSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Amount Balance",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            AccountBalanceValue()
            ActionButtons()
        }
    }
}

@Composable
fun AccountBalanceValue() {
    Text(
        text = "$9,876.52",
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(text = "Top Up", color = Color(0xFF77DD77))
        ActionButton(text = "Transfer", color = Color(0xFFFFD700))
        ActionButton(text = "Withdraw", color = Color(0xFFFFA07A))
    }
}

@Composable
fun ActionButton(text: String, color: Color) {
    Button(
        onClick = { /* Action */ },
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text)
    }
}

@Composable
fun QuickSendSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(title = "Quick Send", actionText = "View all")
        QuickSendContacts()
    }
}

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Color.White
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = actionText,
                color = Color(0xFF6C63FF),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun QuickSendContacts() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickSendContact("Alex Buck", R.drawable.pictureprofile1)
        QuickSendContact("Kimberly Green", R.drawable.pictureprofile2)
        QuickSendContact("Mary French", R.drawable.pictureprofile3)
        QuickSendContact("Stephanie Lee", R.drawable.pictureprofile4)
    }
}

@Composable
fun QuickSendContact(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun LastTransactionSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(title = "Last Transaction", actionText = "View all")
        LastTransactionsList()
    }
}

@Composable
fun LastTransactionsList() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LastTransaction("PayPal", "$100", "Today, 10:00 AM")
        LastTransaction("Alex Buckmaster", "$50", "618 474-9169")
    }
}

@Composable
fun LastTransaction(title: String, amount: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Text(
            text = amount,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
