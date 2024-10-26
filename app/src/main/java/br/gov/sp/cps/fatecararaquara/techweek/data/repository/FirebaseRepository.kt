import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseRepository(private val context: Context) {

    private val database = FirebaseDatabase.getInstance()

    // Obter o identificador único do usuário
    @SuppressLint("HardwareIds")
    private fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid
            ?: Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // Gerar identificador baseado no timestamp atual
    private fun getTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Enviar dados para o Firebase
    fun saveDataToFirebase(dataMap: Map<String, Any?>) {
        val userId = getUserId()
        val timestamp = getTimestamp()
        val userRef = database.getReference("userData").child(userId).child(timestamp)

        userRef.setValue(dataMap)
            .addOnSuccessListener {
                // Sucesso ao salvar os dados
            }
            .addOnFailureListener { e ->
                // Log da falha
                e.printStackTrace()
            }
    }
}
