package br.gov.sp.cps.fatecararaquara.techweek

import HalloweenGhostGameScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import br.gov.sp.cps.fatecararaquara.techweek.ui.theme.FatecTechWeek2024Theme
import br.gov.sp.cps.fatecararaquara.techweek.worker.CollectDataWorker
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import java.util.concurrent.TimeUnit.MINUTES

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Black.toArgb()
        startDataCollection()

        setContent {
            FatecTechWeek2024Theme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "loading") {
                            composable("loading") { HalloweenAnimation(navController) }
                            composable("game") { HalloweenGhostGameScreen() }
                        }
                    }
                }
            }
        }
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
fun HalloweenAnimation(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.werewolf))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = 2)

    LaunchedEffect(progress) {
        if (progress == 1f) {
            navController.navigate("game") {
                popUpTo("loading") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GreetingPreview() {
    FatecTechWeek2024Theme {
        HalloweenAnimation(rememberNavController())
    }
}