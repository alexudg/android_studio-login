package com.planetsistemas.login

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MainPage(navController: NavController, viewModel: LoginViewModel = LoginViewModel()) {
    val context = LocalContext.current

    // next for scrolling if show keyboard
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val focus = remember { FocusRequester() }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
        focus.requestFocus()
    }

    // traditional values of TextField
    //var email: String by rememberSaveable { mutableStateOf("") }
    //var pass: String by rememberSaveable { mutableStateOf("") }
    var isShowDlg by rememberSaveable { mutableStateOf(false) }

    // new method viewModel
    val email: String by viewModel.email.observeAsState(initial = "")
    val pass: String by viewModel.pass.observeAsState(initial = "")
    val isBtnSessionEnable: Boolean by viewModel.isBtnSessionEnabled.observeAsState(initial = false)

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // drive for imeState if show Keyboard
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Green)
            )
            Image(
                painter = painterResource(id = R.drawable.logo_leones_negros),
                contentDescription = "Logo"
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = email,
                onValueChange = { viewModel.onLoginChanged(it, pass) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focus),
                placeholder = { Text(text = "Correo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = pass,
                onValueChange = { viewModel.onLoginChanged(email, it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Olvidaste la contraseña",
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "click contraseña",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                color = Color(0xFFFF9800)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { isShowDlg = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF005294),
                    disabledContainerColor = Color(0xFF637B8F)
                ),
                enabled = isBtnSessionEnable
            ) {
                Text(text = "Iniciar sesión")
            }

            if (isShowDlg) {
                // android.compose.material3
                AlertDialog(
                    icon = { Icon(Icons.Default.Info, "Info", Modifier, Color.Blue) },
                    title = { Text("Ingreso de usuario") },
                    text = { Text("¿Estás segur@ de ingresar con tu usuario $email?") },
                    onDismissRequest = {
                        isShowDlg = false
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isShowDlg = false
                                navController.navigate(Screens.SecondPage.route + "/$email")
                            }
                        ) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                viewModel.onLoginChanged("", "")
                                isShowDlg = false
                                focus.requestFocus()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            )
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }

}