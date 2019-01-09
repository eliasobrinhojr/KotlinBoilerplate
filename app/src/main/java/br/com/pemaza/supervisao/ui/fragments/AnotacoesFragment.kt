package br.com.pemaza.supervisao.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.dto.AnotacaoResponse
import br.com.pemaza.supervisao.repositorios.AnotacaoRepository
import br.com.pemaza.supervisao.ui.adapter.ListaAnotacoesAdapter
import br.com.pemaza.supervisao.ui.viewmodel.AnotacaoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_anotacoes.view.*
import javax.inject.Inject

class AnotacoesFragment : Fragment() {

    val disposer = CompositeDisposable()
    lateinit var viewModel: AnotacaoViewModel

    @Inject
    lateinit var questRepo: AnotacaoRepository

    init {
        MainApplication.fragmentComponent.anotacaoFragInject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_anotacoes, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(AnotacaoViewModel::class.java)

        val adapter = ListaAnotacoesAdapter(listOf(), context!!, viewModel)
        view.lista_anotacoes.adapter = adapter

        sincronizaListaAnotacao()
        observaListaAnotacoes(adapter)
        btnCancelarAnotacao()

        view.lista_anotacoes.setHasFixedSize(false)

        return view
    }

    private fun btnCancelarAnotacao() {
        viewModel.actionFecharFragmentNovaAnotacao.subscribe {
            viewModel.btnAnotarHabilitado.onNext(true)
            childFragmentManager.popBackStack()
        }
    }

    private fun observaListaAnotacoes(adapter: ListaAnotacoesAdapter) {
        viewModel.listaQuestSubject.subscribe {
            adapter.lista = it
            adapter.notifyDataSetChanged()
        }
    }

    fun sincronizaListaAnotacao() {

        val subs = listaAnotacaoObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(listaAnotacaoConsumer())

        disposer.add(subs)
    }

    fun listaAnotacaoObservable() = questRepo.getListaAnotacaoApi(viewModel.questionarioSubject.value!!.id_agendamento)
    fun listaAnotacaoConsumer(): BiConsumer<AnotacaoResponse, Throwable> {
        return BiConsumer { anotacao, _ ->
            anotacao?.let { viewModel.listaQuestSubject.onNext(it.anotacao) }
        }
    }

    override fun onDestroy() {
        disposer.dispose()
        super.onDestroy()
    }
}
