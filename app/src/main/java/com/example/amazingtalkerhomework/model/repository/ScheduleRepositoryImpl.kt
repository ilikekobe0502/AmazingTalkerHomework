package com.example.amazingtalkerhomework.model.repository

import com.example.amazingtalkerhomework.model.api.ApiService
import com.example.amazingtalkerhomework.model.api.response.ScheduleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.HttpException


class ScheduleRepositoryImpl(private val apiService: ApiService) : ScheduleRepository {
    override suspend fun getSchedules(
        teacherName: String,
        time: String
    ): Flow<ScheduleResponse?> {
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["started_at"] = time
        return flowOf(apiService.getSchedules(teacherName, queryMap))
            .map { result ->
                if (!result.isSuccessful) throw HttpException(result)
                return@map result.body()
            }
    }
}