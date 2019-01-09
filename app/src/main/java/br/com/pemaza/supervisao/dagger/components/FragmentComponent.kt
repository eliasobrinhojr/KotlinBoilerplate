package br.com.pemaza.supervisao.dagger.components

import br.com.pemaza.supervisao.ui.fragments.AnotacoesFragment
import br.com.pemaza.supervisao.ui.fragments.MainFragment
import br.com.pemaza.supervisao.ui.fragments.NovaAnotacaoFragment
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {
    fun mainFragInject(mainFrag: MainFragment)
    fun anotacaoFragInject(anotacaoFrag: AnotacoesFragment)
    fun novaAnotacaoFragInject(novaAnotacaoFragment: NovaAnotacaoFragment)
}