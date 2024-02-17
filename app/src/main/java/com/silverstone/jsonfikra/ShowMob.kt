package com.silverstone.jsonfikra

import android.content.Context

    var counter=0

    fun incrementCounter():Int{
        counter++
        if (counter>7) counter=0
        return counter
    }

    fun showMob(context:Context){
        if (incrementCounter() ==7){
            (context as MainActivity).loadInterstitialAd {
                if (it){
                    (context as MainActivity).showInterstitialAd()
                }
            }}
    }


