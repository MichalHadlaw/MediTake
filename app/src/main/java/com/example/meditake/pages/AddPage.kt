package com.example.meditake.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.ToTakeViewModel


@Composable
fun AddPage(viewModel: ToTakeViewModel) {
    val toTakeList by viewModel.toTakeList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = inputText,
            onValueChange = {
                inputText = it
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = inputText,
            onValueChange = {
                inputText = it
            }
        )


        Button(
            modifier = Modifier
                .padding(8.dp),
            onClick = {
            viewModel.addToTake(inputText)
            inputText = ""
        }) {
            Text(text = "Add")
        }
    }

}