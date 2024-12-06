package com.rejowan.ottmovies.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rejowan.ottmovies.utils.NetworkConnection


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkConnection = NetworkConnection(this)
        networkConnection.registerNetworkCallback()

    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable(networkConnection.isConnected)
    }


    override fun onDestroy() {
        super.onDestroy()
        networkConnection.unregisterNetworkCallback()
    }

    open fun isNetworkAvailable(isConnected: Boolean) {

    }


}