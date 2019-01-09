package br.com.pemaza.supervisao.dagger.components

import br.com.pemaza.supervisao.dagger.modules.AppModule
import br.com.pemaza.supervisao.dagger.modules.DbModule
import br.com.pemaza.supervisao.dagger.modules.NetModule
import br.com.pemaza.supervisao.helpers.interceptors.RedirectInterceptor
import br.com.pemaza.supervisao.helpers.interceptors.TokenInterceptor
import br.com.pemaza.supervisao.ui.activity.LoginActivity
import br.com.pemaza.supervisao.ui.activity.PerfilActivity
import br.com.pemaza.supervisao.ui.activity.SplashScreenActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DbModule::class, AppModule::class])
interface AppComponent {
    fun injectRedirect(interceptor: RedirectInterceptor)
    fun injectTokenInterceptor(interceptor: TokenInterceptor)
    fun injectLoginActivity(activity: LoginActivity)
    fun injectPerfilActivity(perfilActivity: PerfilActivity)
    fun injectSplashScreenActivity(splashScreenActivity: SplashScreenActivity)

    fun viewModelComponent(): ViewModelComponent
    fun fragmentComponent(): FragmentComponent
    fun activityComponent(): ActivityComponent
}