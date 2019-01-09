package br.com.pemaza.supervisao.dto

import br.com.pemaza.supervisao.model.Anotacao
import java.io.Serializable

data class AnotacaoResponse(
    var cod: Int,
    var anotacao: List<Anotacao>,
    var msg: String
): Serializable