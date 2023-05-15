package com.nmt.film4life.application

import android.app.Application
import com.nmt.film4life.module.detailViewModelModule
import com.nmt.film4life.module.firebaseRepositoryModule
import com.nmt.film4life.module.homeViewModelModule
import com.nmt.film4life.module.retrofitModule
import com.nmt.film4life.module.signInViewModelModule
import com.nmt.film4life.module.signUpViewModelModule
import com.nmt.film4life.module.singleTonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(firebaseRepositoryModule, signInViewModelModule, retrofitModule,
                homeViewModelModule, singleTonModule, detailViewModelModule, signUpViewModelModule)
        }
    }

}