package br.com.pemaza.supervisao.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.model.Anotacao
import br.com.pemaza.supervisao.ui.viewmodel.AnotacaoViewModel
import kotlinx.android.synthetic.main.item_lista_anotacoes.view.*

class ListaAnotacoesAdapter(
    var lista: List<Anotacao>,
    var context: Context,
    var viewModel: AnotacaoViewModel
) :
    RecyclerView.Adapter<ListaAnotacoesAdapter.ListaAnotacoesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaAnotacoesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lista_anotacoes, parent, false)
        return ListaAnotacoesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ListaAnotacoesViewHolder, position: Int) {
        holder.bind(lista[position])

        holder.itemView.item_lista_anotacao_editar.setOnClickListener {
            viewModel.anotacaoSubject.onNext(lista[position])
        }

        viewModel.btnAnotarHabilitado
            .subscribe {
                if (!it)
                    holder.itemView.item_lista_anotacao_editar.visibility = View.INVISIBLE
                else
                    holder.itemView.item_lista_anotacao_editar.visibility = View.VISIBLE
            }
    }

    inner class ListaAnotacoesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(anotacao: Anotacao) {
            itemView.item_anotacao_titulo.text = anotacao.titulo
            itemView.item_anotacao_descricao.text = anotacao.observacao
        }
    }
}