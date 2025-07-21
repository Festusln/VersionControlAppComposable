package com.example.versioncontrolappcomposable.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.versioncontrolappcomposable.R
import com.example.versioncontrolappcomposable.ui.components.BottomNavBar
import com.example.versioncontrolappcomposable.ui.components.FragmentBox
import com.example.versioncontrolappcomposable.ui.components.HeaderTextComponent
import com.example.versioncontrolappcomposable.ui.viewmodel.TransactionViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {

    //used to trigger navigation to selected fragment box
    LaunchedEffect(Unit) {
        viewModel.navigateTo.collect { route ->
            navController.navigate(route)
        }
    }

    Scaffold(
        modifier = Modifier.background(Color.White),
        bottomBar = {
            BottomNavBar(
                selectedItem = "Home",
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
                .fillMaxSize()
        ) {
            HeaderTextComponent("Home", 18.sp)
            Spacer(modifier = Modifier.size(31.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                FragmentBox(
                    "Users",
                    R.drawable.person,
                    modifier = Modifier.weight(1f)
                ) { selected ->
                    viewModel.handleTransactionClick(selected)
                }
                Spacer(modifier = Modifier.size(9.dp))
                FragmentBox(
                    "Repositories",
                    R.drawable.repo,
                    modifier = Modifier.weight(1f)
                ) { selected ->
                    viewModel.handleTransactionClick(selected)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}


