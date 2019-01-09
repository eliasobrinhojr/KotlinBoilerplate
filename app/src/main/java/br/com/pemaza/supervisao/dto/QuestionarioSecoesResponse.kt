package br.com.pemaza.supervisao.dto

import br.com.pemaza.supervisao.model.Filial
import br.com.pemaza.supervisao.model.Secao
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuestionarioSecoesResponse(
        @SerializedName("cod")
        val cod: Int,
        @SerializedName("dados")
        val dados: ResponseDados,
        @SerializedName("msg")
        val msg: String
): Serializable {
    class ResponseDados(
            @SerializedName("grupos")
            val secoes: List<Secao>,
            @SerializedName("filial")
            val filial: Filial
    ): Serializable
}