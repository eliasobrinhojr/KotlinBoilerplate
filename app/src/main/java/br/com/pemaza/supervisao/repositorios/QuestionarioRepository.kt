package br.com.pemaza.supervisao.repositorios

import br.com.pemaza.supervisao.api.PmzAPIQuestionario
import br.com.pemaza.supervisao.dao.QuestionarioDAO
import br.com.pemaza.supervisao.dao.UsuarioQuestionarioDAO
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import io.reactivex.Completable
import javax.inject.Inject

class QuestionarioRepository
    @Inject constructor(
    val questionarioDao: QuestionarioDAO,
    val usuarioQuestionarioDAO: UsuarioQuestionarioDAO,
    val API: PmzAPIQuestionario
) {
    fun getListaQuestionarioApi(id_usuario: String, tipo: String = "") = API.listaQuestionarios(id_usuario, tipo)

    fun getListaQuestionarioInterno(situacao: String) = questionarioDao.getListaQuestionario(situacao)
    fun getListaQuestionarioInternoPorUsuario(situacao: String, id_usuario: String) =
        usuarioQuestionarioDAO.getListaQuestionarioPorUsuario(situacao, id_usuario)

    fun getListaQuestionarioInternoAndamentoPendentes(id_usuario: String) =
        usuarioQuestionarioDAO.getListaQuestionarioPorUsuarioAndamentoPendentes(
            QuestionarioStatus.Finalizado.valor,
            id_usuario
        )

    fun addOrUpdateQuestionarioInterno(questionario: List<Questionario>): Completable {
        return Completable.fromAction { questionarioDao.insertOrUpdateQuestionario(questionario) }
    }

    fun addOrUpdateRelacaoUsuarioQuestionarioLista(lista: List<UsuarioQuestionario>): Completable {
        return Completable.fromAction {usuarioQuestionarioDAO.insertListRelacao(lista)}
    }

    fun getListaQuestionarioInternoLimit(situacao: String, limite: Int) =
        questionarioDao.getListaQuestionarioLimit(situacao, limite)

    fun getQuestionarioSecoes(id: String) = API.secoesQuestionario(id)
}