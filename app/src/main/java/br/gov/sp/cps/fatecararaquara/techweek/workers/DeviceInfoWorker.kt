package br.gov.sp.cps.fatecararaquara.techweek.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class DeviceInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Coleta os dados
        val output = Data.Builder()
            .putString("model", android.os.Build.MODEL)
            .putString("brand", android.os.Build.BRAND)
            .putString("manufacturer", android.os.Build.MANUFACTURER)
            .putString("androidVersion", android.os.Build.VERSION.RELEASE)
            .build()

        return Result.success(output)
    }
}
