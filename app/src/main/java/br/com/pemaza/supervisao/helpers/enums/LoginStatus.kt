package br.com.pemaza.supervisao.helpers.enums

enum class LoginStatus(val valor: Int) {
    Ok(0),
    SenhaIncorreta(1),
    UsuarioInexistente(2)
}