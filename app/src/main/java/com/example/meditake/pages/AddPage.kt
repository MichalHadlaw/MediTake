package com.example.meditake.pages
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.R
import com.example.meditake.ToTakeViewModel


@Composable
fun AddPage(viewModel: ToTakeViewModel) {
    val toTakeList by viewModel.toTakeList.observeAsState()
    var titleInput by remember { mutableStateOf("") }
    var doseInput by remember { mutableStateOf("") }
    var numberInPackageInput by remember { mutableStateOf("") }
    var isPrescriptionInput by remember { mutableStateOf(false) }



    var dosageTimeInput by remember { mutableStateOf("Doraźnie") }
    val dosageTimeOption = listOf("Doraźnie","6:00 ", "8:00", "10:00", "12:00","14:00","16:00 ","18:00 ","20:00 ","22:00 ","00:00 ")
    var expanded3 by remember { mutableStateOf(false) }


    var routeOfAdministrationInput by remember { mutableStateOf("Domyślna") }
    val routeOption = listOf("Domyślna", "Inne", "Doustnie", "Domięśniowo","Dożylnie","Naskórnie", "Dojelitowo")
    var expanded2 by remember { mutableStateOf(false) }


    var mealVarInput by remember { mutableStateOf("Nie Koniecznie") }
    val mealOptions = listOf("Nie koniecznie", "Inne", "Przed", "Po")
    var expanded1 by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = titleInput,
            onValueChange = {
                titleInput = it
            },
            label = { Text(text = ("Title"))}
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
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = numberInPackageInput.toString(),
            onValueChange = { numberInPackageInput= it},
            label = { Text("Quantity in Package") }
        )



        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded3 = !expanded3 }
                .padding(8.dp),
            value = dosageTimeInput,
            onValueChange = { dosageTimeInput = it },
            label = { Text("Dosage Time") } ,
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded3,
            onDismissRequest = { expanded3 = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            dosageTimeOption.forEach { option ->
                DropdownMenuItem({
                    Text(text = option)
                },
                    onClick = {
                        dosageTimeInput = option
                        expanded3 = false

                    })

            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded1 = !expanded1 }
                .padding(8.dp),
            value = mealVarInput,
            onValueChange = { mealVarInput = it },
            label = { Text("Meal (Optional)") },
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded1,
            onDismissRequest = { expanded1 = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            mealOptions.forEach { option ->
                DropdownMenuItem( {
                    Text(text = option)
                },
                    onClick = {
                    mealVarInput = option
                    expanded1 = false
                })
            }
        }


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded2 = !expanded2 }
                .padding(8.dp),
            value = routeOfAdministrationInput,
            onValueChange = { routeOfAdministrationInput = it },
            label = { Text("Route of Administration") },
            readOnly = true
        )
        DropdownMenu(expanded = expanded2,
            onDismissRequest = {expanded2 = false },
            modifier = Modifier.fillMaxWidth()
            ) {
            routeOption.forEach{ option ->
                DropdownMenuItem({
                    Text(text = option)
                },
                    onClick = {
                        routeOfAdministrationInput = option
                        expanded2 = false
                    })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Checkbox(
                checked = isPrescriptionInput,
                onCheckedChange = { isPrescriptionInput = it }
            )
            Text(text = "Is Prescription?")
        }

        Button(
            modifier = Modifier
                .padding(8.dp),
            onClick = {

                if (titleInput.isNotBlank() && doseInput.isNotBlank() && numberInPackageInput.isNotBlank()&& dosageTimeInput.isNotBlank() && mealVarInput.isNotBlank() && routeOfAdministrationInput.isNotBlank()) {
                    viewModel.addToTake(
                        titleInput,
                        doseInput,
                        numberInPackageInput.toInt(),
                        dosageTimeInput,
                        mealVarInput,
                        routeOfAdministrationInput,
                        isPrescription = isPrescriptionInput
                    )
                    titleInput = ""
                    doseInput = ""
                    numberInPackageInput = ""
                    dosageTimeInput =""
                    mealVarInput = ""
                    routeOfAdministrationInput = ""
                    isPrescriptionInput = false
                }
            }
        ) {
            Text(text = "Add")
        }


        Text(
            text = "Current Medications:",
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 18.sp
        )
        toTakeList?.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.title, fontSize = 16.sp)
                IconButton(onClick = { viewModel.deleteToTake(item.id) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }

}