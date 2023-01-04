package com.example.amazingtalkerhomework.model.data

import java.io.Serializable

data class Schedule(
    val date: String,
    val priorList: ArrayList<PriorState> = arrayListOf(),
) : Serializable
