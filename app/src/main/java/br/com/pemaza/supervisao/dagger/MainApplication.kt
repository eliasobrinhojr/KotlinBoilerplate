package br.com.pemaza.supervisao.dagger

import android.app.Application
import android.os.StrictMode
import android.support.annotation.VisibleForTesting
import android.util.Log
import br.com.pemaza.supervisao.BuildConfig
import br.com.pemaza.supervisao.dagger.components.*
import br.com.pemaza.supervisao.dagger.modules.AppModule
import br.com.pemaza.supervisao.dagger.modules.DbModule
import br.com.pemaza.supervisao.dagger.modules.NetModule


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
        strictMode()
    }

    private fun initDagger() {
        val appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dbModule(DbModule(this))
                .netModule(NetModule())
                .build()
        setComponent(appComponent)
    }

    private fun strictMode() {
        if (BuildConfig.DEBUG) {
            Log.d("Application", "Strict Mode")
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }
    }

    fun setComponent(component: AppComponent) {
        appComponent = component
        viewModelComponent = appComponent.viewModelComponent()
        fragmentComponent = appComponent.fragmentComponent()
        actComponent = appComponent.activityComponent()
    }

    companion object {
        @VisibleForTesting
        lateinit var appComponent: AppComponent
        lateinit var actComponent: ActivityComponent
        lateinit var viewModelComponent: ViewModelComponent
        lateinit var fragmentComponent: FragmentComponent
    }
}