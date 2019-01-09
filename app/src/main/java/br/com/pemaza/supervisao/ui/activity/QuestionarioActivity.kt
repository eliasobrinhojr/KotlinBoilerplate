package br.com.pemaza.supervisao.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.dto.QuestionarioSecoesResponse
import br.com.pemaza.supervisao.extensions.toPtBr
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.ui.fragments.AnotacoesFragment
import br.com.pemaza.supervisao.ui.fragments.QuestionarioAbertoFragment
import br.com.pemaza.supervisao.ui.viewmodel.AnotacaoViewModel
import br.com.pemaza.supervisao.ui.viewmodel.SecoesQuestionarioViewModel
import br.com.pemaza.supervisao.utilities.NovaAnotacaoFragment
import br.com.pemaza.supervisao.utilities.QuestionarioExtra
import br.com.pemaza.supervisao.utilities.QuestionarioSecoesExtra
import kotlinx.android.synthetic.main.activity_questionario.*

class QuestionarioActivity : BaseActivity() {
    lateinit var secoesViewModel: SecoesQuestionarioViewModel
    lateinit var anotacaoViewModel: AnotacaoViewModel

    init {
        MainApplication.actComponent.questActInject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

        secoesViewModel = ViewModelProviders.of(this).get(SecoesQuestionarioViewModel::class.java)
        anotacaoViewModel = ViewModelProviders.of(this).get(AnotacaoViewModel::class.java)
        val (questResponse, questionario) = getExtras()
        configuraBanner(questResponse, questionario)
        inicializaValoresViewModel(questResponse, questionario)
        backBtn()

        if (savedInstanceState == null) {
            criaFragment()
        }
    }

    private fun getExtras(): Pair<QuestionarioSecoesResponse, Questionario> {
        val questResponse = intent.getSerializableExtra(QuestionarioSecoesExtra) as QuestionarioSecoesResponse
        val questionario = intent.getSerializableExtra(QuestionarioExtra) as Questionario
        return Pair(questResponse, questionario)
    }

    private fun criaFragment() {
        val fragment = QuestionarioAbertoFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.questionario_fragment_conteiner, fragment)
                .setPrimaryNavigationFragment(fragment)
                .commit()
    }

    private fun inicializaValoresViewModel(questResponse: QuestionarioSecoesResponse, questionario: Questionario) {
        secoesViewModel.filial.onNext(questResponse.dados.filial)
        secoesViewModel.secoes.onNext(questResponse.dados.secoes)
        anotacaoViewModel.questionarioSubject.onNext(questionario)
    }

    private fun backBtn() {
        questionario_back_button.setOnClickListener {
            finish()
        }
    }

    fun configuraBanner(questResponse: QuestionarioSecoesResponse, questionario: Questionario) {
        questionario_filial.text = questResponse.dados.filial.getLoja()
        questionario_filial_endereco.text = questResponse.dados.filial.getEnderecoCompleto()
        questionario_inicioprevisto_data.text = questionario.data_inicio.toPtBr()

        if (questionario.situacao == QuestionarioStatus.Aberto.valor) {
            questionario_inicioprevisto.text = "Início"
            questionario_termino.text = "Término"
            questionario_termino_data.text = "Até o momento"
        }
    }

    private fun trata2() {
        supportFragmentManager.primaryNavigationFragment
    }

    private fun trataBackFragments() {
        val childFM = supportFragmentManager.fragments[0].childFragmentManager
        println(childFM)
        val fragmentChild = childFM.getBackStackEntryAt(childFM.backStackEntryCount - 1).name
        when (fragmentChild) {
            NovaAnotacaoFragment -> {
                childFM.popBackStackImmediate()
                anotacaoViewModel.btnAnotarHabilitado.onNext(true)
            }
            else -> super.onBackPressed()
        }
//        for (fChild in fragmentsChild) {
//            if (fChild is AnotacoesFragment) {
//                if (fChild.childFragmentManager.backStackEntryCount > 0) {
////                    fChild.childFragmentManager.popBackStackImmediate()
//                    super.onBackPressed()
//                    anotacaoViewModel.btnAnotarHabilitado.onNext(true)
//                } else {
//                    super.onBackPressed()
//                }
//            }
//        }
    }
}