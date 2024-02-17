package com.silverstone.jsonfikra

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val customFontFamily = FontFamily(
    Font(R.font.titleyazifontu )
)
val nunitoFont= FontFamily(Font(R.font.nunito))

val detailsScreenTitleTextStyle= TextStyle(
    fontFamily = customFontFamily,
    fontSize = 30.sp,
    color = Color.Blue
)
val listScreenTitleTextStyle= TextStyle(
    fontFamily = customFontFamily,
    fontSize = 25.sp,
    color = Color.Blue
)
val dailyScreenTitleTextStyle= TextStyle(
    fontFamily = customFontFamily,
    fontSize = 35.sp,
    color = Color.Blue
)
val longTextTextStyle = TextStyle(
    fontFamily = nunitoFont,
    fontSize = 22.sp,
    lineHeight = 40.sp, // Satır yüksekliği
    color = Color.Black,
    fontWeight = FontWeight.Bold,
    letterSpacing = 1.sp // Harf aralığı
)