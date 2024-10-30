package br.gov.sp.cps.fatecararaquara.techweek.worker.provider

import android.os.Build
import androidx.work.Data

class DeviceInfoProvider {

    fun provide(): Data {
        // TODO: Recuperar informações do dispositivo para as variáveis usando a classe Build.
        return Data.Builder()
            .putString("deviceManufacturer", "")    // Fabricante do dispositivo
            .putString("deviceModel", "")                  // Modelo do dispositivo
            .putString("deviceBrand", "")                  // Marca do dispositivo
            .putString("deviceProduct", "")              // Nome do produto
            .putString("deviceHardware", "")            // Nome do hardware
            .putString("osVersion", "")          // Versão do SO
            .putInt("apiLevel", 0)              // Nível da API
            .putString("buildFingerprint", "")       // Impressão digital da build
            .putString("buildType", "")                     // Tipo da build
            .build()
    }
}
