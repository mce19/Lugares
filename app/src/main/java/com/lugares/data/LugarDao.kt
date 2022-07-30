package com.lugares.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.lugares.model.Lugar

class LugarDao {

    private var codigoUsuario: String
    private var firestore: FirebaseFirestore

    init {
        val usuario = Firebase.auth.currentUser?.email
        // val usuario = Firebase.auth.currentUser?.uid
        codigoUsuario = "$usuario"
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getLugares() : MutableLiveData<List<Lugar>> {
        val listaLugares = MutableLiveData<List<Lugar>>()
        firestore.collection("lugaresApp")
            .document(codigoUsuario)
            .collection("misLugares")
            .addSnapshotListener{ snapshot, e ->
                if(e != null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }
            }

        return listaLugares
    }

    fun saveLugar(lugar: Lugar){
        val document: DocumentReference
        if(lugar.id.isEmpty()){
            document = firestore
                .collection("lugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document()
            lugar.id = document.id
        } else {
            document = firestore
                .collection("lugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
        }
        val set = document.set(lugar)
        set.addOnSuccessListener {
            Log.d("AddLugar", "Lugar agregado")
        }
            .addOnCanceledListener {
                Log.e("AddLugar", "Lugar no agregado")
            }
    }

    fun deleteLugar(lugar: Lugar){
        if(lugar.id.isNotEmpty()){
            firestore
                .collection("lugaresApp")
                .document(codigoUsuario)
                .collection("misLugares")
                .document(lugar.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("DeleteLugar", "Lugar eliminado")
                }
                .addOnCanceledListener {
                    Log.e("DeleteLugar", "Lugar no eliminado")
                }

        }
    }
}