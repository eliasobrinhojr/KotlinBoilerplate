package br.com.pemaza.supervisao.model

import java.io.Serializable

class Anotacao(
    var id_anotacao: Int,
    var id_agendamento: Int,
    var id_avaliador: Int,
    var titulo: String,
    var observacao: String,
    var situacao: String): Serializable
