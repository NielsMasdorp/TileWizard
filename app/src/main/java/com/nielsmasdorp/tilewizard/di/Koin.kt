package com.nielsmasdorp.tilewizard.di

import com.nielsmasdorp.domain.*
import com.nielsmasdorp.tilewizard.data.ApiSocketRepository
import com.nielsmasdorp.tilewizard.data.PaperSocketInfoRepository
import com.nielsmasdorp.tilewizard.data.network.ApiSocketService
import com.nielsmasdorp.tilewizard.presentation.tiles.QsTileManager
import com.nielsmasdorp.tilewizard.presentation.settings.SettingsViewModel
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val tileWizardModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            //addInterceptor(ChuckInterceptor(androidApplication()))
        }.build()
    }
    single<ApiSocketService> {
        Retrofit.Builder()
            .baseUrl("http://example.com/") // ignored, full url is used in service
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiSocketService::class.java)
    }
    single { GetSocketState(get()) }
    single { UpdateSocketState(get()) }
    single { GetSocketInfo(get()) }
    single { StoreSocketInfo(get()) }
    single<SocketInfoRepository> { PaperSocketInfoRepository() }
    single<SocketRepository> { ApiSocketRepository(get()) }
}

val settingsModule = module {
    single<TileManager> { QsTileManager(androidApplication()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
}