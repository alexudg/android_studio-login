package com.planetsistemas.login

sealed class Screens(val route: String) {
    data object MainPage: Screens("main_page")
    data object SecondPage: Screens("second_page")
}