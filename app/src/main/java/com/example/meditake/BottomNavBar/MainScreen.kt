package com.example.meditake.BottomNavBar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.meditake.ToTakeViewModel
import com.example.meditake.pages.AddPage
import com.example.meditake.pages.HomePage
import com.example.meditake.pages.StatsPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier){

    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home, 0),
        NavItem("Add", Icons.Default.Edit, 5),
        NavItem("Stats", Icons.Default.Star,2),

        )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed{ index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index ,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            BadgedBox(badge ={
                                if(navItem.badgeCount > 0)
                                    Badge(){
                                        Text(text = navItem.badgeCount.toString())
                                    }
                            } ) {

                            }
                            Icon(imageVector = navItem.icon, contentDescription = "Icon" )
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                    )

                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
    }

}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int){
    when(selectedIndex){
        0-> HomePage(ToTakeViewModel())
        1-> AddPage(ToTakeViewModel())
        2-> StatsPage()
    }


}