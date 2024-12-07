package com.rejowan.ottmovies.ui.activity

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.adapter.FragmentAdapter
import com.rejowan.ottmovies.databinding.ActivityHomeBinding
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : BaseActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val fragmentAdapter by lazy { FragmentAdapter(supportFragmentManager, lifecycle) }

    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)


        setupFragmentPager()


    }

    private fun setupFragmentPager() {
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

                else -> false
            }
        }
    }

    override fun isNetworkAvailable(isConnected: Boolean) {
        if (isConnected) {
            movieViewModel.getBannerMovies()
            movieViewModel.getBatmanMovies()
            movieViewModel.getLatestMovies()

        }

    }


}