package com.silverstone.jsonfikra.ReadAsset

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.gson.Gson
import com.silverstone.jsonfikra.Fikra
import com.silverstone.jsonfikra.FikraListScreen
import java.io.InputStreamReader

fun readFikralarFromAssets(context: Context): List<Fikra> {
    try {
        val jsonFile = context.assets.open("fikralar.json")
        val reader = InputStreamReader(jsonFile)
        val gson = Gson()
        val fikralar = gson.fromJson(reader, Array<Fikra>::class.java).toList()
        return fikralar
    } catch (e: Exception) {
        Log.e("JSONError", "Error reading JSON file: ${e.message}")
        return emptyList()
    }
}
@Composable
fun readFikralarFromAssetsByCategory(navController: NavController, category: String) {
    val fikralarNonFiltered = readFikralarFromAssets(LocalContext.current)
    val fikralar=fikralarNonFiltered.filter { it.category==category }
    FikraListScreen(navController = navController, fikralar = fikralar)

}
fun readFikraFromAssetsWithId(fikralar: List<Fikra>, id: Int): Fikra? {
    return fikralar.find { it.id == id }
}

