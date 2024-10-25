package br.gov.sp.cps.fatecararaquara.techweek.workers

import android.content.Context
import android.content.pm.PackageManager
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class InstalledAppsWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val packageManager = applicationContext.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val installedAppsStr = apps.joinToString(",") { it.packageName }

        val output = Data.Builder()
            .putString("installedApps", installedAppsStr)
            .build()

        return Result.success(output)
    }
}
