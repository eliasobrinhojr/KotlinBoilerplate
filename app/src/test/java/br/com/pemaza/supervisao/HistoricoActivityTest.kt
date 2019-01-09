package br.com.pemaza.supervisao

import br.com.pemaza.supervisao.api.PmzAPIQuestionario
import br.com.pemaza.supervisao.dao.QuestionarioDAO
import br.com.pemaza.supervisao.dao.UsuarioQuestionarioDAO
import br.com.pemaza.supervisao.dto.QuestionarioResponse
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import br.com.pemaza.supervisao.ui.activity.HistoricoActivity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.Robolectric
import java.text.SimpleDateFormat

class HistoricoActivityTest : BaseRobolectricTestClass() {
    private lateinit var listaQuestionario: List<Questionario>
    private lateinit var questResponse: QuestionarioResponse

    @Mock
    lateinit var questApi: PmzAPIQuestionario
    @Mock
    lateinit var userQuestDao: UsuarioQuestionarioDAO
    @Mock
    lateinit var questDao: QuestionarioDAO

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
        Mockito.`when`(questApi.listaQuestionarios("", QuestionarioStatus.Finalizado.valor)).thenReturn(Single.just(questResponse))
        Mockito.doNothing().`when`(userQuestDao).insertListRelacao(listOf(UsuarioQuestionario("", "1")))
        Mockito.`when`(userQuestDao.getListaQuestionarioPorUsuarioAndamentoPendentes("FC", "")).thenReturn(Single.just(listaQuestionario))
        Mockito.`when`(questDao.getListaQuestionarioLimit(QuestionarioStatus.Finalizado.valor, 5)).thenReturn(Single.just(listaQuestionario))
    }

    @Test
    fun sincronizaListaCadeia() {
        val activity = Robolectric.buildActivity(HistoricoActivity::class.java).create().get()
        val testObserver = activity.sincronizaLista().test()
        testObserver.awaitTerminalEvent()
        testObserver.assertValue { it == listaQuestionario}
    }

    @Test
    fun consumerDeveAtualizarListaNaViewModel() {
        val activity = Robolectric.setupActivity(HistoricoActivity::class.java)
        activity.sincronizaLista().subscribe(activity.sincronizaConsumer())
        val testObserver = activity.viewModel.listaQuestSubject.test()
        testObserver.awaitCount(1)
        testObserver.assertValue {it == listaQuestionario}
    }
}