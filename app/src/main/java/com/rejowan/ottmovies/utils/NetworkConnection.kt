package com.rejowan.ottmovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import com.rejowan.ottmovies.ui.activity.BaseActivity

class NetworkConnection(private val context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    var isConnected: Boolean = false


    fun registerNetworkCallback() {
        val builder = NetworkRequest.Builder()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                notifyNetworkStatusChanged(isConnected = true)
                isConnected = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                notifyNetworkStatusChanged(isConnected = false)
                isConnected = false
            }
        }
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback!!)
    }

    private fun notifyNetworkStatusChanged(isConnected: Boolean) {
        (context as? BaseActivity)?.runOnUiThread {
            (context as? BaseActivity)?.isNetworkAvailable(isConnected)
        }
    }

    fun unregisterNetworkCallback() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
            networkCallback = null
        }
    }
}
