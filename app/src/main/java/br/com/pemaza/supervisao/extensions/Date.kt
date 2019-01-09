package br.com.pemaza.supervisao.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toPtBr(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(this)
}