package br.gov.sp.cps.fatecararaquara.techweek.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseRepository(private val context: Context) {

    companion object {
        private const val TAG = "FirebaseRepository"
    }

    private val database = FirebaseDatabase.getInstance().reference

    @SuppressLint("HardwareIds")
    private fun getUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid
            ?: Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown_user"
    }

    private fun getTimestamp(): String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

    fun saveDataToFirebase(dataMap: Map<String, Any?>) {
        val userRef = database.child("userData").child(getUserId()).child(getTimestamp())

        userRef.setValue(dataMap)
            .addOnSuccessListener { Log.d(TAG, "Dados enviados com sucesso ao Firebase.") }
            .addOnFailureListener { e -> Log.e(TAG, "Erro no envio de dados ao Firebase", e) }
    }
}
