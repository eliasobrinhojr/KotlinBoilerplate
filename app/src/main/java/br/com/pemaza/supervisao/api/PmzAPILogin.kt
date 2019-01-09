package br.com.pemaza.supervisao.api

import br.com.pemaza.supervisao.dto.LoginResponse
import br.com.pemaza.supervisao.model.Login
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface PmzAPILogin {

    @POST("login.php")
    fun login(@Body login: Login): Single<LoginResponse>
}