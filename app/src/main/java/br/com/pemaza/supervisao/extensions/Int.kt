package br.com.pemaza.supervisao.extensions

fun Int.toCategorias(): String {
    return if(this == 1) {
        "• $this categoria"
    } else {
        "• $this categorias"
    }
}