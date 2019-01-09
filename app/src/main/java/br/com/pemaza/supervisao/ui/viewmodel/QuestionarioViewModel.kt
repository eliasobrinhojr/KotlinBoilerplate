package br.com.pemaza.supervisao.ui.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.pemaza.supervisao.model.Questionario
import io.reactivex.subjects.BehaviorSubject

class QuestionarioViewModel : ViewModel() {
    val listaQuestSubject = BehaviorSubject.create<List<Questionario>>()
}