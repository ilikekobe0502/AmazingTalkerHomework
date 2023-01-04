package com.example.amazingtalkerhomework.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.amazingtalkerhomework.model.api.ApiResult
import com.example.amazingtalkerhomework.model.repository.ScheduleRepository
import com.example.amazingtalkerhomework.misc.extensions.getViewModelScope
import com.example.amazingtalkerhomework.misc.provider.DispatcherProvider
import com.example.amazingtalkerhomework.misc.utils.DateUtil
import com.example.amazingtalkerhomework.model.data.PriorState
import com.example.amazingtalkerhomework.model.data.Schedule
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val apiRepository: ScheduleRepository
) : ViewModel() {
    private val _scheduleResult = MutableLiveData<ApiResult<List<Schedule>>>()
    val scheduleResult: LiveData<ApiResult<List<Schedule>>> = _scheduleResult

    fun getSchedule(teacherName: String, time: String, dateOffset: Int) {
        _scheduleResult.postValue(ApiResult.loading())

        getViewModelScope().launch(DispatcherProvider.IO) {
            apiRepository.getSchedules(teacherName, time)
                .map { response ->
                    response?.let {
                        return@map it
                    } ?: kotlin.run {
                        throw Exception("Empty Result")
                    }
                }.map {
                    val combineList = arrayListOf<PriorState>()
                    it.available.forEach { prior ->
                        combineList.addAll(
                            spiltDate(
                                DateUtil.convertTimezone(prior.start),
                                DateUtil.convertTimezone(prior.end),
                                true
                            )
                        )
                    }
                    it.booked.forEach { prior ->
                        combineList.addAll(
                            spiltDate(
                                DateUtil.convertTimezone(prior.start),
                                DateUtil.convertTimezone(prior.end),
                                false
                            )
                        )
                    }

                    return@map combineList.sortedBy { it.start }
                }.map { priorStateList ->
                    val list = ArrayList<Schedule>()
                    for (i in 0..6) {
                        list.add(Schedule(date = DateUtil.getDateString(i + dateOffset)))
                    }
                    priorStateList.groupBy { item ->
                        DateUtil.getDate(item.start)
                    }.map { groupData ->
                        list.find { it.date == groupData.key }?.priorList?.addAll(groupData.value)
                    }
                    return@map list
                }
                .catch {
                    _scheduleResult.postValue(ApiResult.error(it))
                }.collect {
                    _scheduleResult.postValue(ApiResult.success(it))
                }
        }
    }

    private fun spiltDate(start: String, end: String, available: Boolean): ArrayList<PriorState> {
        val result = arrayListOf<PriorState>()
        result.add(PriorState(start, "", available))
        while (DateUtil.compareTwoDate(result.last().start, end)) {
            val startTime = DateUtil.addHalfHour(result.last().start)
            if (startTime == end) {
                break
            }

            result.add(
                PriorState(
                    start = startTime,
                    end = "",
                    available
                )
            )
        }

        return result
    }
}