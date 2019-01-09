package br.com.pemaza.supervisao.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import br.com.pemaza.supervisao.api.PmzAPIAnotacao
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.databinding.FragmentNovaAnotacaoBinding
import br.com.pemaza.supervisao.model.Anotacao
import br.com.pemaza.supervisao.ui.viewmodel.AnotacaoViewModel
import br.com.pemaza.supervisao.utilities.AnotacaoExtra
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NovaAnotacaoFragment : Fragment() {

    lateinit var viewModel: AnotacaoViewModel
    lateinit var binding: FragmentNovaAnotacaoBinding
    private val disposer = CompositeDisposable()
    lateinit var anotacao: Anotacao

    @Inject
    lateinit var service: PmzAPIAnotacao

    init {
        MainApplication.fragmentComponent.novaAnotacaoFragInject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNovaAnotacaoBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(requireActivity()).get(AnotacaoViewModel::class.java)
        binding.viewModel = viewModel

        validaFormulario()
        salvarClick()
        cancelarClick()
        getExtra()

        return binding.root
    }

    fun salvarClick() {
        binding.novaAnotacaoSalvarBtn.setOnClickListener {
            saveOrUpdateAnotacao()
        }
    }

    fun saveOrUpdateAnotacao() {
        anotacao.titulo = binding.novaAnotacaoTitulo.text.toString()
        anotacao.observacao = binding.novaAnotacaoComentario.text.toString()
        service.saveOrUpdateAnotacaoApi(anotacao)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                recarregaListaAnotacoes()
            }
            .subscribe({
                Toast.makeText(context, "Sucesso", Toast.LENGTH_SHORT).show()
                viewModel.actionFecharFragmentNovaAnotacao.onNext(Any())

            }, {
                Toast.makeText(context, "Falha comunicação com o servidor \n ${it.message}", Toast.LENGTH_LONG).show()
            })
    }

    private fun recarregaListaAnotacoes() {
        service.anotacoesQuestionario(viewModel.questionarioSubject.value!!.id_agendamento)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.listaQuestSubject.onNext(it.anotacao)
                escondeTeclado()
            }, {
                Toast.makeText(context, "Falha comunicação com o servidor \n ${it.message}", Toast.LENGTH_LONG).show()
            })
    }

    private fun escondeTeclado() {
        val inputManager: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }

    private fun cancelarClick() {
        binding.novaAnotacaoCancelarBtn.setOnClickListener {
            viewModel.actionFecharFragmentNovaAnotacao.onNext(Any())
        }
    }

    private fun getExtra() {
        val bundle = this.arguments
        if (bundle != null) {
            anotacao = bundle.getSerializable(AnotacaoExtra) as Anotacao
            setAnotacaoEditar()
        }
    }

    fun setAnotacaoEditar() {
        binding.viewModel!!.titulo.set(anotacao.titulo)
        binding.viewModel!!.comentario.set(anotacao.observacao)
    }

    private fun validaFormulario() {
        val subs = viewModel.formularioValido.subscribe {
            binding.novaAnotacaoSalvarBtn.isEnabled = it
        }
        disposer.add(subs)
    }

    override fun onDestroy() {
        disposer.dispose()
        super.onDestroy()
    }
}