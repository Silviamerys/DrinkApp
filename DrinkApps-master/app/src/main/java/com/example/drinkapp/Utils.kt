package com.example.drinkapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class Utils {

    companion object {
        fun isOnline(context: Context): Boolean {

            val hasInternet: Boolean

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                hasInternet = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                hasInternet = try {
                    if (connectivityManager.activeNetworkInfo == null) {
                        false
                    } else {
                        connectivityManager.activeNetworkInfo?.isConnected!!
                    }
                } catch (e: Exception) {
                    false
                }
            }
            return hasInternet}

    }
}