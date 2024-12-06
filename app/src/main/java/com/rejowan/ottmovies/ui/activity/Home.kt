package com.rejowan.ottmovies.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.adapter.FragmentAdapter
import com.rejowan.ottmovies.databinding.ActivityHomeBinding

class Home : BaseActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val fragmentAdapter by lazy { FragmentAdapter(supportFragmentManager, lifecycle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)


        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = fragmentAdapter
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }

                R.id.nav_list -> {
                    binding.viewPager.currentItem = 1
                    true
                }

                R.id.nav_search -> {
                    startActivity(Intent(this, Search::class.java))
                    false
                }

                else -> false
            }
        }


    }

    override fun isNetworkAvailable(isConnected: Boolean) {
        Log.e("SystemLog Home", "isNetworkAvailable: $isConnected")

    }


}