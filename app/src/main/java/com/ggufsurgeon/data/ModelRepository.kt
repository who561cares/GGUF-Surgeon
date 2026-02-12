package com.ggufsurgeon.data

import com.ggufsurgeon.core.GgufEditor
import com.ggufsurgeon.core.GgufParser
import com.ggufsurgeon.core.GgufValidator
import com.ggufsurgeon.core.ModelOperations
import com.ggufsurgeon.domain.ModelFile
import com.ggufsurgeon.domain.OperationResult
import java.io.File

class ModelRepository(
    private val parser: GgufParser = GgufParser(),
    private val editor: GgufEditor = GgufEditor(),
    private val validator: GgufValidator = GgufValidator(),
    private val operations: ModelOperations = ModelOperations()
) {
    fun inspectModel(path: String): Result<ModelFile> = runCatching {
        val file = File(path)
        val model = parser.parse(file)
        model.copy(tensors = parser.parseTensors(file), tensorCount = parser.parseTensors(file).size)
    }

    fun editMetadata(model: ModelFile, updates: Map<String, String>): Result<ModelFile> = runCatching {
        val updated = editor.updateMetadata(model, updates)
        val errors = validator.validate(updated)
        require(errors.isEmpty()) { errors.joinToString("; ") }
        updated
    }

    fun saveModel(originalPath: String, model: ModelFile, outputPath: String): Result<File> = runCatching {
        val errors = validator.validate(model)
        require(errors.isEmpty()) { errors.joinToString("; ") }
        editor.saveAsNewFile(File(originalPath), model, File(outputPath))
    }

    fun mergeLora(baseModelPath: String, loraPath: String, alpha: Float, rank: Int, scaling: Float): OperationResult {
        return operations.mergeLora(File(baseModelPath), File(loraPath), alpha, rank, scaling)
    }

    fun optimize(modelPath: String, targetQuantization: String, droppedPrefixes: List<String>): OperationResult {
        return operations.optimizeModel(File(modelPath), targetQuantization, droppedPrefixes)
    }
}
