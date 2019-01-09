package br.com.pemaza.supervisao.helpers.interceptors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.helpers.LoginHelper
import br.com.pemaza.supervisao.ui.activity.LoginActivity
import br.com.pemaza.supervisao.utilities.UnauthorizedExtra
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RedirectInterceptor : Interceptor {
    @Inject
    lateinit var contexto: Context

    @Inject
    lateinit var loginHelper: LoginHelper

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
        MainApplication.appComponent.injectRedirect(this)
        val response = chain.proceed(newRequest)
        Log.d("Redirect", "Codigo: ${response.code()}")
        if (response.code() == 401) {
            val intent = Intent(contexto, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(UnauthorizedExtra, "Login expirou!")
            response.close()
            loginHelper.logout()
            contexto.startActivity(intent)
            (contexto as Activity).finish()

        }
        return response
    }
}