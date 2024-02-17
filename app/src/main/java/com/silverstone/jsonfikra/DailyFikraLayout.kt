package com.silverstone.jsonfikra

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.silverstone.jsonfikra.ReadAsset.readFikralarFromAssets

@Composable
fun DailyFikraLayout(){
    val sharedFikra= (LocalContext.current.getSharedPreferences("gunlukFikra", Context.MODE_PRIVATE))
        .getInt("dailyFikra",0)
    val fikra = readFikralarFromAssets(LocalContext.current).find { it.id==sharedFikra }

    LazyColumn(modifier = Modifier

        .height(400.dp)
        .clip(RoundedCornerShape(40.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFF5E1 ), Color(0xFFF0F0F0).copy(0.3f)),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )){

        item { Text(text = fikra!!.title,
        style = dailyScreenTitleTextStyle,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()) }
        item{ Text(text = fikra!!.content,
            style= longTextTextStyle,
            modifier = Modifier.padding(bottom = 100.dp, start = 15.dp, end = 15.dp))
        }
    }

}

@Composable
fun FikraDetailsLayout(fikra:String){
    LazyColumn(modifier = Modifier
        .padding(1.dp,1.dp)
        .height(450.dp)
        .clip(RoundedCornerShape(40.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFF5E1 ), Color(0xFFF0F0F0).copy(0.3f)),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )){

        item{ Text(text = fikra,
            style= longTextTextStyle,
            modifier = Modifier.padding(top = 15.dp, bottom = 200.dp, start = 5.dp, end = 5.dp))
        }
}}

