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
fun ToTakeItem(item: ToTake, onDelete : ()-> Unit,onCheckedChange: (Boolean) -> Unit) {
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


        }

        androidx.compose.material3.Checkbox(
            checked = item.isChecked,
            onCheckedChange = onCheckedChange
        )

    }
}
