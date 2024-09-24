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
    var doseInput by remember {
        mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = inputText,
            onValueChange = {
                inputText = it
            },
            label = { Text("Title") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = doseInput,
            onValueChange = {
                doseInput = it
            },
            label = { Text("Dose") }
        )


        Button(
            modifier = Modifier
                .padding(8.dp),
            onClick = {
                if (inputText.isNotBlank() && doseInput.isNotBlank()) {
                    viewModel.addToTake(inputText, doseInput) // Nowa funkcja dodawania z dawką
                    inputText = ""
                    doseInput = ""
                }
            }
        ) {
            Text(text = "Add")
        }
    }
}