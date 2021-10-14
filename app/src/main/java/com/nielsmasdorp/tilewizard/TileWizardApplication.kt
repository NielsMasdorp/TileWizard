package com.nielsmasdorp.tilewizard

import android.app.Application
import com.nielsmasdorp.tilewizard.di.settingsModule
import com.nielsmasdorp.tilewizard.di.tileWizardModule
import io.paperdb.Paper
import org.koin.android.ext.android.startKoin

class TileWizardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        startKoin(
            androidContext = this,
            modules = listOf(tileWizardModule, settingsModule)
        )
    }
}