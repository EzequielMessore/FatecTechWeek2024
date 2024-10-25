package br.gov.sp.cps.fatecararaquara.techweek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import br.gov.sp.cps.fatecararaquara.techweek.data.repository.FirebaseRepository
import br.gov.sp.cps.fatecararaquara.techweek.ui.theme.FatecTechWeek2024Theme
import br.gov.sp.cps.fatecararaquara.techweek.workers.BatteryStatusWorker
import br.gov.sp.cps.fatecararaquara.techweek.workers.DeviceInfoWorker
import br.gov.sp.cps.fatecararaquara.techweek.workers.InstalledAppsWorker
import br.gov.sp.cps.fatecararaquara.techweek.workers.SystemInfoWorker
import br.gov.sp.cps.fatecararaquara.techweek.workers.UserInfoWorker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FatecTechWeek2024Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        startWorkManager()
    }

    private fun startWorkManager() {
        val workManager = WorkManager.getInstance(this)

        // Definir os trabalhos
        val deviceInfoWork = OneTimeWorkRequestBuilder<DeviceInfoWorker>().build()
        val systemInfoWork = OneTimeWorkRequestBuilder<SystemInfoWorker>().build()
        val installedAppsWork = OneTimeWorkRequestBuilder<InstalledAppsWorker>().build()
        val batteryStatusWork = OneTimeWorkRequestBuilder<BatteryStatusWorker>().build()
        val userInfoWork = OneTimeWorkRequestBuilder<UserInfoWorker>().build()

        // Executar os trabalhos
        workManager.beginWith(listOf(deviceInfoWork, systemInfoWork, installedAppsWork, batteryStatusWork, userInfoWork))
            .enqueue()

        // Coletar dados e enviar para o Firebase após a conclusão
        workManager.getWorkInfoByIdLiveData(userInfoWork.id).observe(this) { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                val consolidatedData = mutableMapOf<String, Any?>()

                // Consolidar os dados diretamente em um Map
                consolidatedData.putAll(workInfo.outputData.keyValueMap)

                // Enviar os dados consolidados para o Firebase
                FirebaseRepository().saveDataToFirebase(consolidatedData)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FatecTechWeek2024Theme {
        Greeting("Android")
    }
}