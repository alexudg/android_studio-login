package com.planetsistemas.login

// dependence
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    private val _pass = MutableLiveData<String>()
    private val _isBtnSessionEnabled = MutableLiveData<Boolean>()
    val email: LiveData<String> = _email
    val pass: LiveData<String> = _pass
    val isBtnSessionEnabled = _isBtnSessionEnabled

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPass(pass: String): Boolean = pass.length > 6

    fun onLoginChanged(email: String, pass: String) {
        _email.value = email
        _pass.value = pass
        _isBtnSessionEnabled.value = isValidEmail(email) && isValidPass(pass)
    }
}