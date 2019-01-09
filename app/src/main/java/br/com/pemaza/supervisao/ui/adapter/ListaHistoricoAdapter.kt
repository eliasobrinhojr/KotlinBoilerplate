package br.com.pemaza.supervisao.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.pemaza.supervisao.databinding.ItemListaAgendamentoHistoricoBinding
import br.com.pemaza.supervisao.model.Questionario

class ListaHistoricoAdapter(var lista: MutableList<Questionario>) :
    RecyclerView.Adapter<ListaHistoricoAdapter.HistoricoViewHolder>() {

    inner class HistoricoViewHolder(private val binding: ItemListaAgendamentoHistoricoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quest: Questionario) {
            binding.questionario = quest
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListaAgendamentoHistoricoBinding.inflate(inflater, parent, false)
        return HistoricoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}