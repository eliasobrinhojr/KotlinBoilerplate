package br.com.pemaza.supervisao.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.pemaza.supervisao.databinding.ItemListaAgendamentoAndamentoBinding
import br.com.pemaza.supervisao.interfaces.listenerItens
import br.com.pemaza.supervisao.model.Questionario
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_lista_agendamento_andamento.view.*
import java.util.concurrent.TimeUnit

class ListaEmAndamentoAdapter(
    override var items: MutableList<Questionario>, private val context:
    Context
) : RecyclerView.Adapter<ListaEmAndamentoAdapter.ViewHolder>(), listenerItens {
    var onItemClick: (Questionario?) -> Unit = {}
    private val disposer = CompositeDisposable()

    inner class ViewHolder(private val binding: ItemListaAgendamentoAndamentoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.item_lista_agendamento_andamento_cardview.setOnClickListener {
                onItemClick(binding.questionario)
            }
        }

        var subs: Disposable? = null

        fun bind(quest: Questionario) {

            binding.questionario = quest
            binding.executePendingBindings()

            val numeroPercentual = quest.percentual_avaliado.toInt()

            val percentual = Observable.intervalRange(
                1,
                numeroPercentual.toLong(),
                0,
                100,
                TimeUnit.MICROSECONDS,
                AndroidSchedulers.mainThread()
            )
                .subscribeOn(Schedulers.io())

            subs = percentual.subscribe {
                itemView.item_lista_agendamento_andamento_progressBar.progress = it.toInt()
                itemView.item_lista_agendamento_andamento_progress_porcentagem.text = "$it%"
            }
            disposer.add(subs!!)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.subs?.let { if (!it.isDisposed) disposer.remove(it) }
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListaAgendamentoAndamentoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        disposer.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}

