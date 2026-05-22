package com.example.lab10.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab10.data.PeliculaApiService
import com.example.lab10.data.PeliculaModel

@Composable
fun ContenidoPeliculas(
    servicio: PeliculaApiService
) {

    var listaPeliculas by remember {
        mutableStateOf<ArrayList<PeliculaModel>>(arrayListOf())
    }

    LaunchedEffect(Unit) {

        try {

            listaPeliculas = servicio.selectPeliculas()

        } catch (e: Exception) {

            Log.e("ERROR_PELICULA", e.toString())
        }
    }

    LazyColumn {

        items(listaPeliculas) { item ->

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = item.titulo,
                    fontSize = 22.sp
                )

                Text(text = "Director: ${item.director}")

                Text(text = "Año: ${item.anio}")

                Text(text = "Género: ${item.genero}")
            }
        }
    }
}