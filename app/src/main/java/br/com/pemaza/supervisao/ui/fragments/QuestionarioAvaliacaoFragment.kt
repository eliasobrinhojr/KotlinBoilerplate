package br.com.pemaza.supervisao.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.helpers.EstadoQuestionario
import br.com.pemaza.supervisao.helpers.enums.CardSecoesEnum
import br.com.pemaza.supervisao.helpers.enums.SecaoStatus
import br.com.pemaza.supervisao.model.Secao
import br.com.pemaza.supervisao.ui.adapter.ListaEstadosQuestionariosAdapter
import br.com.pemaza.supervisao.ui.viewmodel.SecoesQuestionarioViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_questionario_avaliacao.view.*

class QuestionarioAvaliacaoFragment : Fragment() {

    lateinit var viewModel: SecoesQuestionarioViewModel
    val adapter = ListaEstadosQuestionariosAdapter(listOf())
    private val disposer = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questionario_avaliacao, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(SecoesQuestionarioViewModel::class.java)
        view.questionario_avaliacao_lista_estados.adapter = adapter
        view.questionario_avaliacao_lista_estados.addItemDecoration(DividerItemDecoration(requireContext(), 0))
        observaSecoes()

        return view
    }

    fun observaSecoes() {
        val subs = viewModel.secoes.subscribe {
            separaSecoes(it)
        }
        disposer.add(subs)
    }

    private fun separaSecoes(list: List<Secao>) {
        val listaRoot = mutableListOf<EstadoQuestionario>()
        SecaoStatus.values().forEach { status ->
            verificaEstado(status.valor, list)?.let { listaRoot.add(it) }
        }
        atualizaAdapter(listaRoot)
    }

    fun atualizaAdapter(listaRoot: List<EstadoQuestionario>) {
        adapter.lista = listaRoot
        adapter.notifyDataSetChanged()
    }

    private fun verificaEstado(estado: String, listaGeral: List<Secao>): EstadoQuestionario? {
        val listaSecoes = mutableListOf<Secao>()
        listaGeral.forEach {
            if (it.situacao == estado) listaSecoes.add(it)
        }

        return if (listaSecoes.size > 0) {
            EstadoQuestionario(CardSecoesEnum.valueOf(estado), listaSecoes)
        } else {
            null
        }
    }

    override fun onDestroy() {
        disposer.dispose()
        super.onDestroy()
    }
}
