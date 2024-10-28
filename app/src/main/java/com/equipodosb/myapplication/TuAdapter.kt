package com.equipodosb.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TuAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<TuAdapter.ViewHolder> () {
    private var datos: List<Products> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(tuModelo:Products)
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
        val txtMonto: TextView = itemView.findViewById(R.id.txtMonto)
        val txtMetodo: TextView = itemView.findViewById(R.id.txtMetodo)
        val txtDesc: TextView = itemView.findViewById(R.id.txtDesc)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION)
                {
                    val tuModelo = datos[position]
                    itemClickListener.onItemClick(tuModelo)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun setDatos(datos: List<Products>)
    {
        this.datos=datos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datos[position]
        holder.txtFecha.text=item.fecha
        holder.txtMonto.text=item.monto.toString()
        holder.txtMetodo.text=item.metodo
        holder.txtDesc.text=item.desc

    }

}