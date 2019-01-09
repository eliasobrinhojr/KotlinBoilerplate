package br.com.pemaza.supervisao.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.model.Secao
import kotlinx.android.synthetic.main.item_lista_secoes.view.*

class ListaSecoesAdapter(private val lista: List<Secao>) :
    RecyclerView.Adapter<ListaSecoesAdapter.ListaSecoesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaSecoesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lista_secoes, parent, false)
        return ListaSecoesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ListaSecoesViewHolder, position: Int) {
        holder.bind(lista[position])
        holder.itemView.item_lista_secoes_icone.setOnClickListener {
            Log.d("itemView", "click item: " + lista[position].descricao)
        }
    }

    inner class ListaSecoesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(secao: Secao) {
            itemView.item_lista_secoes_descricao.text = secao.descricao.toLowerCase().capitalize()
        }
    }
}