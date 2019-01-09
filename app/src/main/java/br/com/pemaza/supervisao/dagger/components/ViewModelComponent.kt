package br.com.pemaza.supervisao.dagger.components

import br.com.pemaza.supervisao.ui.activity.HistoricoActivity
import br.com.pemaza.supervisao.ui.activity.MainActivity
import br.com.pemaza.supervisao.ui.viewmodel.*
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {
    fun vmInject(viewModel: LoginViewModel)

    fun questionarioInject(questVm: QuestionarioViewModel)
    fun loginVmInject(loginVm: LoginViewModel)
    fun questSecoesInject(qstSec: SecoesQuestionarioViewModel)
}