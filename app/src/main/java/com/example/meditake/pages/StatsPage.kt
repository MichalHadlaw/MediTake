package com.example.meditake.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.AlarmManager.AlarmItem
import com.example.meditake.AlarmManager.AndroidAlarmScheduler
import com.example.meditake.ToTakeViewModel
import java.time.LocalDateTime



@Composable
fun StatsPage(viewModel: ToTakeViewModel) {
    val toTakeList by viewModel.toTakeList.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .padding(bottom = 56.dp) // Dostosowanie dolnego marginesu, aby uniknąć zasłaniania przez navbar
    ) {
        toTakeList?.let {
            LazyColumn {
                itemsIndexed(it) { _, item ->
                    var showDialog by remember { mutableStateOf(false) } // Stan dla każdego elementu

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF6200EE))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Informacje o leku
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = "TookOnTime: ${item.tookOnTime}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Text(
                                text = "NotTaken: ${item.notTaken}",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                        // Przycisk do otwarcia wykresu
                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(text = "Show Chart")
                        }
                    }

                    // Okno dialogowe z wykresem
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = {
                                Text(text = "Stats for ${item.title}")
                            },
                            text = {
                                Column {
                                    Text(text = "TookOnTime: ${item.tookOnTime}")
                                    Text(text = "NotTaken: ${item.notTaken}")
                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Wykres słupkowy
                                    val total = item.tookOnTime + item.notTaken
                                    if (total > 0) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Słupek "Taken" - niebieski
                                            Box(
                                                modifier = Modifier
                                                    .weight((item.tookOnTime.toFloat() / total).coerceAtLeast(0.01f))
                                                    .height(24.dp)
                                                    .background(Color.Blue)
                                            )
                                            // Słupek "Not Taken" - czerwony
                                            Box(
                                                modifier = Modifier
                                                    .weight((item.notTaken.toFloat() / total).coerceAtLeast(0.01f))
                                                    .height(24.dp)
                                                    .background(Color.Red)
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = "No Data Available",
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            },
                            confirmButton = {
                                Button(onClick = { showDialog = false }) {
                                    Text(text = "Close")
                                }
                            }
                        )
                    }
                }
            }
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No Items Yet",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}