package com.ggufsurgeon.domain

data class ModelFile(
    val name: String,
    val architecture: String,
    val contextLength: Int,
    val ropeBase: Float,
    val ropeScaling: Float,
    val quantization: String,
    val tensorCount: Int,
    val tokenizer: String,
    val metadata: Map<String, String>,
    val tensors: List<TensorInfo>
)

data class TensorInfo(
    val name: String,
    val shape: List<Int>,
    val type: String,
    val bytes: Long
)
