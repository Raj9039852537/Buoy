package com.draco.buoy.utils

import android.content.ContentResolver
import android.provider.Settings
import com.draco.buoy.models.BatterySaverConstantsConfig
import com.draco.buoy.repositories.constants.BatterySaverSecureSettings

class BatterySaverManager(private val contentResolver: ContentResolver) {
    /**
     * Reset constants to default values
     */
    fun resetConstants() {
        Settings.Global.putString(
            contentResolver,
            BatterySaverSecureSettings.BATTERY_SAVER_CONSTANTS,
            null
        )
    }

    /**
     * Enable or disable low power mode
     */
    fun setLowPower(state: Boolean) {
        val intBool = if (state) 1 else 0
        Settings.Global.putInt(
            contentResolver,
            BatterySaverSecureSettings.LOW_POWER,
            intBool
        )
    }

    /**
     * Enable or disable low power sticky mode
     */
    fun setLowPowerSticky(state: Boolean) {
        val intBool = if (state) 1 else 0
        Settings.Global.putInt(
            contentResolver,
            BatterySaverSecureSettings.LOW_POWER_STICKY,
            intBool
        )
    }

    /**
     * Enable or disable low power sticky auto disable mode
     */
    fun setLowPowerStickyAutoDisableEnabled(state: Boolean) {
        val intBool = if (state) 1 else 0
        Settings.Global.putInt(
            contentResolver,
            BatterySaverSecureSettings.LOW_POWER_STICKY_AUTO_DISABLE_ENABLED,
            intBool
        )
    }

    /**
     * Set the raw battery saver constants secure setting
     */
    fun setConstantsString(constants: String) {
        Settings.Global.putString(
            contentResolver,
            BatterySaverSecureSettings.BATTERY_SAVER_CONSTANTS,
            constants
        )
    }

    /**
     * Get the raw battery saver constants secure setting
     */
    fun getConstantsString(): String? {
        return Settings.Global.getString(
            contentResolver,
            BatterySaverSecureSettings.BATTERY_SAVER_CONSTANTS
        )
    }

    /**
     * Set the battery saver constants secure setting via a config
     */
    fun setConstantsConfig(config: BatterySaverConstantsConfig) {
        setConstantsString(config.toString())
    }

    /**
     * Quick way to apply either type of config
     */
    fun apply(config: Any) {
        when (config) {
            is String -> setConstantsString(config)
            is BatterySaverConstantsConfig -> setConstantsConfig(config)
        }

        setLowPower(true)
        setLowPowerSticky(true)
        setLowPowerStickyAutoDisableEnabled(false)
    }

    /**
     * Quick way to reset everything
     */
    fun reset() {
        resetConstants()
        setLowPower(false)
        setLowPowerSticky(false)
        setLowPowerStickyAutoDisableEnabled(true)
    }
}