package com.example.amazingtalkerhomework.misc.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazingtalkerhomework.misc.provider.TestCoroutineScope

fun ViewModel.getViewModelScope() =
    TestCoroutineScope ?: this.viewModelScope