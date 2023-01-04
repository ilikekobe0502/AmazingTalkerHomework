package com.example.amazingtalkerhomework.di

import android.content.Context
import com.example.amazingtalkerhomework.model.repository.ScheduleRepository
import com.example.amazingtalkerhomework.model.repository.ScheduleRepositoryImpl
import com.example.amazingtalkerhomework.network.ApiModule
import com.example.amazingtalkerhomework.network.AuthInterceptor
import com.example.amazingtalkerhomework.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinModules {
    companion object {
        fun initKoin(app: Context) {
            startKoin {
                androidContext(app)
                modules(
                    listOf(
                        networkModule,
                        viewModelModule
                    )
                )
            }
        }
    }
}

val networkModule = module {
    single { AuthInterceptor() }
    single { ApiModule.provideOkHttpClient(get()) }
    single { ApiModule.provideRetrofit(get()) }
    single { ApiModule.provideAirStatusApi(get()) }
    factory<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}