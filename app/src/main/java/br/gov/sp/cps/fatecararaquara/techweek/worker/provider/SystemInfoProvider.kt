package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.app.ActivityManager
import android.content.Context
import androidx.work.Data

class SystemInfoProvider(private val context: Context) {

    fun provide(): Data {
        val memoryInfo = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)

        // TODO: Recuperar informações do sistema para as variáveis totalMemory e availableMemory.
        val totalMemory = memoryInfo.totalMem / (1024 * 1024) // em MB
        val availableMemory = memoryInfo.availMem / (1024 * 1024) // em MB

        return Data.Builder()
            .putLong("totalMemoryMB", totalMemory)
            .putLong("availableMemoryMB", availableMemory)
            .build()
    }
}
