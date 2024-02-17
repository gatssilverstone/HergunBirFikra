package com.silverstone.jsonfikra


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun FikraListScreen(navController: NavController, fikralar: List<Fikra>) {

    val titleCategory= fikralar[0].category



    val itemsPerPage = 10
    val pageCount = (fikralar.size + itemsPerPage - 1) / itemsPerPage
    val currentPage = remember { mutableStateOf(0) }
    Column() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(680.dp)
            .padding(top= 26.dp,bottom = 5.dp)
    ) {


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigate("StartScreen",
                builder = {popUpTo("StartScreen"){inclusive=true} }
            ) },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(imageVector = ImageVector.vectorResource(id = R . drawable . home) , "",
                    tint = Color.Cyan, modifier = Modifier
                        .size(50.dp))
            }
            Text(
                text =     if (areAllFikrasInSameCategory(fikralar)) {
                    "$titleCategory Fıkraları"
                } else {
                    "Favori Fıkralar" }

                ,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

        }
        LazyColumn {
            itemsIndexed(fikralar) { index, item ->
                val pageIndex = index / itemsPerPage

                if (pageIndex == currentPage.value) {
                    ListCard(fikra = item) {
                        navController.navigate("FikraDetailsScreen/${it.id}")
                    }
                }
            }
        }
    }
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            PagerIndicator(
                currentPage = currentPage.value,
                pageCount = pageCount,
                onPageSelected = { newPage ->
                    currentPage.value = newPage
                },
                onNextPage = {
                    currentPage.value++
                },
                onPreviousPage = {
                    currentPage.value--
                }
            )
        }
}}



@Composable
fun ListCard(fikra: Fikra, onItemClick: (Fikra) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .clickable { onItemClick(fikra) },
        shape = RoundedCornerShape(16.dp),

        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = Color.Blue,
                text = fikra.title,
                style = listScreenTitleTextStyle

            )
        }
    }
}
fun areAllFikrasInSameCategory(fikralar: List<Fikra>): Boolean {
    if (fikralar.isEmpty()) {

        return false
    }

    val firstCategory = fikralar[0].category

    for (fikra in fikralar) {
        if (fikra.category != firstCategory) {
            return false
        }
    }

    return true
}
