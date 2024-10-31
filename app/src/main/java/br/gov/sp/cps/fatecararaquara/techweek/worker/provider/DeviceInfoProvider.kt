package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.os.Build
import androidx.work.Data

class DeviceInfoProvider {

    fun provide(): Data {
        // TODO: Recuperar informações do dispositivo para as variáveis usando a classe Build.
        return Data.Builder()
            .putString("deviceManufacturer", Build.MANUFACTURER)    // Fabricante do dispositivo
            .putString("deviceModel", Build.MODEL)                  // Modelo do dispositivo
            .putString("deviceBrand", Build.BRAND)                  // Marca do dispositivo
            .putString("deviceProduct", Build.PRODUCT)              // Nome do produto
            .putString("deviceHardware", Build.HARDWARE)            // Nome do hardware
            .putString("osVersion", Build.VERSION.RELEASE)          // Versão do SO
            .putInt("apiLevel", Build.VERSION.SDK_INT)              // Nível da API
            .putString("buildFingerprint", Build.FINGERPRINT)      // Fingerprint da build)       // Impressão digital da build
            .putString("buildType", Build.TYPE)                     // Tipo da build
            .build()
    }
}
