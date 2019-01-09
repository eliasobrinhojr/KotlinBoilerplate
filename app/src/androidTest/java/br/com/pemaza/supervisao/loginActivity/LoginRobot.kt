package br.com.pemaza.supervisao.loginActivity

import android.content.Context
import android.support.test.espresso.ViewInteraction
import br.com.pemaza.supervisao.BaseTestRobot
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.ui.activity.LoginActivity

class LoginRobot(private val context: Context): BaseTestRobot() {
    fun setUser(user: String) = apply {
        fillEditText(R.id.login_username_text, user)
    }

    fun setPassword(pass: String) = apply {
        fillEditText(R.id.login_password_text, pass)
    }

    fun clickLogin() = apply {
        clickButton(R.id.login_button)
    }

    fun matchErrorText(): ViewInteraction {
        return matchText(textView(android.R.id.message),"Usuário invalido")
    }
}