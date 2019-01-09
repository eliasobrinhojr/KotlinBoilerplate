package br.com.pemaza.supervisao.api

import br.com.pemaza.supervisao.dto.QuestionarioResponse
import br.com.pemaza.supervisao.dto.QuestionarioSecoesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PmzAPIQuestionario {

    @GET("agendamentos.php/{id_usuario}")
    fun listaQuestionarios(
        @Query("id_usuario") id_usuario: String,
        @Query("tipo") tipo: String
    ): Single<QuestionarioResponse>

    @GET("questionario.php")
    fun secoesQuestionario(@Query(value = "id_agendamento") agendamento: String): Single<QuestionarioSecoesResponse>
}