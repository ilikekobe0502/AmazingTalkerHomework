package com.example.amazingtalkerhomework.misc.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.amazingtalkerhomework.rule.TestDispatcherRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
internal class DateUtilTest {
    private lateinit var dateUtil: DateUtil

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Before
    fun setUp() {
        dateUtil = DateUtil
    }

    @Test
    fun getDayByFormat_pass() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val verifyData = dateFormat.format(calendar.time)

        // act
        val result = dateUtil.getDayByFormat(0, dateFormat)

        // assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun getDayOfWeekString_pass() {
        // arrange
        val dateFormat = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val verifyData = dateFormat.format(calendar.time)

        // act
        val result = dateUtil.getDayOfWeekString()

        // assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun isBeforeToday_pass() {
        // arrange
        val calendar = Calendar.getInstance()
        val dayOfWeek: Int = calendar.get(Calendar.DAY_OF_WEEK)
        val dummyData = 5

        // act
        val result = dateUtil.isBeforeToday(dummyData)

        //assert
        if (dummyData < dayOfWeek) {
            Assert.assertTrue(result)
        } else {
            Assert.assertFalse(result)
        }
    }

    @Test
    fun getTodayOfWeekString_pass() {
        // arrange
        val dateFormat = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        val verifyData = dateFormat.format(calendar.time)

        // act
        val result = dateUtil.getTodayOfWeekString()

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun getDateString_pass() {
        // arrange
        val dummyOffset = 3
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.add(Calendar.DATE, dummyOffset)
        val verifyData = dateFormat.format(calendar.time)

        // act
        val result = dateUtil.getDateString(dummyOffset)

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun getDisplayTimezone_pass() {
        // arrange
        val timezone = TimeZone.getDefault()
        val verifyData =
            timezone.id.toString() + ": " + timezone.getDisplayName(false, TimeZone.SHORT)

        // act
        val result = dateUtil.getDisplayTimezone()

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun getFullTimeString_pass() {
        // arrange
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val verifyData = dateFormat.format(calendar.time)

        // act
        val result = dateUtil.getFullTimeString()

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun convertTimezone_pass() {
        // arrange
        val dummyTimeStr = "2023-01-04T16:00:00Z"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val parsed = dateFormat.parse(dummyTimeStr)
        dateFormat.timeZone = TimeZone.getTimeZone("JST")

        mockkStatic(TimeZone::class)
        every { TimeZone.getDefault() } returns dateFormat.timeZone

        val verifyData = dateFormat.format(parsed)

        // act
        val result = dateUtil.convertTimezone(dummyTimeStr)

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun addHalfHour_pass() {
        // arrange
        val dummyTimeStr = "2023-01-04T16:00:00Z"
        val verifyData = "2023-01-04T16:30:00Z"

        // act
        val result = dateUtil.addHalfHour(dummyTimeStr)

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun compareTwoDate_pass() {
        // arrange
        val dummyTimeStr = "2023-01-04T16:00:00Z"
        val dummyTimeStr2 = "2023-01-04T16:30:00Z"

        // act
        val result = dateUtil.compareTwoDate(dummyTimeStr, dummyTimeStr2)

        //assert
        Assert.assertTrue(result)
    }

    @Test
    fun getHourAndMinutes_pass() {
        // arrange
        val dummyTimeStr = "2023-01-04T16:00:00Z"
        val verifyData = "16:00"

        // act
        val result = dateUtil.getHourAndMinutes(dummyTimeStr)

        //assert
        Assert.assertEquals(verifyData, result)
    }

    @Test
    fun getDate_pass() {
        // arrange
        val dummyTimeStr = "2023-01-04T16:00:00Z"
        val verifyData = "2023-01-04"

        // act
        val result = dateUtil.getDate(dummyTimeStr)

        //assert
        Assert.assertEquals(verifyData, result)
    }
}