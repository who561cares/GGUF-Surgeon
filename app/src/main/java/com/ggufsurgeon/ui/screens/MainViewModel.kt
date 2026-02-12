package com.ggufsurgeon.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ggufsurgeon.data.ModelRepository
import com.ggufsurgeon.domain.ModelFile
import com.ggufsurgeon.domain.OperationResult

class MainViewModel(
    private val repository: ModelRepository = ModelRepository()
) : ViewModel() {
    var selectedPath by mutableStateOf("")
    var currentModel by mutableStateOf<ModelFile?>(null)
    var statusMessage by mutableStateOf("Load a GGUF model to begin")

    fun inspectModel(path: String) {
        selectedPath = path
        repository.inspectModel(path)
            .onSuccess {
                currentModel = it
                statusMessage = "Model parsed: ${it.name}"
            }
            .onFailure {
                statusMessage = "Inspect failed: ${it.message}"
            }
    }

    fun updateMetadata(updates: Map<String, String>) {
        val model = currentModel ?: return
        repository.editMetadata(model, updates)
            .onSuccess {
                currentModel = it
                statusMessage = "Metadata updated (not saved yet)"
            }
            .onFailure {
                statusMessage = "Edit failed: ${it.message}"
            }
    }

    fun saveEditedModel(outputPath: String) {
        val model = currentModel ?: return
        repository.saveModel(selectedPath, model, outputPath)
            .onSuccess { statusMessage = "Saved to ${it.absolutePath}" }
            .onFailure { statusMessage = "Save failed: ${it.message}" }
    }

    fun merge(basePath: String, loraPath: String, alpha: Float, rank: Int, scaling: Float) {
        statusMessage = when (val result = repository.mergeLora(basePath, loraPath, alpha, rank, scaling)) {
            is OperationResult.Success -> "Merge complete: ${result.outputPath}"
            is OperationResult.Failure -> "Merge failed: ${result.error}"
        }
    }

    fun optimize(path: String, quant: String, drop: List<String>) {
        statusMessage = when (val result = repository.optimize(path, quant, drop)) {
            is OperationResult.Success -> "Optimization complete: ${result.outputPath}"
            is OperationResult.Failure -> "Optimization failed: ${result.error}"
        }
    }
}
