package com.example.versioncontrolappcomposable.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.versioncontrolappcomposable.R
import com.example.versioncontrolappcomposable.data.screenStateAndEvents.UserAndRepoSearchUiEvents
import com.example.versioncontrolappcomposable.ui.components.BottomNavBar
import com.example.versioncontrolappcomposable.ui.components.EditTextWithButton
import com.example.versioncontrolappcomposable.ui.components.HeaderTextComponent
import com.example.versioncontrolappcomposable.ui.components.ImageAndTextComponent
import com.example.versioncontrolappcomposable.ui.navigation.Routes
import com.example.versioncontrolappcomposable.ui.viewmodel.TransactionViewModel

@Composable
fun UserScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
)
{

    //used to trigger navigation to selected fragment box
    LaunchedEffect(Unit) {
        viewModel.navigateTo.collect { route ->
            navController.navigate(route)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = "Users",
                onItemSelected = { item ->
                    viewModel.handleTransactionClick(item)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, top = 40.dp, end = 10.dp)
                .background(Color.White)
                .fillMaxSize()
        ) {
            HeaderTextComponent("Users", 18.sp)
            Spacer(modifier = Modifier.size(31.dp))

            EditTextWithButton(
                buttonText = "Search", prompt = "Search for users...", onButtonClick = {viewModel.onEvent(UserAndRepoSearchUiEvents.UsernameEntered(it))}
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                ImageAndTextComponent(R.drawable.image_search, Routes.SEARCH_INFO)
                Routes.SEARCH_INFO = "Search for users..."
            }

        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun UserScreenPreview() {
//    val navController = rememberNavController()
//    UserScreen(navController)
//}
