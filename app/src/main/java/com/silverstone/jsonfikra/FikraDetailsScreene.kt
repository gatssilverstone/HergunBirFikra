package com.silverstone.jsonfikra
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.silverstone.jsonfikra.ReadAsset.readFikraFromAssetsWithId
import com.silverstone.jsonfikra.ReadAsset.readFikralarFromAssets



@Composable
fun FikraDetailsScreen(navController: NavController, fikraId: Int) {

    val context = LocalContext.current
    val fikraNonFiltered = readFikralarFromAssets(LocalContext.current)
    val fikra = readFikraFromAssetsWithId(fikraNonFiltered, fikraId)
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val favoriFikraIds = sharedPreferences.getStringSet("favFikraList", emptySet()) ?: emptySet()
    val (currentFikraId, setCurrentFikraId) = remember { mutableStateOf(fikraId) }

    val isFavori = fikra != null && fikra.id.toString() in favoriFikraIds

    val (favoriButtonText, setFavoriButtonText) = remember { mutableStateOf(if (isFavori) "Favorilerden Çıkar" else "Favorilere Ekle") }
    val (favoriButtonColor, setFavoriButtonColor) = remember { mutableStateOf(if (isFavori) Color.Red else Color.White) }


  Column(

        modifier = Modifier

            .fillMaxSize()
            .padding(6.dp, 27.dp, 6.dp, 6.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    val horizontalPan = pan.x
                    val esik = 30.dp.toPx()
                    if (horizontalPan > 0) {
                        backFikra(
                            currentFikraId,
                            navController,
                            context
                        )
                    } else if (horizontalPan < -esik) {
                        nextFikra(currentFikraId, navController,context)
                    }

                }
            }
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
              text = "${fikra?.category} Fıkraları",
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp,
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier
                  .padding(bottom = 8.dp)
                  .clickable { navController.navigate("FikraListScreen/${fikra?.category}") }
          )
          IconButton(onClick = {                 if (fikra != null) {
              if (isFavori) {

                  removeFromFavoriteFikralar(context, fikra.id)
                  setFavoriButtonText("Favorilere Ekle")
                  setFavoriButtonColor(Color.White)
              } else {

                  addToFavoriteFikralar(context, fikra.id)
                  setFavoriButtonText("Favorilerden Çıkar")
                  setFavoriButtonColor(Color.Red)
              }
          }},
              modifier = Modifier.size(50.dp)
          ) {
              Icon(imageVector = ImageVector.vectorResource(id = R . drawable . heart) , "",
                  tint = favoriButtonColor, modifier = Modifier
                      .size(50.dp))
          }
      }

        Text(

            style = detailsScreenTitleTextStyle,
            text = fikra?.title ?: "",
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(CenterHorizontally)
        )
        FikraDetailsLayout(fikra =fikra!!.content )
      Row(modifier = Modifier
          .fillMaxWidth()
          .padding(13.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically) {
          NextAndBackButton( backgroundImageResource = R.drawable.back) {
              backFikra(currentFikraId,navController,context)
          }

          RandomFikraButton(navController = navController, size = 100)

          NextAndBackButton( backgroundImageResource = R.drawable.next) {

              nextFikra(currentFikraId, navController = navController,context)
          }

      }
      GoogleAd(adId = "ca-app-pub-3940256099942544/6300978111")


  }
}

fun nextFikra(id:Int,navController: NavController,context: Context){
    showMob(context)
    var newFikraId= id
    if (id in 1..2291) newFikraId+=1
    else newFikraId=1

    navController.navigate("FikraDetailsScreen/$newFikraId")
}


fun backFikra(id:Int, navController: NavController,context: Context)
{
    showMob(context)
    var newFikraId= id
    if (id in 1..2291) newFikraId-=1
    else newFikraId=2291

    navController.navigate("FikraDetailsScreen/$newFikraId")

}

@Composable
fun NextAndBackButton( backgroundImageResource: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Cyan, Color.White)
                )
            )
            .border(
                width = 10.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.White, Color.Black),
                ),
                shape = CircleShape
            )
            .padding(3.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = backgroundImageResource),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}
