package br.com.pemaza.supervisao.dto

import br.com.pemaza.supervisao.model.Questionario

data class QuestionarioResponse(
    var cod: Int,
    var dados: List<Questionario>,
    var msg: String
)