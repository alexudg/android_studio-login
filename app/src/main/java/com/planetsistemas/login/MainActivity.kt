package com.planetsistemas.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.planetsistemas.login.api.ApiService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    //var isKeepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var isKeepSplashScreen = true
        //val service = ApiService.makeIAPiService()

        // splash screen
        // implementation("androidx.core:core-splashscreen:1.0.1")
        // create new theme on values/themes: parent="Theme.SplashScreen"
        //installSplashScreen().setKeepOnScreenCondition { isKeepSplashScreen }
        installSplashScreen()

        /*
        println("before http")

        runBlocking {
            val products = service.getAll()
            println(products)
            isKeepSplashScreen = false
            println("hide splash")
        }
        */

        /*
        lifecycleScope.launch {
            val products = service.getAll()
            println(products)
            isKeepSplashScreen = true
            println("hide splash")
        }
        */

        println("after http")

        /*
        runBlocking {
            delay(0)
            isKeepSplashScreen = false
        }
        */

        setContent {
            // navigation drive screen
            Navigation()
        }
    }
}
