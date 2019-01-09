package br.com.pemaza.supervisao.repositorios

import br.com.pemaza.supervisao.api.PmzAPIAnotacao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnotacaoRepository
@Inject constructor(
    val API: PmzAPIAnotacao
) {
    fun getListaAnotacaoApi(id_agendamento: String) = API.anotacoesQuestionario(id_agendamento)
}