package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.Data

class NetworkInfoProvider(private val context: Context) {

    fun provide(): Data {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = connectivityManager?.activeNetwork
        val capabilities = connectivityManager?.getNetworkCapabilities(network)

        // TODO: Recuperar informações de rede para as variáveis isConnected, isWifi e isMobile.
        val isConnected = false
        val isWifi = false
        val isMobile = false

        return Data.Builder()
            .putBoolean("isConnected", isConnected)
            .putBoolean("isWifi", isWifi)
            .putBoolean("isMobile", isMobile)
            .build()
    }
}
