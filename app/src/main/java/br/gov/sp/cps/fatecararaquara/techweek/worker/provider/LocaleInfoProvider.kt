package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import androidx.work.Data
import java.util.Locale

class LocaleInfoProvider(private val context: Context) {

    fun provide(): Data {
        // TODO: Recuperar informações do dispositivo para as variáveis usando a classe Locale.
        val locale = Locale.getDefault()
        val language = ""
        val country = ""

        return Data.Builder()
            .putString("language", language)
            .putString("country", country)
            .build()
    }
}
