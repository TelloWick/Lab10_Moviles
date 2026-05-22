package com.example.lab10.data

import com.google.gson.annotations.SerializedName

data class PeliculaModel(

    @SerializedName("id")
    var id:Int,

    @SerializedName("titulo")
    var titulo:String,

    @SerializedName("director")
    var director:String,

    @SerializedName("anio")
    var anio:Int,

    @SerializedName("genero")
    var genero:String
)