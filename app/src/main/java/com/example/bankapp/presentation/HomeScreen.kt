package com.example.bankapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.bankapp.R
import com.example.bankapp.data.model.LastTranscations
import com.example.bankapp.data.model.User
import com.example.bankapp.presentation.Intent.ViewIntent
import com.example.bankapp.presentation.Intent.ViewState
import com.example.bankapp.presentation.ViewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import io.grpc.Context


@Composable
fun HomeScreen(homeViewModel: HomeViewModel = HomeViewModel()) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid!!
    var user by remember { mutableStateOf<User?>(null) }
    var allUsers by remember { mutableStateOf<List<User>>(emptyList()) } //TODO: only friends

    LaunchedEffect(userId) {
        homeViewModel.fetchUserData(userId) { fetchedUser ->
            user = fetchedUser
        }
        allUsers = homeViewModel.fetchUserProfiles()
    }

    val state = homeViewModel.state.collectAsState().value
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
            is ViewState.Success -> {
                if(user != null){
                    GreetingHeader(user!!.name)
                    AccountBalanceSection(user!!.balance)
                    QuickSendSection(allUsers)
                    LastTransactionSection(state.transactions)
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
fun QuickSendSection(allUsers: List<User>) {
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
fun QuickSendContacts(allUsers: List<User>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Log.d("testowanie", allUsers.toString())
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
fun LastTransactionSection(transcations: List<LastTranscations>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(title = "Last Transaction", actionText = "View all")
        LastTransactionsList(transcations)
    }
}

@Composable
fun LastTransactionsList(transcations: List<LastTranscations>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(transcations.size){
            LastTransaction(
                transcations[it].name,
                transcations[it].price.toString(),
                transcations[it].timeOrPhoneNumber
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
