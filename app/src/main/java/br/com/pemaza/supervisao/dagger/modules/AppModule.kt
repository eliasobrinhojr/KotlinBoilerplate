package br.com.pemaza.supervisao.dagger.modules

import android.app.Application
import android.content.Context
import br.com.pemaza.supervisao.helpers.LoginHelper
import br.com.pemaza.supervisao.helpers.enums.RunMode
import br.com.pemaza.supervisao.model.Usuario
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    open fun provideRunMode() = RunMode.NORMAL
}