package br.com.pemaza.supervisao.helpers

import android.content.Context
import br.com.pemaza.supervisao.dao.UsuarioDAO
import br.com.pemaza.supervisao.dto.LoginResponse
import br.com.pemaza.supervisao.model.Usuario
import br.com.pemaza.supervisao.utilities.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginHelper @Inject constructor(var context: Context, var usuarioDAO: UsuarioDAO) {
    var APP_TOKEN = ""
    private set
    var usuario: Usuario = Usuario("","","")

    fun login(login: LoginResponse) {
        insereUsuarioPreferences(login)
        inicializaUsuario()
        insereUsuarioBanco()
        APP_TOKEN = login.chave
    }

    private fun insereUsuarioBanco() {
        runOnIoThread {
            usuarioDAO.insertOrUpdateUsuario(usuario)
        }
    }

    private fun insereUsuarioPreferences(login: LoginResponse) {
        val pref = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE)
        with(pref.edit()) {
            putString(TokenKey, login.chave)
            putString(UsuarioKey, login.usuario_nome)
            putString(UsuarioCodKey, login.usuario_codigo)
            putString(UsuarioFilialKey, login.filial)
            apply()
        }
    }

    private fun inicializaUsuario() {
        val pref = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE)
        with(pref) {
            usuario = Usuario(
                usuario_nome = getString(UsuarioKey, "vazio"),
                usuario_codigo = getString(UsuarioCodKey, "vazio"),
                filial = getString(UsuarioFilialKey, "vazio")
            )
        }
    }

    fun logout() {
        val pref = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE)
        with(pref.edit()) {
            remove(TokenKey)
            remove(UsuarioKey)
            remove(UsuarioCodKey)
            remove(UsuarioFilialKey)
            apply()
        }
        APP_TOKEN = ""
    }

    fun inicializaLogin(): Boolean {
        val pref = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE)
        val token = pref.getString(TokenKey, "")
        return if (token != "") {
            inicializaUsuario()
            APP_TOKEN = token
            true
        } else {
            false
        }
    }
}