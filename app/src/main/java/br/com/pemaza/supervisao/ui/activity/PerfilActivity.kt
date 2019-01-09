package br.com.pemaza.supervisao.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.LoginHelper
import kotlinx.android.synthetic.main.activity_perfil.*
import javax.inject.Inject

class PerfilActivity : AppCompatActivity() {
    init {
        MainApplication.appComponent.injectPerfilActivity(this)
    }

    @Inject
    lateinit var loginHelper: LoginHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        perfil_sair_button.setOnClickListener {
            loginHelper.logout()
            val intent = Intent(this, LoginActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        perfil_back_button.setOnClickListener {
            goMain()
        }
    }

    fun goMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        goMain()
        super.onBackPressed()
    }
}
