package com.nielsmasdorp.tilewizard.presentation.settings

import android.content.Context
import android.content.pm.PackageManager
import android.content.ComponentName
import com.nielsmasdorp.domain.TileManager
import com.nielsmasdorp.tilewizard.presentation.tiles.*

class QsTileManager(private val context: Context) : TileManager {

    override fun enableTile(id: Int, enabled: Boolean) {
        if (enabled) {
            context.packageManager.setComponentEnabledSetting(
                mapPosToName(id),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else
            context.packageManager.setComponentEnabledSetting(
                mapPosToName(id),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
    }

    private fun mapPosToName(pos: Int): ComponentName {
        val clazz = when (pos) {
            0 -> FirstTileWizardService::class.java
            1 -> SecondTileWizardService::class.java
            2 -> ThirdTileWizardService::class.java
            3 -> FourthTileWizardService::class.java
            else -> FifthTileWizardService::class.java
        }
        return ComponentName(context, clazz)
    }
}