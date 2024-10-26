package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import androidx.work.Data
import java.util.TimeZone

class TimeZoneInfoProvider(private val context: Context) {

    fun provide(): Data {
        val timeZone = TimeZone.getDefault()
        val timeZoneId = timeZone.id
        val timeZoneOffset = timeZone.rawOffset / (60 * 60 * 1000) // em horas

        return Data.Builder()
            .putString("timeZoneId", timeZoneId)
            .putInt("timeZoneOffsetHours", timeZoneOffset)
            .build()
    }
}
