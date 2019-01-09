package br.com.pemaza.supervisao.helpers

import br.com.pemaza.supervisao.helpers.enums.CardSecoesEnum
import br.com.pemaza.supervisao.model.Secao

class EstadoQuestionario(val estado: CardSecoesEnum, val secoes: List<Secao>)