package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import androidx.work.Data
import java.util.Locale

class LocaleInfoProvider(private val context: Context) {

    fun provide(): Data {
        val locale = Locale.getDefault()
        val language = locale.language
        val country = locale.country

        return Data.Builder()
            .putString("language", language)
            .putString("country", country)
            .build()
    }
}
