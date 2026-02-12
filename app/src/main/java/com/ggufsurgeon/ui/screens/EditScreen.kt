package com.ggufsurgeon.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditScreen(vm: MainViewModel) {
    val contextLength = remember { mutableStateOf("") }
    val ropeScaling = remember { mutableStateOf("") }
    val modelName = remember { mutableStateOf("") }
    val outputPath = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Safe Metadata Editing")
        Text(vm.statusMessage)

        OutlinedTextField(contextLength.value, { contextLength.value = it }, label = { Text("Context Length") })
        OutlinedTextField(ropeScaling.value, { ropeScaling.value = it }, label = { Text("RoPE Scaling") })
        OutlinedTextField(modelName.value, { modelName.value = it }, label = { Text("Model Name") })

        Button(onClick = {
            vm.updateMetadata(
                mapOf(
                    "llama.context_length" to contextLength.value,
                    "rope.scaling" to ropeScaling.value,
                    "general.name" to modelName.value
                ).filterValues { it.isNotBlank() }
            )
        }) { Text("Apply Edits") }

        OutlinedTextField(
            value = outputPath.value,
            onValueChange = { outputPath.value = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Output GGUF path") }
        )
        Button(onClick = { vm.saveEditedModel(outputPath.value) }) { Text("Save as New File") }
    }
}
