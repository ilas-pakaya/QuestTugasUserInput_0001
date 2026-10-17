package com.example.tugas5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun BiodataScreen() {
    Scaffold(containerColor = colorResource(R.color.screen_background)) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(R.drawable.Assasin),
                contentDescription = stringResource(R.string.cd_header_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.header_height))
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.card_top_offset)),
                color = colorResource(R.color.surface_card),
                shape = RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.card_corner),
                ),
                shadowElevation = dimensionResource(R.dimen.card_elevation)
            ) {
                FormContent()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormContent() {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var experience by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val skillOptions = stringArrayResource(R.array.skill_list)
    val experienceOptions = stringArrayResource(R.array.experience_list)
    val selectedSkills = remember { mutableStateListOf<String>() }
    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner))
    val spacing = dimensionResource(R.dimen.spacing_medium)

    val isFormValid = firstName.isNotBlank() && lastName.isNotBlank() &&
            email.isNotBlank() && birthDate.isNotBlank() &&
            experience.isNotBlank() && selectedSkills.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.card_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.form_title),
            fontSize = spResource(R.dimen.text_title),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.purple_title)
        )
        Text(
            text = stringResource(R.string.form_subtitle),
            fontSize = spResource(R.dimen.text_subtitle),
            color = colorResource(R.color.text_subtitle),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.spacing_small))
        )
        Spacer(
            Modifier.height(dimensionResource(R.dimen.spacing_large))
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text(stringResource(R.string.hint_first_name)) },
                shape = fieldShape,
                colors = fieldColors(),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(spacing))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(stringResource(R.string.hint_email)) },
            shape = fieldShape,
            colors = fieldColors(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(spacing))
        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            OutlinedTextField(
                value = birthDate,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(stringResource(R.string.hint_birth_date)) },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_calender),
                            contentDescription = stringResource(R.string.cd_calendar),
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
                        )
                    }
                },
                shape = fieldShape,
                colors = fieldColors(),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = experience,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text(stringResource(R.string.hint_experience)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    shape = fieldShape,
                    colors = fieldColors(),
                    singleLine = true,
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    experienceOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                experience = option
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(spacing))

        Text(
            text = stringResource(R.string.label_skill),
            fontWeight = FontWeight.Bold,
            fontSize = spResource(R.dimen.text_label),
            color = colorResource(R.color.text_primary),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            skillOptions.forEach { skill ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = skill in selectedSkills,
                        onCheckedChange = {
                            if (it) selectedSkills.add(skill) else selectedSkills.remove(skill)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.purple_title),
                            uncheckedColor = colorResource(R.color.border_field),
                            checkmarkColor = colorResource(R.color.button_text)
                        )
                    )
                    Text(
                        text = skill,
                        fontSize = spResource(R.dimen.text_checkbox),
                        color = colorResource(R.color.text_primary)
                    )
                }
            }
        }
        Spacer(Modifier.height(spacing))

        Button(
            onClick = { },
            enabled = isFormValid,
            shape = fieldShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.button_enabled),
                contentColor = colorResource(R.color.button_text),
                disabledContainerColor = colorResource(R.color.button_disabled),
                disabledContentColor = colorResource(R.color.button_text)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_height))
        ) {
            Text(stringResource(R.string.btn_proceed), fontWeight = FontWeight.Bold)
        }
    }

@Composable
fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = colorResource(R.color.purple_title),
    unfocusedBorderColor = colorResource(R.color.border_field),
    focusedTextColor = colorResource(R.color.text_primary),
    unfocusedTextColor = colorResource(R.color.text_primary),
    focusedContainerColor = colorResource(R.color.surface_card),
    unfocusedContainerColor = colorResource(R.color.surface_card)
)

