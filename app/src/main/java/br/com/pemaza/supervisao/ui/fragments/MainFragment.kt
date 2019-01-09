package br.com.pemaza.supervisao.ui.fragments

import android.app.ActivityOptions
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.repositorios.QuestionarioRepository
import br.com.pemaza.supervisao.ui.activity.QuestionarioActivity
import br.com.pemaza.supervisao.ui.adapter.ListaAgendadasAdapter
import br.com.pemaza.supervisao.ui.adapter.ListaEmAndamentoAdapter
import br.com.pemaza.supervisao.ui.viewmodel.QuestionarioViewModel
import br.com.pemaza.supervisao.utilities.QuestionarioExtra
import br.com.pemaza.supervisao.utilities.QuestionarioSecoesExtra
import br.com.pemaza.supervisao.utilities.runOnComputationalThread
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : Fragment() {
    var contador = 0

    lateinit var viewModel: QuestionarioViewModel
    lateinit var adapterAndamento: ListaEmAndamentoAdapter
    lateinit var adapterAgendadas: ListaAgendadasAdapter
    var listaQuestionarioAgendadas: MutableList<Questionario> = mutableListOf()
    var listaQuestionarioAndamento: MutableList<Questionario> = mutableListOf()
    val disposer = CompositeDisposable()

    @Inject
    lateinit var questRepo: QuestionarioRepository

    init {
        MainApplication.fragmentComponent.mainFragInject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.lista_agendadas.setHasFixedSize(false)
        viewModel = ViewModelProviders.of(requireActivity()).get(QuestionarioViewModel::class.java)

        inicializaListas(view)
        atualizaListas()
        println("Create View")

        val subs = viewModel.listaQuestSubject
                .subscribe {
                    consumeRx(it)
                }

        disposer.add(subs)
        return view
    }

    private fun separaRx(lista: List<Questionario>): Single<MutableMap<String, MutableList<Questionario>>> {
        val map = mutableMapOf<String, MutableList<Questionario>>()
        map[QuestionarioStatus.Aberto.valor] = mutableListOf()
        map[QuestionarioStatus.Pendente.valor] = mutableListOf()
        return Observable.fromIterable(lista)
                .collectInto(map) { mapInterno, item ->
                    mapInterno[item.situacao]?.add(item)
                }
    }

    private fun consumeRx(lista: List<Questionario>) {
        separaRx(lista)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
            listaQuestionarioAgendadas = it[QuestionarioStatus.Pendente.valor]!!
            listaQuestionarioAndamento = it[QuestionarioStatus.Aberto.valor]!!
            atualizaListas()
        }, {})
    }

    fun atualizaListas() {
        adapterAndamento.items = listaQuestionarioAndamento
        adapterAndamento.notifyDataSetChanged()
        adapterAgendadas.items = listaQuestionarioAgendadas
        adapterAgendadas.notifyDataSetChanged()
    }

    fun inicializaListas(view: View) {
        adapterAndamento = ListaEmAndamentoAdapter(listaQuestionarioAndamento, requireContext())
        adapterAndamento.onItemClick = { questionario ->
            questionario?.let { questNotNull ->
                vaiParaQuestionario(questNotNull)
            }
        }
        view.lista_em_andamento.adapter = adapterAndamento
        view.lista_em_andamento.setHasFixedSize(false)

        adapterAgendadas = ListaAgendadasAdapter(listaQuestionarioAgendadas, requireContext())
        adapterAgendadas.onItemClick = { questionario ->
            questionario?.let { questNotNull ->
                vaiParaQuestionario(questNotNull)
            }
        }

        view.lista_agendadas.adapter = adapterAgendadas
    }

    fun vaiParaQuestionario(questionario: Questionario) {
        val subs = questRepo.getQuestionarioSecoes(questionario.id_agendamento).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val intent = Intent(requireContext(), QuestionarioActivity::class.java)
                    intent.putExtra(QuestionarioSecoesExtra, response)
                    intent.putExtra(QuestionarioExtra, questionario)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                }, {
                    //TODO Tratar retorno em caso de erro
                })
        disposer.add(subs)
    }

    override fun onDestroy() {
        disposer.dispose()
        super.onDestroy()
    }
}