package br.com.pemaza.supervisao

import android.content.Intent
import android.os.Bundle
import br.com.pemaza.supervisao.api.PmzAPIAnotacao
import br.com.pemaza.supervisao.dto.AnotacaoResponse
import br.com.pemaza.supervisao.dto.QuestionarioSecoesResponse
import br.com.pemaza.supervisao.model.Anotacao
import br.com.pemaza.supervisao.model.Filial
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.ui.activity.QuestionarioActivity
import br.com.pemaza.supervisao.ui.fragments.AnotacoesFragment
import br.com.pemaza.supervisao.utilities.QuestionarioExtra
import br.com.pemaza.supervisao.utilities.QuestionarioSecoesExtra
import io.reactivex.Single
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowApplication
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class AnotacaoFragmentTest : BaseRobolectricTestClass() {

    private lateinit var listaAnotacao: List<Anotacao>
    private lateinit var questResponse: AnotacaoResponse

    @Mock
    lateinit var service: PmzAPIAnotacao

    @Before
    fun setup() {
        setupMocks()
    }

    private fun setupMocks() {
        val anotacao = Anotacao(1, 1, 1, "", "", "")
        listaAnotacao = listOf(anotacao)
        questResponse = AnotacaoResponse(5, listaAnotacao, "Teste")
        `when`(service.anotacoesQuestionario("1")).thenReturn(Single.just(questResponse))
    }

    @Test
    fun testFragment() {
        val fragment = AnotacoesFragment()
        startFragment(fragment)
        assertNotNull(fragment)
        val test = fragment.viewModel.listaQuestSubject.test()
        test.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1000MS)
//        fragment.viewModel.listaQuestSubject.onNext(fragment.listaAnotacaoObservable().blockingGet().anotacao)
        test.assertValue { it == listaAnotacao }
    }

    fun startFragment(anotacoesFragment: AnotacoesFragment) {
        val intent = Intent(
            RuntimeEnvironment.application,
            QuestionarioActivity::class.java
        )

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val questionario =
            Questionario("1", 32, "Hoje", dateFormat.parse("01/02/2018"), dateFormat.parse("02/02/2018"), "AB", "20", 5)

        intent.putExtra(QuestionarioSecoesExtra, QuestionarioSecoesResponse(
                1,
                QuestionarioSecoesResponse.ResponseDados(listOf(), Filial("", "", "", "", "")),
                ""
        ))
        intent.putExtra(QuestionarioExtra, questionario)


        val activity = Robolectric.buildActivity(QuestionarioActivity::class.java, intent)
            .create()
            .start()
            .resume()
            .get()

        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(anotacoesFragment, null)
        fragmentTransaction.commit()
    }
}