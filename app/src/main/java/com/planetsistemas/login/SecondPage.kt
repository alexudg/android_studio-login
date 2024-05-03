package com.planetsistemas.login

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.planetsistemas.login.api.ApiService
import com.planetsistemas.login.api.Product
import com.planetsistemas.login.api.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.runInterruptible
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondPage(navController: NavController, email: String?) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Mi primera chamba")
                }
            })
        }
    ) { innerPadding ->
        BodyContent(navController, innerPadding, email!!)
    }
}

@Composable
fun BodyContent(navController: NavController, innerPadding: PaddingValues, email: String) {
    val apiService = ApiService.makeIAPiService()

    val context = LocalContext.current

    var result: Result
    var products by rememberSaveable {
        mutableStateOf(listOf<Product>())
    }

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenid@ \n${email}")
        Button(
            onClick = {
                products = emptyList()
                isLoading = true
                CoroutineScope(Dispatchers.Main).launch {

                    val deferred = async { apiService.getAll() }
                    result = deferred.await()
                    products = result.products
                    println(products)
                    isLoading = false
                }
            }
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
            else {
                Text("Datos")
            }
        }
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("Cerrar sesión")
        }
        if (products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Sin productos")
            }
        }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    Column {
                        Text(product.title)
                        Text(product.description)
                        Text(product.price.toString())
                        Divider()
                    }

                }
            }
        }
    }
}