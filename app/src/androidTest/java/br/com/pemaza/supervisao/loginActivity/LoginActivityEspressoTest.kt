package br.com.pemaza.supervisao.loginActivity

import android.content.Context
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.pemaza.supervisao.ui.activity.LoginActivity
import br.com.pemaza.supervisao.ui.activity.MainActivity
import br.com.pemaza.supervisao.utilities.SharedPrefName
import br.com.pemaza.supervisao.utilities.TokenKey
import junit.framework.AssertionFailedError
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityEspressoTest {
    private lateinit var context: Context

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(LoginActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
        context = getInstrumentation().targetContext
        val prefEditor = context.getSharedPreferences(SharedPrefName, Context.MODE_PRIVATE).edit()
        with(prefEditor) {
            putString(TokenKey, "")
            commit()
        }
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test(expected = AssertionFailedError::class)
    fun deveNegarLoginComCredenciaisErradas() {
        val loginRobot = LoginRobot(context)
        loginRobot.setUser("Teste").setPassword("Teste").clickLogin()

        intended(IntentMatchers.anyIntent())
    }

    @Test
    fun devePermitirLoginComCredenciaisCorretas() {
        val loginRobot = LoginRobot(context)
        loginRobot.setUser("ricardo").setPassword("013579").clickLogin()

        intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
    }
}