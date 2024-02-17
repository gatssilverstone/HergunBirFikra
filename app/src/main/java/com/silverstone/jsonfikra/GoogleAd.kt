package com.silverstone.jsonfikra
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun GoogleAd(adId:String) {
    Column() {
        Spacer(modifier = Modifier.size(2.dp))
        AndroidView(modifier = Modifier.fillMaxWidth(), factory ={

            AdView(it).apply {
                setAdSize(AdSize.BANNER)
                adUnitId=adId
                loadAd(AdRequest.Builder().build())
            }
        }

        )
    }
}