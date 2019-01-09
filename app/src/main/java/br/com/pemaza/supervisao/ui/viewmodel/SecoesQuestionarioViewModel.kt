package br.com.pemaza.supervisao.ui.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.model.Filial
import br.com.pemaza.supervisao.model.Secao
import io.reactivex.subjects.BehaviorSubject

class SecoesQuestionarioViewModel : ViewModel() {
    init {
        MainApplication.viewModelComponent.questSecoesInject(this)
    }

    val filial = BehaviorSubject.create<Filial>()
    var secoes = BehaviorSubject.create<List<Secao>>()
}