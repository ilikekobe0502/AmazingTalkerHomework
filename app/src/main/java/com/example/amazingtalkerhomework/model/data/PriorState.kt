package com.example.amazingtalkerhomework.model.data

import java.io.Serializable

data class PriorState(
    val start: String,
    val end: String,
    val available: Boolean
) : Serializable
