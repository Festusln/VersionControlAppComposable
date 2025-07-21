package com.example.versioncontrolappcomposable.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.versioncontrolappcomposable.R
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FragmentBox(fragmentTypeText : String, image: Int, modifier: Modifier = Modifier, onClick: (String)->Unit) {
    Box(
        modifier = modifier
//            .width(156.dp)
            .height(118.dp)
            .background(Color(0xFFECF5F8))
            .padding(start = 12.dp, top = 16.dp, bottom = 10.dp)
            .clickable { onClick(fragmentTypeText) }
    ) {
        Column {
            Image(modifier = Modifier.size(35.dp), painter = painterResource(image), contentDescription = null)
            Spacer(modifier = Modifier.size(41.dp))
            HeaderTextComponent(fragmentTypeText, 16.sp)
        }
    }
}


@Composable
fun BottomNavBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val items = listOf("Home", "Repositories", "Users")

    NavigationBar {
        items.forEach { item ->
            println("selected item: $selectedItem")
            println("item check: $item")
            val isSelected = selectedItem == item
            val iconRes = when (item) {
                "Home" -> if (isSelected) R.drawable.ic_home_selected else R.drawable.ic_home
                "Repositories" -> if (isSelected) R.drawable.ic_repo_selected else R.drawable.ic_repo
                "Users" -> if (isSelected) R.drawable.ic_user_selected else R.drawable.ic_user
                else -> R.drawable.ic_home
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = item,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    TextComponent(textValue = item, textSize =  10.sp, isBold = isSelected, shouldCenter = true)
                },
                selected = isSelected,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    var selectedItem by remember { mutableStateOf("Users") }
    BottomNavBar("Home", onItemSelected = {selectedItem = it})
}

