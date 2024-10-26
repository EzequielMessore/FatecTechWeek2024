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
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.gov.sp.cps.fatecararaquara.techweek.ui.theme.FatecTechWeek2024Theme
import br.gov.sp.cps.fatecararaquara.techweek.worker.CollectDataWorker
import java.util.concurrent.TimeUnit.MINUTES

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
        startDataCollection()
    }

    private fun startDataCollection() {
        val workManager = WorkManager.getInstance(this)

        // Solicitação e execução imediatas (apenas uma vez)
        workManager.enqueueUniqueWork(
            "ImmediateDataCollection",
            ExistingWorkPolicy.REPLACE, // Substitui a execução imediata caso já exista uma
            OneTimeWorkRequestBuilder<CollectDataWorker>().build()
        )

        // Solicitação e agendamento periódico para execução a cada 15 minutos
        workManager.enqueueUniquePeriodicWork(
            "PeriodicDataCollection",
            ExistingPeriodicWorkPolicy.KEEP, // Mantém apenas uma execução periódica ativa
            PeriodicWorkRequestBuilder<CollectDataWorker>(15, MINUTES).build()
        )
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