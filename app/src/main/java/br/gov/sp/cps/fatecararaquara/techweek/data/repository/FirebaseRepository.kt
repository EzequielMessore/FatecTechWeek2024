package br.gov.sp.cps.fatecararaquara.techweek.data.repository

import com.google.firebase.database.FirebaseDatabase

class FirebaseRepository {

    private val database = FirebaseDatabase.getInstance()

    fun saveDataToFirebase(dataMap: Map<String, Any?>) {
        val userId = dataMap["userId"] as String? ?: "unknown_user"
        val myRef = database.getReference("userData").child(userId)

        myRef.setValue(dataMap)
            .addOnSuccessListener {
                // Sucesso ao salvar os dados
            }
            .addOnFailureListener {
                // Falha ao salvar os dados
            }
    }
}
