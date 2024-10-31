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
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        val isWifi = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        val isMobile = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true

        return Data.Builder()
            .putBoolean("isConnected", isConnected)
            .putBoolean("isWifi", isWifi)
            .putBoolean("isMobile", isMobile)
            .build()
    }
}
