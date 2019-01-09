package br.com.pemaza.supervisao.interfaces

import br.com.pemaza.supervisao.model.Questionario

interface listenerItens {

    var items: MutableList<Questionario>
    fun notifyDataSetChanged()
}