package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.os.Environment
import android.os.StatFs
import androidx.work.Data

class StorageInfoProvider(private val context: Context) {

    fun provide(): Data {
        val stat = StatFs(Environment.getDataDirectory().path)

        // TODO: Recuperar informações de armazenamento para as variáveis totalStorage e availableStorage.
        val totalStorage = 0L // em MB
        val availableStorage = 0L // em MB

        return Data.Builder()
            .putLong("totalStorageMB", totalStorage)
            .putLong("availableStorageMB", availableStorage)
            .build()
    }
}
