package br.gov.sp.cps.fatecararaquara.techweek.worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.gov.sp.cps.fatecararaquara.techweek.data.repository.FirebaseRepository
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.BatteryInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.DeviceInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.LocaleInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.NetworkInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.ScreenStatusInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.StorageInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.SystemInfoProvider
import br.gov.sp.cps.fatecararaquara.techweek.worker.provider.TimeZoneInfoProvider

class CollectDataWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        private const val TAG = "CollectDataWorker"
    }

    override fun doWork(): Result {
        return try {
            val context = applicationContext
            val consolidatedData = Data.Builder()

            fun safeCollect(provider: () -> Data): Data {
                return try {
                    provider()
                } catch (e: Exception) {
                    Log.e(TAG, "Erro na coleta de dados do ${provider::class.simpleName}", e)
                    Data.EMPTY
                }
            }

            consolidatedData.putAll(safeCollect { DeviceInfoProvider().provide() })
            consolidatedData.putAll(safeCollect { SystemInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { BatteryInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { NetworkInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { StorageInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { TimeZoneInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { ScreenStatusInfoProvider(context).provide() })
            consolidatedData.putAll(safeCollect { LocaleInfoProvider(context).provide() })

            val data = consolidatedData.build()

            data.keyValueMap.forEach { (key, value) -> Log.d(TAG, "$key: $value") }

            FirebaseRepository(context).saveDataToFirebase(data.keyValueMap)

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Erro no processo de coleta de dados", e)
            Result.failure()
        }
    }
}
