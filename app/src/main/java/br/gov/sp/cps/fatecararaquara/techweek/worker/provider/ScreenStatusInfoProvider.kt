package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.os.PowerManager
import androidx.work.Data

class ScreenStatusInfoProvider(private val context: Context) {

    fun provide(): Data {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        //TODO: Recuperar informações de tela para a variável isScreenOn.
        val isScreenOn = false

        return Data.Builder()
            .putBoolean("isScreenOn", isScreenOn)
            .build()
    }
}
