package com.example.meditake.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.R
import com.example.meditake.ToTakeItem
import com.example.meditake.db.ToTake
import com.example.meditake.ToTakeViewModel
import com.example.meditake.notifications.ToTakeNotificationService
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomePage(viewModel: ToTakeViewModel) {
    val context = LocalContext.current
    val service = ToTakeNotificationService(context)
    val toTakeList by viewModel.toTakeList.observeAsState()
    var selectedItem by remember { mutableStateOf<ToTake?>(null) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding()
    ) {
        toTakeList?.let { items ->
            LazyColumn {
                itemsIndexed(items) { _, item ->
                    ToTakeItem(
                        item = item,
                        onDelete = { viewModel.deleteToTake(item.id) },
                        onCheckedChange = { isChecked -> viewModel.updateCheckBoxState(item.id, isChecked) },
                        onShowDetails = { selectedItem = item },
                        onIncreaseTookOnTime = { viewModel.increaseTookOnTimeForItem(item.id) },
                        onSendNotification = { service.scheduleNotificationAfterMinutes(item.title) }
                    )
                }
            }
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Items Yet",
            fontSize = 16.sp
        )

        // Wyświetlenie dialogu, jeśli wybrano element
        selectedItem?.let { item ->
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { selectedItem = null },
                modifier = Modifier.fillMaxWidth(),
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Title: ${item.title}",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Dose: ${item.dose}",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Dosage Time: ${item.dosageTime}",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Meal: ${item.mealVar ?: "N/A"}",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Route of Administration: ${item.routeOfAdministration}",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Remaining Doses: ${item.remainingDoses}",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            // Warunek do wyświetlania "Uzupełnij dawkę"
                            if (item.remainingDoses == 5) {
                                Text(
                                    text = "  |  Uzupełnij dawkę",
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Text(
                            text = "Created At: ${
                                SimpleDateFormat("HH:mm, dd/MM", Locale.ENGLISH).format(item.createdAt)
                            }",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { selectedItem = null },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Close", fontSize = 20.sp)
                    }
                }
            )
        }
    }
}

