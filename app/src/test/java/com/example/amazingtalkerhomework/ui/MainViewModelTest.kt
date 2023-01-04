package com.example.amazingtalkerhomework.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.amazingtalkerhomework.misc.utils.DateUtil
import com.example.amazingtalkerhomework.model.api.ApiResult
import com.example.amazingtalkerhomework.model.api.response.ScheduleResponse
import com.example.amazingtalkerhomework.model.repository.ScheduleRepositoryImpl
import com.example.amazingtalkerhomework.rule.TestDispatcherRule
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val mockApiRepository = mockk<ScheduleRepositoryImpl>(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(mockApiRepository)
    }

    @Test
    fun getSchedule_pass() = runTest {
        // arrange
        val verifyData =
            "[{\"date\":\"2023-01-01\",\"priorList\":[]},{\"date\":\"2023-01-02\",\"priorList\":[]},{\"date\":\"2023-01-03\",\"priorList\":[]},{\"date\":\"2023-01-04\",\"priorList\":[]},{\"date\":\"2023-01-05\",\"priorList\":[{\"start\":\"2023-01-05T12:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T13:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T13:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T14:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T14:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T15:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T15:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T16:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T16:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T17:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T17:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T18:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T18:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T19:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T20:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-05T20:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T21:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T21:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T22:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T22:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T23:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-05T23:30:00Z\",\"end\":\"\",\"available\":true}]},{\"date\":\"2023-01-06\",\"priorList\":[{\"start\":\"2023-01-06T00:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T00:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T12:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T13:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T13:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T14:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T14:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T15:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T15:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T16:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T16:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T18:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T19:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T19:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T20:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T20:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T21:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T21:30:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T22:00:00Z\",\"end\":\"\",\"available\":false},{\"start\":\"2023-01-06T22:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T23:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-06T23:30:00Z\",\"end\":\"\",\"available\":true}]},{\"date\":\"2023-01-07\",\"priorList\":[{\"start\":\"2023-01-07T00:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T00:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T12:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T13:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T13:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T14:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T14:30:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T15:00:00Z\",\"end\":\"\",\"available\":true},{\"start\":\"2023-01-07T15:30:00Z\",\"end\":\"\",\"available\":true}]}]"
        val dummyResult = getDummyData()

        val dummyTeacher = ""
        val dummyOffset = 0
        val dummyTime = DateUtil.getFullTimeString()
        coEvery { mockApiRepository.getSchedules(dummyTeacher, dummyTime) } returns flow {
            emit(
                dummyResult
            )
        }

        // act
        viewModel.getSchedule(
            dummyTeacher,
            dummyTime,
            dummyOffset
        )
        runCurrent()

        // assert
        Assert.assertTrue((viewModel.scheduleResult.value is ApiResult.Success))
        Assert.assertEquals(
            verifyData,
            Gson().toJson((viewModel.scheduleResult.value as ApiResult.Success).result)
        )
    }

    @Test
    fun getSchedule_empty_pass() = runTest {
        // arrange
        val dummyTeacher = ""
        val dummyOffset = 0
        val dummyTime = DateUtil.getFullTimeString()
        coEvery { mockApiRepository.getSchedules("", dummyTime) } returns flow {
            emit(null)
        }

        // act
        viewModel.getSchedule(
            dummyTeacher,
            dummyTime,
            dummyOffset
        )
        runCurrent()

        // assert
        Assert.assertTrue((viewModel.scheduleResult.value is ApiResult.Error))
    }

    private fun getDummyData(): ScheduleResponse? {
        return Gson().fromJson(
            "{\"available\":[{\"start\":\"2023-01-05T04:30:00Z\",\"end\":\"2023-01-05T08:00:00Z\"},{\"start\":\"2023-01-05T09:30:00Z\",\"end\":\"2023-01-05T10:30:00Z\"},{\"start\":\"2023-01-05T12:30:00Z\",\"end\":\"2023-01-05T16:00:00Z\"},{\"start\":\"2023-01-06T04:30:00Z\",\"end\":\"2023-01-06T09:00:00Z\"},{\"start\":\"2023-01-06T10:30:00Z\",\"end\":\"2023-01-06T12:00:00Z\"},{\"start\":\"2023-01-06T13:00:00Z\",\"end\":\"2023-01-06T13:30:00Z\"},{\"start\":\"2023-01-06T14:30:00Z\",\"end\":\"2023-01-06T17:00:00Z\"},{\"start\":\"2023-01-07T04:30:00Z\",\"end\":\"2023-01-07T08:00:00Z\"}],\"booked\":[{\"start\":\"2023-01-05T08:00:00Z\",\"end\":\"2023-01-05T09:30:00Z\"},{\"start\":\"2023-01-05T10:30:00Z\",\"end\":\"2023-01-05T11:00:00Z\"},{\"start\":\"2023-01-05T11:30:00Z\",\"end\":\"2023-01-05T12:30:00Z\"},{\"start\":\"2023-01-05T16:00:00Z\",\"end\":\"2023-01-05T17:00:00Z\"},{\"start\":\"2023-01-06T12:00:00Z\",\"end\":\"2023-01-06T13:00:00Z\"},{\"start\":\"2023-01-06T13:30:00Z\",\"end\":\"2023-01-06T14:30:00Z\"}]}",
            ScheduleResponse::class.java
        )
    }
}