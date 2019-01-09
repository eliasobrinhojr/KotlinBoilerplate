package br.com.pemaza.supervisao.ui.activity

import android.app.ActivityOptions
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.LoginHelper
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import br.com.pemaza.supervisao.repositorios.QuestionarioRepository
import br.com.pemaza.supervisao.ui.fragments.MainFragment
import br.com.pemaza.supervisao.ui.fragments.SemAgendamentoFragment
import br.com.pemaza.supervisao.ui.fragments.SemConexaoFragment
import br.com.pemaza.supervisao.ui.viewmodel.QuestionarioViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.net.ConnectException
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val mainFragment = MainFragment()

    private val semAgendamentoFragment = SemAgendamentoFragment()

    lateinit var viewModel: QuestionarioViewModel

    @Inject
    lateinit var questRepo: QuestionarioRepository

    @Inject
    lateinit var loginHelper: LoginHelper

    init {
        MainApplication.actComponent.mainActInject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(QuestionarioViewModel::class.java)
        swipeRefreshAction()
        perfilClick()
        historicoClick()
        observaLista()
    }

    private fun observaLista() {
        val subs = viewModel.listaQuestSubject.subscribe {
            verifyListSetFragment(it.size)
        }
        disposer.add(subs)
    }

    override fun sincronizaDados() {
        val subs = sincronizaListaQuestionario()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sincronizaConsumer())
        disposer.add(subs)
    }

    fun sincronizaListaQuestionario(): Single<List<Questionario>> {
        return questRepo.getListaQuestionarioApi(loginHelper.usuario.usuario_codigo) // Requisição p/ servidor
                .map { resp -> resp.dados }
                .flatMap { lista -> questRepo.addOrUpdateQuestionarioInterno(lista).toSingle { lista } }
                .flatMapObservable { lista ->  Observable.fromIterable(lista)}
                .collectInto(mutableListOf<UsuarioQuestionario>()) { lista, questionario ->
                    lista.add(UsuarioQuestionario(loginHelper.usuario.usuario_codigo, questionario.id_agendamento))
                }
                .flatMapCompletable { questRepo.addOrUpdateRelacaoUsuarioQuestionarioLista(it) }
                .andThen(questRepo.getListaQuestionarioInternoAndamentoPendentes(loginHelper.usuario.usuario_codigo)) // Lista vinda do banco interno após completar a cadeia
    }

    fun sincronizaConsumer(): BiConsumer<List<Questionario>?, Throwable?> {
        return BiConsumer { lista, erro ->
            main_swipe.isRefreshing = false
            lista?.let { viewModel.listaQuestSubject.onNext(it) }
            erro?.let { semConexao() }
        }
    }

    private fun semConexao() {
        addFragment(SemConexaoFragment(), false, "")
    }

    private fun swipeRefreshAction() {
        main_swipe.setOnRefreshListener {
            sincronizaDados()
        }
    }

    private fun perfilClick() {
        btn_perfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun historicoClick() {
        btn_left_history.setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }

    private fun verifyListSetFragment(size: Int) {
        if (size > 0)
            addFragment(mainFragment, true, "0")
        else
            addFragment(semAgendamentoFragment, true, "2")
    }

    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame_back, fragment, tag)
        ft.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        finish()
    }
}
