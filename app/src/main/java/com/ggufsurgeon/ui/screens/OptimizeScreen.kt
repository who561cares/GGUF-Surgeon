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
fun OptimizeScreen(vm: MainViewModel) {
    val modelPath = remember { mutableStateOf(vm.selectedPath) }
    val quant = remember { mutableStateOf("Q4_K_M") }
    val dropPrefixes = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Model Optimization / Slimming")
        Text(vm.statusMessage)

        OutlinedTextField(modelPath.value, { modelPath.value = it }, label = { Text("Model Path") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(quant.value, { quant.value = it }, label = { Text("Target Quantization") })
        OutlinedTextField(
            dropPrefixes.value,
            { dropPrefixes.value = it },
            label = { Text("Tensor prefixes to drop (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            vm.optimize(
                modelPath.value,
                quant.value,
                dropPrefixes.value.split(',').map { it.trim() }.filter { it.isNotBlank() }
            )
        }) {
            Text("Optimize Model")
        }
    }
}
