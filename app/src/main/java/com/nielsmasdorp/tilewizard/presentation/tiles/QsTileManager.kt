package com.nielsmasdorp.tilewizard.presentation.tiles

import android.content.Context
import android.content.pm.PackageManager
import android.content.ComponentName
import com.nielsmasdorp.domain.TileManager

class QsTileManager(private val context: Context) : TileManager {

    override fun enableTile(id: Int, enabled: Boolean) {
        if (enabled) {
            context.packageManager.setComponentEnabledSetting(
                mapIdToName(id),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else
            context.packageManager.setComponentEnabledSetting(
                mapIdToName(id),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
    }

    private fun mapIdToName(id: Int): ComponentName {
        val clazz = when (id) {
            0 -> FirstTileWizardService::class.java
            1 -> SecondTileWizardService::class.java
            2 -> ThirdTileWizardService::class.java
            3 -> FourthTileWizardService::class.java
            else -> FifthTileWizardService::class.java
        }
        return ComponentName(context, clazz)
    }
}