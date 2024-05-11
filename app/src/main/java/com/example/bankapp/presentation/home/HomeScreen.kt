package com.example.bankapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bankapp.data.model.firebase.FriendFireStore
import com.example.bankapp.data.model.realm.LastTransactionsRealm
import com.example.bankapp.presentation.IntentAndStates.ViewIntent
import com.example.bankapp.presentation.IntentAndStates.ViewState
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val state = homeViewModel.state.collectAsState().value

    Log.d("testowanie", state.toString())

    val userId = FirebaseAuth.getInstance().currentUser?.uid!!
    LaunchedEffect(key1 = userId) {
        homeViewModel.processIntent(ViewIntent.LoadData(userId))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF514eac))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (state) {
            is ViewState.Loading -> CircularProgressIndicator()
            is ViewState.DataLoaded -> {
                if(state.user != null){
                    GreetingHeader(state.user.name)
                    AccountBalanceSection(state.user.balance)
                    QuickSendSection(state.allUsers)
                    LastTransactionSection(state.user.lastTransactions)
                } else {
                    Text(text = "Loading user data")
                }
            }
            is ViewState.Error -> Text("Error: ${state.exception.message}")
        }
    }
}

@Composable
fun GreetingHeader(name: String) {
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
                text = "Autumn $name \uD83D\uDD90\uFE0F",
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
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Profile Icon",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun AccountBalanceSection(balance: Double) {
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
            AccountBalanceValue(balance)
            ActionButtons()
        }
    }
}

@Composable
fun AccountBalanceValue(balance: Double) {
    Text(
        text = "$ $balance",
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
fun QuickSendSection(allUsers: List<FriendFireStore>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(title = "Quick Send", actionText = "View all")
        QuickSendContacts(allUsers)
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
fun QuickSendContacts(allUsers: List<FriendFireStore>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if(allUsers.isNotEmpty() && allUsers.size <= 5){
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                items(allUsers.size){ index ->
                    QuickSendContact(name = allUsers[index].name, imageRes = allUsers[index].profilePicUrl)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun QuickSendContact(name: String, imageRes: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GlideImage(
            model = imageRes,
            contentScale = ContentScale.Crop,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Text(
            text = name,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun LastTransactionSection(transcations: List<LastTransactionsRealm>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(title = "Last Transaction", actionText = "View all")
        LastTransactionsList(transcations)
    }
}

@Composable
fun LastTransactionsList(transcations: List<LastTransactionsRealm>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(transcations.size){ index ->
            LastTransaction(
                title = transcations[index].name,
                amount = transcations[index].price.toString(),
                description = transcations[index].timeOrPhoneNumber
            )
        }
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
            text = "$ $amount",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}
