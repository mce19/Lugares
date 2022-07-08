package com.lugares_v.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lugares_v.model.Lugar


class LugarDao {

    private val coleecion1 = "lugaresApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleecion2 = "misLugares"




}


fun getAllData(): MutableLiveData<list<Lugar>>{

    val listLugares = MutableLiveData<list<Lugar>>()
    firestore.collection(coleccion1)

}

fun saveLugar(lugar: lugar){


}

fun deleteLugar(lugar: lugar){


}