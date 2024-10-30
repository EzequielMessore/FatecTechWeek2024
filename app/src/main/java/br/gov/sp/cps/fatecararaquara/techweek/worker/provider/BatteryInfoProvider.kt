package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.Data

class BatteryInfoProvider(private val context: Context) {

    fun provide(): Data {
        val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        // TODO: Recuperar informações de bateria para as variáveis isCharging e batteryLevel.
        val isCharging = false
        val batteryLevel = 0

        return Data.Builder()
            .putInt("batteryLevel", batteryLevel)
            .putBoolean("isCharging", isCharging)
            .build()
    }
}
