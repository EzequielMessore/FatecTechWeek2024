package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.os.Environment
import android.os.StatFs
import androidx.work.Data

class StorageInfoProvider(private val context: Context) {

    fun provide(): Data {
        val stat = StatFs(Environment.getDataDirectory().path)

        // TODO: Recuperar informações de armazenamento para as variáveis totalStorage e availableStorage.
        val totalStorage = stat.totalBytes / (1024 * 1024) // em MB
        val availableStorage = stat.availableBytes / (1024 * 1024) // em MB

        return Data.Builder()
            .putLong("totalStorageMB", totalStorage)
            .putLong("availableStorageMB", availableStorage)
            .build()
    }
}
