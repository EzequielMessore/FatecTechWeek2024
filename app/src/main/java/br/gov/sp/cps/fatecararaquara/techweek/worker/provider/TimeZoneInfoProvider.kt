package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import androidx.work.Data
import java.util.TimeZone

class TimeZoneInfoProvider(private val context: Context) {

    fun provide(): Data {
        val timeZone = TimeZone.getDefault()

        // TODO: Recuperar informações do fuso horário para as variáveis timeZoneId e timeZoneOffset.
        val timeZoneId = ""
        val timeZoneOffset = 0 // em horas

        return Data.Builder()
            .putString("timeZoneId", timeZoneId)
            .putInt("timeZoneOffsetHours", timeZoneOffset)
            .build()
    }
}
