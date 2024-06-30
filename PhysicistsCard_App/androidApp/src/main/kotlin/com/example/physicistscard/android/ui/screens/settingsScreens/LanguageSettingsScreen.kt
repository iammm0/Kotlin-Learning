package com.example.physicistscard.android.ui.screens.settingsScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun LanguageSettingsScreen(navController: NavController) {
    val languages = listOf("简体中文", "English")
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar("语言")
        Spacer(modifier = Modifier.height(16.dp))
        languages.forEach { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedLanguage = language }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = language == selectedLanguage,
                    onClick = { selectedLanguage = language }
                )
                Text(language, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
