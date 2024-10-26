package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.work.Data

class BatteryInfoProvider(private val context: Context) {

    fun provide(): Data {
        val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryStatus?.getIntExtra("level", -1) ?: -1
        val scale = batteryStatus?.getIntExtra("scale", -1) ?: -1
        val isCharging = batteryStatus?.getIntExtra("status", -1) == 2 // STATUS_CHARGING

        val batteryLevel = if (scale > 0) (level * 100) / scale else -1

        return Data.Builder()
            .putInt("batteryLevel", batteryLevel)
            .putBoolean("isCharging", isCharging)
            .build()
    }
}
