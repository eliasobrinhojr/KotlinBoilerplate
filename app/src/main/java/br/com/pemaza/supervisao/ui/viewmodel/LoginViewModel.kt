package br.com.pemaza.supervisao.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import br.com.pemaza.supervisao.extensions.toRx
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class LoginViewModel : ViewModel() {
    val campoUsuario = ObservableField<String>()
    val campoSenha = ObservableField<String>()
    val formularioValido: Observable<Boolean> =
        Observables.combineLatest(campoUsuario.toRx(), campoSenha.toRx()) { usuario, senha ->
            usuario.isNotEmpty() && senha.isNotEmpty()
        }
}