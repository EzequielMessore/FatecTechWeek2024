package br.gov.sp.cps.fatecararaquara.techweek.workers

import android.app.ActivityManager
import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class SystemInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Coleta informações do sistema
        val memoryInfo = ActivityManager.MemoryInfo()
        val activityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memoryInfo)

        val totalMemory = memoryInfo.totalMem / (1024 * 1024) // em MB
        val availableMemory = memoryInfo.availMem / (1024 * 1024) // em MB

        val output = Data.Builder()
            .putLong("totalMemoryMB", totalMemory)
            .putLong("availableMemoryMB", availableMemory)
            .build()

        return Result.success(output)
    }
}
