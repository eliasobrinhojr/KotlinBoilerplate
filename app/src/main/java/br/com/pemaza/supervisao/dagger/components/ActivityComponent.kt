package br.com.pemaza.supervisao.dagger.components

import br.com.pemaza.supervisao.ui.activity.HistoricoActivity
import br.com.pemaza.supervisao.ui.activity.MainActivity
import br.com.pemaza.supervisao.ui.activity.QuestionarioActivity
import dagger.Subcomponent

@Subcomponent
interface ActivityComponent {
    fun mainActInject(mainAct: MainActivity)
    fun histActInjet(histAct: HistoricoActivity)
    fun questActInject(questAct: QuestionarioActivity)
}