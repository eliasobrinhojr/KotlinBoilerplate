package br.com.pemaza.supervisao.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.pemaza.supervisao.databinding.ItemListaAgendamentoAgendadasBinding
import br.com.pemaza.supervisao.interfaces.listenerItens
import br.com.pemaza.supervisao.model.Questionario
import kotlinx.android.synthetic.main.item_lista_agendamento_agendadas.view.*

class ListaAgendadasAdapter(
    override var items: MutableList<Questionario>, private val context:
    Context
) : RecyclerView.Adapter<ListaAgendadasAdapter.ViewHolder>(), listenerItens {
    var onItemClick: (Questionario?) -> Unit = {}

    inner class ViewHolder(private val binding: ItemListaAgendamentoAgendadasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.item_lista_agendamento_agendada_cardview.setOnClickListener {
                onItemClick(binding.questionario)
            }
        }

        fun bind(quest: Questionario) {
            exibeAvaliadores(quest)
            binding.questionario = quest
            binding.executePendingBindings()
        }

        fun exibeAvaliadores(quest: Questionario) {
            val listRelacionados: MutableList<String> = mutableListOf()
            for (i in 0..1)
                listRelacionados.add("")

            itemView.item_lista_agendamento_agendada_lista_pessoas.adapter =
                ListaPessoasRelacionadasAdapter(listRelacionados, context)

            if (quest.num_avaliadores > 2) {
                itemView.item_lista_agendamento_agendada_ver_mais.text = "+" + (quest.num_avaliadores - 2)
            } else {
                itemView.item_lista_agendamento_agendada_ver_mais.text = ""
                itemView.item_lista_agendamento_agendada_ver_mais.layoutParams.width = 1
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListaAgendamentoAgendadasBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

