package br.com.pemaza.supervisao.dagger.modules

import br.com.pemaza.supervisao.BuildConfig
import br.com.pemaza.supervisao.api.PmzAPIAnotacao
import br.com.pemaza.supervisao.api.PmzAPILogin
import br.com.pemaza.supervisao.api.PmzAPIQuestionario
import br.com.pemaza.supervisao.helpers.interceptors.RedirectInterceptor
import br.com.pemaza.supervisao.helpers.interceptors.TokenInterceptor
import br.com.pemaza.supervisao.utilities.PmzApiUrl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetModule {

    @Provides
    @Singleton
    open fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor())
            .addInterceptor(RedirectInterceptor())
        val gson = GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm:ss")
                .setLenient()
                .create()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        return Retrofit.Builder()
            .baseUrl(PmzApiUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    open fun provideLoginApi(retrofit: Retrofit): PmzAPILogin {
        return retrofit.create(PmzAPILogin::class.java)
    }

    @Provides
    open fun provideQuestionarioService(retrofit: Retrofit): PmzAPIQuestionario {
        return retrofit.create(PmzAPIQuestionario::class.java)
    }

    @Provides
    fun provideAnotacaoService(retrofit: Retrofit): PmzAPIAnotacao {
        return retrofit.create(PmzAPIAnotacao::class.java)
    }
}