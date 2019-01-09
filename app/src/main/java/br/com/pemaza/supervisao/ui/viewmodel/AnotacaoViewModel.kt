package br.com.pemaza.supervisao.ui.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import br.com.pemaza.supervisao.extensions.toRx
import br.com.pemaza.supervisao.model.Anotacao
import br.com.pemaza.supervisao.model.Questionario
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject

class AnotacaoViewModel : ViewModel() {

    val titulo = ObservableField<String>()
    val comentario = ObservableField<String>()
    val formularioValido: Observable<Boolean> =
        Observables.combineLatest(titulo.toRx(), comentario.toRx()) { titulo, comentario ->
            titulo.isNotEmpty() && comentario.isNotEmpty()
        }

    val btnAnotarHabilitado = BehaviorSubject.create<Boolean>()
    val actionFecharFragmentNovaAnotacao = BehaviorSubject.create<Any>()
    val listaQuestSubject = BehaviorSubject.create<List<Anotacao>>()

    val anotacaoSubject = BehaviorSubject.create<Anotacao>()
    val questionarioSubject = BehaviorSubject.create<Questionario>()
}