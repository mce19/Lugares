package com.lugares.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lugares.databinding.LugarFilaBinding
import com.lugares.model.Lugar
import com.lugares.ui.lugar.LugarFragmentDirections

class LugarAdapter : RecyclerView.Adapter<LugarAdapter.LugarViewHolder>(){

    // Lista para almacenar la información de los lugares
    private var listaLugares = emptyList<Lugar>()

    inner class LugarViewHolder(private val itemBinding: LugarFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        fun bind(lugar: Lugar){
            itemBinding.tvNombre.text = lugar.nombre
            itemBinding.tvCorreo.text = lugar.correo
            itemBinding.tvTelefono.text = lugar.telefono
            itemBinding.tvWeb.text = lugar.web

            Glide.with(itemBinding.root.context)
                .load(lugar.rutaImagen)
                .circleCrop()
                .into(itemBinding.imagen)

            itemBinding.vistaFila.setOnClickListener{
                val action = LugarFragmentDirections
                    .actionNavLugarToUpdateLugarFragment(lugar)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val itemBinding = LugarFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LugarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugarActual = listaLugares[position]
        holder.bind(lugarActual)
    }

    override fun getItemCount(): Int {
        return listaLugares.size
    }

    fun setData(lugares: List<Lugar>){
        this.listaLugares = lugares
        notifyDataSetChanged() // Se redibuja la lista
    }
}