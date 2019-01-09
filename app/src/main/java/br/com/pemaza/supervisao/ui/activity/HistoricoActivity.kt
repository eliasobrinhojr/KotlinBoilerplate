package br.com.pemaza.supervisao.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.LoginHelper
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import br.com.pemaza.supervisao.repositorios.QuestionarioRepository
import br.com.pemaza.supervisao.ui.adapter.ListaHistoricoAdapter
import br.com.pemaza.supervisao.ui.viewmodel.QuestionarioViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_historico.*
import javax.inject.Inject

class HistoricoActivity : BaseActivity() {
    lateinit var viewModel: QuestionarioViewModel
    private val adapter = ListaHistoricoAdapter(mutableListOf())

    @Inject
    lateinit var questRepo: QuestionarioRepository

    @Inject
    lateinit var loginHelper: LoginHelper

    init {
        MainApplication.actComponent.histActInjet(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuracoesIniciais()
        configuraSwipe()
        observeLista()
    }

    private fun observeLista() {
        val subs = viewModel.listaQuestSubject.subscribe {
            atualizaListaAdapter(it)
        }
        disposer.add(subs)
    }

    private fun configuracoesIniciais() {
        supportActionBar?.title = "Histórico"
        setContentView(R.layout.activity_historico)
        historico_lista_cardview.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(QuestionarioViewModel::class.java)
    }

    private fun configuraSwipe() {
        historico_lista_swipe.setOnRefreshListener {
            sincronizaDados()
        }
    }

    override fun sincronizaDados() {
        val subs = sincronizaLista()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(sincronizaConsumer())
        disposer.add(subs)
    }

    fun sincronizaLista(): Single<List<Questionario>> {
        return questRepo.getListaQuestionarioApi(loginHelper.usuario.usuario_codigo, QuestionarioStatus.Finalizado.valor)
                .map { response -> response.dados }
                .flatMap { lista -> questRepo.addOrUpdateQuestionarioInterno(lista).toSingle { lista } }
                .flatMapObservable { lista ->  Observable.fromIterable(lista)}
                .collectInto(mutableListOf<UsuarioQuestionario>()) { lista, questionario ->
                    lista.add(UsuarioQuestionario(loginHelper.usuario.usuario_codigo, questionario.id_agendamento))
                }
                .flatMapCompletable { questRepo.addOrUpdateRelacaoUsuarioQuestionarioLista(it) }
                .andThen(questRepo.getListaQuestionarioInternoLimit(QuestionarioStatus.Finalizado.valor, 5))
    }

    fun sincronizaConsumer(): BiConsumer<List<Questionario>?, Throwable?> {
        return BiConsumer { lista, erro ->
            lista?.let { viewModel.listaQuestSubject.onNext(it) }
            //TODO melhorar tratamento de erro
            erro?.let { Toast.makeText(this, "Falha na comunicação com o servidor", Toast.LENGTH_SHORT).show() }
            historico_lista_swipe.isRefreshing = false
        }
    }

    fun atualizaListaAdapter(lista: List<Questionario>) {
        adapter.lista = lista.toMutableList()
        adapter.notifyDataSetChanged()
    }
}
