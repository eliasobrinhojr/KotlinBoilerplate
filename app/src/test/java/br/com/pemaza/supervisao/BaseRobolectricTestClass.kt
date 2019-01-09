package br.com.pemaza.supervisao

import br.com.pemaza.supervisao.dagger.MainApplication
import br.com.pemaza.supervisao.dagger.components.AppComponent
import br.com.pemaza.supervisao.dagger.modules.AppModule
import br.com.pemaza.supervisao.dagger.modules.DbModule
import br.com.pemaza.supervisao.dagger.modules.NetModule
import br.com.pemaza.supervisao.helpers.enums.RunMode
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        sdk = [21])
abstract class BaseRobolectricTestClass {
    private val appModuleTest = object :AppModule(RuntimeEnvironment.application) {
        override fun provideRunMode(): RunMode {
            return RunMode.TEST
        }
    }

    @Rule
    @JvmField
    val rule: DaggerMockRule<AppComponent> = DaggerMockRule<AppComponent>(AppComponent::class.java, NetModule(), DbModule(RuntimeEnvironment.application), appModuleTest)
            .set{
                (RuntimeEnvironment.application as MainApplication).setComponent(it)
            }
}