package br.com.pemaza.supervisao.api

import br.com.pemaza.supervisao.dto.AnotacaoResponse
import br.com.pemaza.supervisao.model.Anotacao
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PmzAPIAnotacao {

    @GET("questionarioAnotacoes.php")
    fun anotacoesQuestionario(@Query(value = "id_agendamento") agendamento: String): Single<AnotacaoResponse>

    @POST("questionarioAnotacoes.php")
    fun saveOrUpdateAnotacaoApi(@Body t: Anotacao): Single<Anotacao>
}