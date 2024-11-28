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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.R
import com.example.meditake.ToTakeItem
import com.example.meditake.db.ToTake
import com.example.meditake.ToTakeViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomePage(viewModel: ToTakeViewModel){
    val toTakeList by viewModel.toTakeList.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding()
    ) {
        toTakeList?.let {
            LazyColumn {
                itemsIndexed(toTakeList!!) { index, item ->
                    ToTakeItem(
                        item = item,
                        onDelete = { viewModel.deleteToTake(item.id) },
                        onCheckedChange = { isChecked ->
                            viewModel.updateCheckBoxState(item.id, isChecked)
                        }
                    )
                }
            }
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Items Yet",
            fontSize = 16.sp
        )
    }
}
