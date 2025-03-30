package com.example.meditake.pages
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meditake.AlarmManager.AlarmItem
import com.example.meditake.AlarmManager.AndroidAlarmScheduler
import com.example.meditake.R
import com.example.meditake.notifications.ToTakeNotificationService
import com.example.meditake.ToTakeViewModel
import com.example.meditake.db.ToTake
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun AddPage(viewModel: ToTakeViewModel) {
    val context = LocalContext.current
    val service = ToTakeNotificationService(context)
    val toTakeList by viewModel.toTakeList.observeAsState()
    var titleInput by remember { mutableStateOf("") }
    var doseInput by remember { mutableStateOf("") }
    var numberInPackageInput by remember { mutableStateOf("") }
    var isPrescriptionInput by remember { mutableStateOf(false) }
    var dosageTimeInput by remember { mutableStateOf("Doraźnie") }
    val dosageTimeOption = listOf("Doraźnie","6:00", "8:00", "10:00", "12:00","14:00","16:00","18:00","20:00","22:00","00:00")
    var expanded3 by remember { mutableStateOf(false) }
    var routeOfAdministrationInput by remember { mutableStateOf("Domyślna") }
    val routeOption = listOf("Domyślna", "Inne", "Doustnie", "Domięśniowo","Dożylnie","Naskórnie", "Dojelitowo")
    var expanded2 by remember { mutableStateOf(false) }
    var mealVarInput by remember { mutableStateOf("Nie Koniecznie") }
    val mealOptions = listOf("Nie koniecznie", "Inne", "Przed", "Po")
    var expanded1 by remember { mutableStateOf(false) }


    var secondsText by remember { mutableStateOf("")}
    val scheduler = AndroidAlarmScheduler(context)
    var alarmItem: AlarmItem? = null

    LaunchedEffect(context) {
        viewModel.loadDrugNamesFromTXT(context) // Załadowanie leków z pliku
    }

    // Lista sugerowanych nazw leków
    var filteredDrugNames by remember { mutableStateOf(viewModel.drugNames) }

    // Filtrowanie nazw leków na podstawie wpisywanego tekstu
    fun filterSuggestions(query: String) {
        filteredDrugNames = viewModel.drugNames.filter {
            it.contains(query, ignoreCase = true)
        }.take(5)
    }
    var expandedMenu by remember { mutableStateOf(false) }

    // Sprawdzamy czy pole tekstowe ma wartość i czy są jakieś sugestie do wyświetlenia
    LaunchedEffect(titleInput) {
        expandedMenu = titleInput.isNotBlank() && filteredDrugNames.isNotEmpty()
    }
    val outsideClickModifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                expandedMenu = false
            }
        )
    }

    fun calculateAlarmTime(selectedTime: String): LocalDateTime? {
        if (selectedTime == "Doraźnie") return null // Brak alarmu, jeśli "Doraźnie"

        // Format godziny
        val timeParts = selectedTime.split(":")
        val hour = timeParts[0].toInt()
        val minute = timeParts[1].toInt()

        val now = LocalDateTime.now()
        var alarmTime = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)

        // Jeśli czas alarmu już minął dzisiaj, ustawiamy go na następny dzień
        if (alarmTime.isBefore(now)) {
            alarmTime = alarmTime.plusDays(1)
        }

        // Logowanie ustawionej godziny alarmu
        Log.d("AddPage", "USTAWIONO ALARM NA GODZINĘ: ${alarmTime.format(DateTimeFormatter.ofPattern("HH:mm"))}")

        return alarmTime
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = titleInput,
            onValueChange = {
                titleInput = it
                filterSuggestions(it) // Filtruj nazw leków podczas wpisywania
            },
            label = { Text(text = "Title") }
        )
            IconButton(
                onClick = { expandedMenu = !expandedMenu },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Toggle suggestions")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(outsideClickModifier)  // Dodajemy nasłuchiwacz kliknięć poza menu
        ) {
            if (expandedMenu) {
                LazyColumn(
                    modifier = Modifier
                        .widthIn(min = 0.dp, max = 320.dp)
                        .heightIn(max = 400.dp) // Określenie maksymalnej wysokości
                        .padding(8.dp)
                ) {
                    items(filteredDrugNames) { suggestion ->
                        Text(
                            text = suggestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    titleInput =
                                        suggestion  // Ustawia nazwę leku na klikniętą sugestię
                                    expandedMenu = false // Zamknięcie menu po kliknięciu w sugestię
                                }
                        )
                    }
                }
            }
        }

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
            value = numberInPackageInput,
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
            Text(text = "Na Receptę", fontSize = 16.sp)
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


                    val alarmTime = calculateAlarmTime(dosageTimeInput)
                    if (alarmTime != null) {
                        val alarmItem = AlarmItem(time = alarmTime)
                        scheduler.schedjule(alarmItem)
                    }



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

                IconButton(onClick = { viewModel.resetRemainingDoses(item.id, item.numberInPackage) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                        contentDescription = "Reset Remaining Doses"
                    )
                }

            }
        }
    }

}


