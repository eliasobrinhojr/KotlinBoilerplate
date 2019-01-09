package br.com.pemaza.supervisao.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.LoginHelper
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {
    init {
        MainApplication.appComponent.injectSplashScreenActivity(this)
    }

    @Inject
    lateinit var loginHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics(), Answers())
        setContentView(R.layout.activity_splash_screen)
        observaTela()
    }

    fun observaTela() {
        val subs = telaObservable()
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe({ proximaTela(it) }, {})
        disposer.add(subs)
    }

    private fun telaObservable(): Single<Class<out AppCompatActivity>> {
        return Single.just(verificaTela())
    }

    private fun verificaTela(): Class<out AppCompatActivity> {
        return if (estaLogado()) {
            MainActivity::class.java
        } else {
            LoginActivity::class.java
        }
    }

    private fun estaLogado() = loginHelper.inicializaLogin()

    fun proximaTela(tela: Class<out AppCompatActivity>) {
        val intent = Intent(this, tela)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()
    }
}
