package com.example.amazingtalkerhomework.model.repository

import com.example.amazingtalkerhomework.model.api.response.ScheduleResponse
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun getSchedules(teacherName: String, time: String): Flow<ScheduleResponse?>
}