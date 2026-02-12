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
fun MergeScreen(vm: MainViewModel) {
    val base = remember { mutableStateOf(vm.selectedPath) }
    val lora = remember { mutableStateOf("") }
    val alpha = remember { mutableStateOf("1.0") }
    val rank = remember { mutableStateOf("16") }
    val scaling = remember { mutableStateOf("1.0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("LoRA Merge")
        Text(vm.statusMessage)

        OutlinedTextField(base.value, { base.value = it }, label = { Text("Base GGUF Path") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(lora.value, { lora.value = it }, label = { Text("LoRA Adapter Path") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(alpha.value, { alpha.value = it }, label = { Text("Alpha") })
        OutlinedTextField(rank.value, { rank.value = it }, label = { Text("Rank") })
        OutlinedTextField(scaling.value, { scaling.value = it }, label = { Text("Scaling") })

        Button(onClick = {
            vm.merge(
                basePath = base.value,
                loraPath = lora.value,
                alpha = alpha.value.toFloatOrNull() ?: -1f,
                rank = rank.value.toIntOrNull() ?: -1,
                scaling = scaling.value.toFloatOrNull() ?: -1f
            )
        }) { Text("Run Merge") }
    }
}
