package com.ggufsurgeon.core

import com.ggufsurgeon.domain.ModelFile

class GgufValidator {
    fun validate(model: ModelFile): List<String> {
        val errors = mutableListOf<String>()

        if (model.name.isBlank()) errors += "Model name cannot be empty"
        if (model.contextLength <= 0) errors += "Context length must be > 0"
        if (model.ropeBase <= 0) errors += "RoPE base must be > 0"
        if (model.ropeScaling <= 0f) errors += "RoPE scaling must be > 0"
        if (model.tensorCount < model.tensors.size) errors += "Tensor count mismatch"

        return errors
    }
}
