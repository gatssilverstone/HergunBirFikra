package com.silverstone.jsonfikra

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CircularCategoryButton(
    category: Categories,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate("FikraListScreen/${category.name}")
            }
            .width(100.dp)
            .padding(0.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.iconResource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .padding(0.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = category.name,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun favButton(navController: NavController){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate("FikraListScreen/favFikraList")
            }
            .width(100.dp)
            .padding(0.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.kalp),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .padding(0.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "Favoriler",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun CategoryRows(navController: NavController){

    val categories = listOf(
        Categories("Temel", R.drawable.temel),
        Categories("Deli", R.drawable.deli),
        Categories("Asker", R.drawable.asker),
        Categories("Doktor", R.drawable.doktor),
        Categories("Hayvan", R.drawable.hayvan),
        Categories("Karı - Koca", R.drawable.kocakari),
        Categories("Kısa", R.drawable.kisa),
        Categories("Köylü", R.drawable.koylu),
        Categories("Matematik", R.drawable.matematik),
        Categories("Meslek", R.drawable.meslek),
        Categories("Okul", R.drawable.okul),
        Categories("Politika", R.drawable.politika),
        Categories("Sarhoş", R.drawable.sarhos),
        Categories("Spor", R.drawable.spor),
        Categories("Karadeniz", R.drawable.karadeniz),
        Categories("Şoför", R.drawable.sofor),
        Categories("Tarih", R.drawable.tarih),
        Categories("Ünlü", R.drawable.unlu)
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0x5B65BFDA), Color(0xFFFCF9F9)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        item(){
            favButton(navController = navController)
        }
        item { Nasrettin(navController = navController) }

        items(categories) { category ->
            CircularCategoryButton(category = category, navController = navController)

        }
    }
}
@Composable
fun Nasrettin(navController: NavController){
    val category=Categories("Nasrettin Hoca", R.drawable.nasrettinhoca)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate("FikraListScreen/${category.name}")
            }
            .width(100.dp)
            .padding(0.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Cyan)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nasrettinhoca),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Cyan)
                    .padding(0.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "N. Hoca",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}