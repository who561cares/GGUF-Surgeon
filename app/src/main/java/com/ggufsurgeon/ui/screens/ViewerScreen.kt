package com.ggufsurgeon.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ViewerScreen(vm: MainViewModel) {
    val model = vm.currentModel

    if (model == null) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("No model loaded")
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Name: ${model.name}")
                    Text("Architecture: ${model.architecture}")
                    Text("Context: ${model.contextLength}")
                    Text("RoPE: base=${model.ropeBase}, scaling=${model.ropeScaling}")
                    Text("Quantization: ${model.quantization}")
                    Text("Tokenizer: ${model.tokenizer}")
                    Text("Tensor count: ${model.tensorCount}")
                }
            }
        }

        item { Text("Metadata") }
        items(model.metadata.entries.toList()) { (k, v) -> Text("$k = $v") }

        item { Text("Tensors") }
        items(model.tensors) { t ->
            Text("${t.name} | ${t.shape.joinToString("x")} | ${t.type} | ${t.bytes} bytes")
        }
    }
}
