package com.example.lab10.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lab10.data.PeliculaApiService
import com.example.lab10.data.PeliculaModel
import kotlinx.coroutines.delay

@Composable
fun ContenidoPeliculasListado(
    navController: NavHostController,
    servicio: PeliculaApiService
) {

    var listaPeliculas: SnapshotStateList<PeliculaModel> =
        remember { mutableStateListOf() }

    LaunchedEffect(Unit) {

        try {

            val listado = servicio.selectPeliculas()

            listado.forEach {

                listaPeliculas.add(it)
            }

        } catch (e: Exception) {

            Log.e("ERROR_PELICULAS", e.toString())
        }
    }

    LazyColumn {

        items(listaPeliculas) { item ->

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                verticalAlignment = Alignment.CenterVertically

            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = item.titulo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Text(text = item.director)

                    Text(text = item.genero)
                }

                IconButton(

                    onClick = {

                        navController.navigate(
                            "peliculaVer/${item.id}"
                        )
                    }

                ) {

                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Editar"
                    )
                }

                IconButton(

                    onClick = {

                        navController.navigate(
                            "peliculaDel/${item.id}"
                        )
                    }

                ) {

                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}

@Composable
fun ContenidoPeliculaEditar(

    navController: NavHostController,

    servicio: PeliculaApiService,

    pid:Int = 0

) {

    var id by remember { mutableStateOf(pid) }

    var titulo by remember { mutableStateOf("") }

    var director by remember { mutableStateOf("") }

    var anio by remember { mutableStateOf("") }

    var genero by remember { mutableStateOf("") }

    var grabar by remember { mutableStateOf(false) }

    if (id != 0) {

        LaunchedEffect(Unit) {

            try {

                val pelicula =
                    servicio.selectPelicula(id.toString())

                delay(100)

                titulo = pelicula.body()?.titulo ?: ""

                director = pelicula.body()?.director ?: ""

                anio = pelicula.body()?.anio.toString()

                genero = pelicula.body()?.genero ?: ""

            } catch (e: Exception) {

                Log.e("ERROR_EDITAR", e.toString())
            }
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        TextField(

            value = titulo,

            onValueChange = {
                titulo = it
            },

            label = {
                Text("Titulo")
            }
        )

        TextField(

            value = director,

            onValueChange = {
                director = it
            },

            label = {
                Text("Director")
            }
        )

        TextField(

            value = anio,

            onValueChange = {
                anio = it
            },

            label = {
                Text("Año")
            }
        )

        TextField(

            value = genero,

            onValueChange = {
                genero = it
            },

            label = {
                Text("Genero")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(

            onClick = {

                grabar = true
            }

        ) {

            Text("Guardar")
        }
    }

    if (grabar) {

        val pelicula = PeliculaModel(

            id,

            titulo,

            director,

            anio.toInt(),

            genero
        )

        LaunchedEffect(Unit) {

            try {

                if (id == 0)

                    servicio.insertPelicula(pelicula)

                else

                    servicio.updatePelicula(
                        id.toString(),
                        pelicula
                    )

            } catch (e: Exception) {

                Log.e("ERROR_SAVE", e.toString())
            }
        }

        grabar = false

        navController.navigate("peliculas")
    }
}

@Composable
fun ContenidoPeliculaEliminar(

    navController: NavHostController,

    servicio: PeliculaApiService,

    id:Int

) {

    var borrar by remember {

        mutableStateOf(true)
    }

    if (borrar) {

        LaunchedEffect(Unit) {

            try {

                servicio.deletePelicula(id.toString())

            } catch (e: Exception) {

                Log.e("ERROR_DELETE", e.toString())
            }
        }

        borrar = false

        navController.navigate("peliculas")
    }
}