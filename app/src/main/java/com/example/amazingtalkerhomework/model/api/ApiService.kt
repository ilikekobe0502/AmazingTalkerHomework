package com.example.amazingtalkerhomework.model.api

import com.example.amazingtalkerhomework.model.api.response.ScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET("guest/teachers/{name}/schedule")
    suspend fun getSchedules(
        @Path("name") teacherName: String,
        @QueryMap queryMap: HashMap<String, String>
    ): Response<ScheduleResponse>

}