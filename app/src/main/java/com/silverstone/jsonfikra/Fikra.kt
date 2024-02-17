package com.silverstone.jsonfikra

import java.io.Serializable

data class Fikra(
    val id: Int,
    val title: String,
    val content: String,
    val isFav: Int,
    val category: String
):Serializable