package br.com.pemaza.supervisao.ui.adapter

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import kotlinx.android.synthetic.main.item_lista_pessoas_relacionadas.view.*

class ListaPessoasRelacionadasAdapter(
    private val items: MutableList<String>, private val context:
    Context
) : RecyclerView.Adapter<ListaPessoasRelacionadasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_lista_pessoas_relacionadas,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        mostraImagem(holder)
        //   clickImagemView(holder, position)
    }

//    fun clickImagemView(
//        holder: ViewHolder,
//        position: Int
//    ) {
////        holder.imageView.setOnClickListener {
////            Toast.makeText(context, "relacionado $position", Toast.LENGTH_SHORT).show()
////        }
//    }

    fun mostraImagem(holder: ViewHolder) {
        holder.imageView.setImageDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.icon_toolbar_left,
                null
            )
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.item_lista_pessoas_relacionadas_img!!
    }
}

