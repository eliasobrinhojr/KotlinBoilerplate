package br.com.pemaza.supervisao.helpers.enums

import android.graphics.Color
import br.com.pemaza.supervisao.R

enum class CardSecoesEnum(val icone: Int, val titulo: String, val cor: Int) {
    PD(R.drawable.icon_secao_estado_pendente, " Pendente", Color.BLACK),
    AB(R.drawable.icon_secao_estado_andamento, " Em Andamento", Color.parseColor("#f7a52c")),
    FC(R.drawable.icon_secao_estado_concluido, " Concluído", Color.parseColor("#308dcc"))
}