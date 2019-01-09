package br.com.pemaza.supervisao

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.pemaza.supervisao.ui.activity.LoginActivity
import br.com.pemaza.supervisao.ui.activity.MainActivity
import br.com.pemaza.supervisao.ui.activity.SplashScreenActivity
import br.com.pemaza.supervisao.utilities.SharedPrefName
import br.com.pemaza.supervisao.utilities.TokenKey
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    private val intent = Intent()
    private lateinit var prefEditor: SharedPreferences.Editor

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(SplashScreenActivity::class.java, true, false)

    @Before
    fun setup(){
        Intents.init()
        val context = getInstrumentation().targetContext
        prefEditor = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE).edit()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun vaiParaLoginSeNaoLogado() {
        with(prefEditor) {
            putString(TokenKey, "")
            commit()
        }
        mActivityRule.launchActivity(intent)
        Thread.sleep(3000)
        intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun vaiParaMainSeLogado() {
        with(prefEditor) {
            putString(TokenKey, "qualquerchave")
            commit()
        }
        mActivityRule.launchActivity(intent)
        Thread.sleep(3000)
        intended(hasComponent(MainActivity::class.java.name))
    }
}