package com.silverstone.jsonfikra

import android.annotation.SuppressLint
import android.os.Bundle
import android.content.Context
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.silverstone.jsonfikra.ReadAsset.readFikralarFromAssets
import com.silverstone.jsonfikra.ReadAsset.readFikralarFromAssetsByCategory
import com.silverstone.jsonfikra.ui.theme.JsonFikraTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {


    private var mInterstitialAd: InterstitialAd? = null
    private var adInterstitialId:String="ca-app-pub-3940256099942544/1033173712"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             setDailyFikraWithWorkManager(this)
        MobileAds.initialize(this)


        setContent {
            JsonFikraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val adStatus = remember{ mutableStateOf(false) }
                    Background()

                    FikraApp(adStatus.value)
                }
            }
        }
    }
    fun  loadInterstitialAd(adStatus:(Boolean)->Unit){
        var adRequest=AdRequest.Builder().build()
        InterstitialAd.load(this,adInterstitialId, adRequest, object:InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                mInterstitialAd=null
                Log.i("AD_TAG","onAdFailedToLoad: ${error.message}")
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                mInterstitialAd=interstitialAd
                adStatus(true)
                Log.i("AD_TAG","onAdFailedToLoad: ")

            }
        } )
    }

    fun showInterstitialAd(){
        mInterstitialAd?.let {
            it.fullScreenContentCallback=object :FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    Log.i("AD_TAG","onAdDismissedFullScreenContent: ")
                    mInterstitialAd=null
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.i("AD_TAG","onAdImpression: ")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }
            }
            it?.show(this)
        }?:kotlin.run {
            Toast.makeText(this,"Ad is null", Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
fun StartScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp, 27.dp, 6.dp, 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CategoryRows(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.gununfikrasi),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(40.dp, 0.dp)
        )

        DailyFikraLayout()
        Spacer(modifier = Modifier.height(6.dp))
        RandomFikraButton(navController,100)
        GoogleAd(adId = "ca-app-pub-3940256099942544/6300978111")
    }
}






@Composable
fun FikraApp(adStatus: Boolean) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "StartScreen") {
        composable("StartScreen") {
            StartScreen(navController = navController)
        }

        composable("FikraListScreen/{selectedCategory}", arguments = listOf(
            navArgument("selectedCategory") { type = NavType.StringType },
        )) { backStackEntry ->
            val selectedCategory =
                backStackEntry.arguments?.getString("selectedCategory") ?: ""

            readFikralarFromAssetsByCategory(
                navController = navController,
                category = selectedCategory
            )
        }

        composable("FikraListScreen/favFikraList") {
            filterFavoriFikralar(navController = navController)
        }


        composable("FikraDetailsScreen/{id}", arguments = listOf(
            navArgument("id") { type = NavType.IntType }
        )) {
            val id = it.arguments?.getInt("id")
            FikraDetailsScreen(navController = navController, id!!)
        }

    }
}

@SuppressLint("MutatingSharedPrefs")
fun removeFromFavoriteFikralar(context: Context, fikraId: Int) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val favoriFikraIds = sharedPreferences.getStringSet("favFikraList", mutableSetOf()) ?: mutableSetOf()

    favoriFikraIds.remove(fikraId.toString())

    val editor = sharedPreferences.edit()
    editor.putStringSet("favFikraList", favoriFikraIds)
    editor.apply()
}

fun addToFavoriteFikralar(context: Context, fikraId: Int) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val favoriteFikralar = sharedPreferences.getStringSet("favFikraList", mutableSetOf())?.toMutableSet()
        ?: mutableSetOf()

    favoriteFikralar.add(fikraId.toString())

    val editor = sharedPreferences.edit()
    editor.putStringSet("favFikraList", favoriteFikralar)
    editor.apply()
    favoriteFikralar
}

@Composable
fun filterFavoriFikralar(navController: NavController) {
    val sharedPreferences = LocalContext.current.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val favoriFikraIds = sharedPreferences.getStringSet("favFikraList", emptySet()) ?: emptySet()
    val fikralar = readFikralarFromAssets(LocalContext.current)
    val favoriFikralar = fikralar.filter { it.id.toString() in favoriFikraIds }
    FikraListScreen(navController = navController, fikralar = favoriFikralar)
}

@Composable
fun RandomFikraButton(navController: NavController, size: Int) {
    val context= LocalContext.current
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .border(
                width = 10.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.White, Color.Black),
                ),
                shape = CircleShape
            )
            .padding(3.dp)
            .clickable {
                showMob(context)
                val randomInt = Random.nextInt(1, 2292)
                navController.navigate("FikraDetailsScreen/$randomInt")

            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.buton),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}



