package br.com.pemaza.supervisao.ui.fragments

import android.app.FragmentTransaction
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.model.Anotacao
import br.com.pemaza.supervisao.ui.viewmodel.AnotacaoViewModel
import br.com.pemaza.supervisao.ui.viewmodel.SecoesQuestionarioViewModel
import br.com.pemaza.supervisao.utilities.AnotacaoExtra
import br.com.pemaza.supervisao.utilities.NovaAnotacaoFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_questionario_aberto.*

class QuestionarioAbertoFragment : Fragment() {

    lateinit var secoesViewModel: SecoesQuestionarioViewModel
    lateinit var anotacaoViewModel: AnotacaoViewModel
    val disposer = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questionario_aberto, container, false)

        secoesViewModel = ViewModelProviders.of(requireActivity()).get(SecoesQuestionarioViewModel::class.java)
        anotacaoViewModel = ViewModelProviders.of(requireActivity()).get(AnotacaoViewModel::class.java)

        anotacaoViewModel.anotacaoSubject.subscribe {
            abreNovaAnotacao(it)
        }

        estadoBtnAnotar()
        criaFragments()

        return view
    }

    fun abreNovaAnotacao(t: Anotacao) {
        anotacaoViewModel.btnAnotarHabilitado.onNext(false)
//    activity.fragmentManager.beginTransaction()
        childFragmentManager.fragments[1].childFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_frame_anotacao, putSerializable(t))
                .addToBackStack(NovaAnotacaoFragment)
                .commit()
        focusFragment()
    }

    private fun focusFragment() {
        nestedScrollView.scrollTo(0, container_frame_anotacoes.top)
    }

    private fun putSerializable(anotacao: Anotacao): NovaAnotacaoFragment {
        val novaAnotacaoFragment = NovaAnotacaoFragment()
        val bundle = Bundle()
        bundle.putSerializable(AnotacaoExtra, anotacao)
        novaAnotacaoFragment.arguments = bundle
        return novaAnotacaoFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        anotarClick()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun anotarClick() {
        questionario_anotar_btn.setOnClickListener {
            anotacaoViewModel.anotacaoSubject.onNext(Anotacao(0, 0, 0, "", "", ""))
        }
    }

    private fun criaFragments() {
        val primaryFragment = AnotacoesFragment()
        childFragmentManager.beginTransaction()
                .add(R.id.container_frame_avaliacao, QuestionarioAvaliacaoFragment())
                .add(R.id.container_frame_anotacoes, primaryFragment)
                .setPrimaryNavigationFragment(primaryFragment)
                .commit()
    }

    private fun estadoBtnAnotar() {
        val subs = anotacaoViewModel.btnAnotarHabilitado
                .subscribe {
                    questionario_anotar_btn.isEnabled = it
                }
        disposer.add(subs)
    }

    override fun onDestroy() {
        disposer.dispose()
        super.onDestroy()
    }
}
