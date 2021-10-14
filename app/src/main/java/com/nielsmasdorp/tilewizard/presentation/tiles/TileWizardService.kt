package com.nielsmasdorp.tilewizard.presentation.tiles

import android.os.Build
import android.service.quicksettings.Tile.*
import android.service.quicksettings.TileService
import com.nielsmasdorp.domain.*
import com.nielsmasdorp.domain.SocketState.*
import com.nielsmasdorp.tilewizard.R
import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

open class TileWizardService(private val id: Int) : TileService(),
    KoinComponent {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private val getSocketState: GetSocketState by inject()

    private val updateSocketState: UpdateSocketState by inject()

    private val getSocketInfo: GetSocketInfo by inject()

    override fun onStartListening() {
        scope.launch {
            val info = getSocketInfo(id = id)
            updateTile(getSocketState(ip = info.ip), info)
        }
    }

    override fun onStopListening() = scope.cancel()

    override fun onClick() {
        scope.launch {
            val info = getSocketInfo(id = id)
            val result = when (qsTile.state) {
                STATE_ACTIVE -> updateSocketState(info.ip, OFF)
                STATE_INACTIVE -> updateSocketState(info.ip, ON)
                else -> UNAVAILABLE
            }
            updateTile(result, info)
        }
    }

    private fun updateTile(state: SocketState, info: SocketInfo) {
        with(qsTile) {
            this.state = when (state) {
                ON -> STATE_ACTIVE
                OFF -> STATE_INACTIVE
                UNAVAILABLE -> STATE_UNAVAILABLE
            }
            this.label = info.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.subtitle = when (state) {
                    ON -> getString(R.string.on)
                    OFF -> getString(R.string.off)
                    UNAVAILABLE -> getString(R.string.offline)
                }
            }
            updateTile()
        }
    }
}

class FirstTileWizardService : TileWizardService(id = 0)

class SecondTileWizardService : TileWizardService(id = 1)

class ThirdTileWizardService : TileWizardService(id = 2)

class FourthTileWizardService : TileWizardService(id = 3)

class FifthTileWizardService : TileWizardService(id = 4)