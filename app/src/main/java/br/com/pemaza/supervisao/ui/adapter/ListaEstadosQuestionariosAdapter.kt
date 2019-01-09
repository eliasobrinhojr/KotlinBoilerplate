package br.com.pemaza.supervisao.ui.adapter

import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.extensions.toCategorias
import br.com.pemaza.supervisao.helpers.EstadoQuestionario
import kotlinx.android.synthetic.main.item_lista_questionario_estados.view.*

class ListaEstadosQuestionariosAdapter(var lista: List<EstadoQuestionario>) :
    RecyclerView.Adapter<ListaEstadosQuestionariosAdapter.EstadosQuestionarioViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadosQuestionarioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lista_questionario_estados, parent, false)
        return EstadosQuestionarioViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: EstadosQuestionarioViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    inner class EstadosQuestionarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: EstadoQuestionario) {
            val drawable = ResourcesCompat.getDrawable(itemView.resources, item.estado.icone, null)
            itemView.item_lista_questionario_estado_titulo.text = item.estado.titulo
            itemView.item_lista_questionario_estado_titulo.setTextColor(item.estado.cor)
            itemView.item_lista_questionario_estado_titulo.setCompoundDrawablesWithIntrinsicBounds(
                drawable,
                null,
                null,
                null
            )
            itemView.item_lista_questionario_estado_categorias.text = item.secoes.size.toCategorias()
            itemView.item_lista_questionario_estado_lista_secoes.adapter = ListaSecoesAdapter(item.secoes)
        }
    }
}