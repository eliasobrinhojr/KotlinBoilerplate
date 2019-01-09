package br.com.pemaza.supervisao.dto

data class LoginResponse(var cod: Int,
                         var chave: String,
                         var usuario_nome: String,
                         var usuario_codigo: String,
                         var filial: String,
                         var msg: String)