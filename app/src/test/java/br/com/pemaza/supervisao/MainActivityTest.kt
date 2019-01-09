package br.com.pemaza.supervisao

import br.com.pemaza.supervisao.api.PmzAPIQuestionario
import br.com.pemaza.supervisao.dao.QuestionarioDAO
import br.com.pemaza.supervisao.dao.UsuarioQuestionarioDAO
import br.com.pemaza.supervisao.dto.QuestionarioResponse
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import br.com.pemaza.supervisao.ui.activity.MainActivity
import io.reactivex.Single
import io.reactivex.observers.BaseTestConsumer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.robolectric.Robolectric
import java.text.SimpleDateFormat

class MainActivityTest: BaseRobolectricTestClass() {
    private lateinit var listaQuestionario: List<Questionario>
    private lateinit var questResponse: QuestionarioResponse

    @Mock lateinit var questApi: PmzAPIQuestionario
    @Mock lateinit var userQuestDao: UsuarioQuestionarioDAO
    @Mock lateinit var questDao: QuestionarioDAO

    @Before
    fun setup() {
        setupVal()
        setupMocks()
    }

    private fun setupVal() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val questionario = Questionario("1", 32, "Hoje", dateFormat.parse("01/02/2018"), dateFormat.parse("02/02/2018"), "AB", "20", 5)
        listaQuestionario = listOf(questionario)
        questResponse = QuestionarioResponse(0, listaQuestionario, "Teste")
    }

    private fun setupMocks() {
        `when`(questApi.listaQuestionarios("", "")).thenReturn(Single.just(questResponse))
        doNothing().`when`(userQuestDao).insertListRelacao(listOf(UsuarioQuestionario("", "1")))
        `when`(userQuestDao.getListaQuestionarioPorUsuarioAndamentoPendentes("FC", "")).thenReturn(Single.just(listaQuestionario))
        `when`(questDao.getListaQuestionario("")).thenReturn(Single.just(listaQuestionario))
    }

    @Test
    fun sincronizaListaCadeia() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val testObserver = activity.sincronizaListaQuestionario().test()
        testObserver.awaitTerminalEvent()
        testObserver.assertValue { it == listaQuestionario}
    }

    @Test
    fun consumerDeveAtualizarListaNaViewModel() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        activity.sincronizaListaQuestionario().subscribe(activity.sincronizaConsumer())
        val testObserver = activity.viewModel.listaQuestSubject.test()
        testObserver.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1000MS)
        testObserver.assertValue {it == listaQuestionario}
    }
}