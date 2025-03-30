package com.example.meditake

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.db.ToTake
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ToTakeItem(
    item: ToTake,
    onDelete: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onShowDetails: () -> Unit,
    onIncreaseTookOnTime: () -> Unit,
    onSendNotification: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm, dd/MM", Locale.ENGLISH).format(item.createdAt),
                fontSize = 10.sp,
                color = Color.LightGray
            )

            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )

            Text(
                text = "Dose: ${item.dose}",
                fontSize = 16.sp,
                color = Color.White
            )

            Text(
                text = "Dosage Time: ${item.dosageTime}",
                fontSize = 14.sp,
                color = Color.White
            )

            item.mealVar?.let {
                Text(
                    text = "Meal: $it",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            Text(
                text = "Route of Administration: ${item.routeOfAdministration}",
                fontSize = 14.sp,
                color = Color.White
            )


                Text(
                    text = "Remaining Doses: ${item.remainingDoses}",
                    fontSize = 14.sp,
                    color = Color.White
                )

            // Add condition to show "Uzupełnij dawkę" when remaining doses is 5 or less
            if (item.remainingDoses in 1..8) {
                Text(
                    text = "Uzupełnij dawkę",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (item.isPrescription && item.remainingDoses in 0..12) {
                Text(
                    text = "Uzupełnij dawkę",
                    fontSize = 14.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Przycisk do zwiększania TookOnTime
                Button(
                    onClick = onIncreaseTookOnTime,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Increase TookOnTime")
                }

                Button(
                    onClick = onShowDetails, // Otwórz szczegóły po kliknięciu
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Show Details")
                }
                Button(
                    onClick = onSendNotification,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Notify")
                }
            }

            androidx.compose.material3.Checkbox(
                checked = item.isChecked,
                onCheckedChange = { isChecked ->
                    onCheckedChange(isChecked)
                }
            )
        }
    }
}

