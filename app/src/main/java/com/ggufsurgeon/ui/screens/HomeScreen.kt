package com.ggufsurgeon.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(vm: MainViewModel, onNavigate: (String) -> Unit) {
    val path = remember { mutableStateOf(vm.selectedPath) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("GGUF Model Surgeon", style = MaterialTheme.typography.headlineSmall)
        Text(vm.statusMessage)

        OutlinedTextField(
            value = path.value,
            onValueChange = {
                path.value = it
                vm.selectedPath = it
            },
            label = { Text("Model path (.gguf)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { vm.inspectModel(path.value); onNavigate("viewer") }) {
            Text("Open & Inspect Model")
        }
        Button(onClick = { onNavigate("edit") }) { Text("Metadata Editor") }
        Button(onClick = { onNavigate("merge") }) { Text("Merge LoRA") }
        Button(onClick = { onNavigate("optimize") }) { Text("Optimize / Slim") }
    }
}
