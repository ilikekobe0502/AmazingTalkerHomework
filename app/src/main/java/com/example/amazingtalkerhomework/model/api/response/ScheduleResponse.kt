package com.example.amazingtalkerhomework.model.api.response

data class ScheduleResponse(
    val available: List<Prior>,
    val booked: List<Prior>,
)
