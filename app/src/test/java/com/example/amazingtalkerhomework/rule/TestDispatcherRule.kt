package com.example.amazingtalkerhomework.rule

import com.example.amazingtalkerhomework.misc.provider.DispatcherProvider
import com.example.amazingtalkerhomework.misc.provider.setTestCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
    private val testCoroutineScope: CoroutineScope = TestScope(testDispatcher),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
        setTestCoroutineScope(testCoroutineScope)
        DispatcherProvider.setTestIO(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
        setTestCoroutineScope(null)
        DispatcherProvider.setTestIO(null)
    }
}