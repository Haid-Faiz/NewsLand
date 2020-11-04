package com.example.newsapp.BroadCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class MyBroadcastReceiver : BroadcastReceiver() {

    var isConnected: Boolean? = null
    lateinit var connectivityListener: ConnectivityListener

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        connectivityListener = context as ConnectivityListener

        if (intent == null || intent.extras == null)
            return

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED)
            isConnected =
                networkInfo.type == ConnectivityManager.TYPE_MOBILE || networkInfo.type == ConnectivityManager.TYPE_WIFI
        else
            isConnected = false


        connectivityListener.checkConnection(isConnected!!)
    }

    interface ConnectivityListener {
        fun checkConnection(isConnected: Boolean)
    }
}