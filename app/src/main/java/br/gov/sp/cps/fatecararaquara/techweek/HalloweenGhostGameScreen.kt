import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HalloweenGhostGameScreen() {
    var score by remember { mutableIntStateOf(0) }
    var gameTime by remember { mutableIntStateOf(60) }
    var gameOver by remember { mutableStateOf(false) }
    var ghostTrigger by remember { mutableIntStateOf(0) }

    // Posição do único fantasma
    val ghostPosition = remember { Animatable(0f) to Animatable(0f) }

    // Temporizador do jogo
    LaunchedEffect(gameTime) {
        if (gameTime > 0 && !gameOver) {
            delay(1000L)
            gameTime--
        } else {
            gameOver = true
        }
    }

    // Controle de movimento contínuo do fantasma
    LaunchedEffect(ghostTrigger) {
        while (!gameOver) {
            ghostPosition.first.animateTo(
                targetValue = Random.nextFloat() * (300f - 60f), // Ajuste para evitar corte
                animationSpec = tween(durationMillis = 1000) // Animação mais rápida
            )
            ghostPosition.second.animateTo(
                targetValue = Random.nextFloat() * (600f - 60f), // Ajuste para evitar corte
                animationSpec = tween(durationMillis = 1000) // Animação mais rápida
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (gameOver) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over! 🎃 \nPontuação: $score",
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Reset game state
                    score = 0
                    gameTime = 30
                    gameOver = false
                    ghostTrigger++
                }) {
                    Text("Restart")
                }
            }
        } else {
            // Exibe o temporizador e a pontuação
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tempo: $gameTime s", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Pontuação: $score", color = Color.White, fontSize = 18.sp)
            }

            // Mostra o fantasma que se move continuamente
            Box(
                modifier = Modifier
                    .offset(x = ghostPosition.first.value.dp, y = ghostPosition.second.value.dp)
                    .size(60.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .graphicsLayer { alpha = 0.8f }
                    .clickable {
                        if (!gameOver) {
                            score++
                            // Reposiciona o fantasma em um ponto aleatório dentro da tela
                            CoroutineScope(Dispatchers.Main).launch {
                                ghostPosition.first.snapTo(Random.nextFloat() * (300f - 60f))
                                ghostPosition.second.snapTo(Random.nextFloat() * (600f - 60f))
                                ghostTrigger++
                            }
                        }
                    }
            ) {
                Text(
                    text = "👻",
                    fontSize = 40.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}