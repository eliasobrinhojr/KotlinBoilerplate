package br.com.pemaza.supervisao.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Secao(
        @SerializedName("id_agenda_questionario")
        val idAgendaQuestionario: String,
        @SerializedName("id_questionario")
        val idQuestionario: String,
        @SerializedName("descricao")
        val descricao: String,
        @SerializedName("situacao")
        val situacao: String
): Serializable