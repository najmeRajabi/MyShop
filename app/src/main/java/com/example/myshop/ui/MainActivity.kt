package com.example.myshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.myshop.R
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val vModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        supportActionBar?.hide()
        val imvSplash = binding.imvSplashScreen
        val fragment = binding.fragmentContainerView
        if (vModel.splashFlag) {
            imvSplash.apply {
                alpha = 0f
                animate().setDuration(3000).alpha(1f)
                    .withEndAction {
                        fragment.visibility = View.VISIBLE
                        binding.bottomNavHome.visibility = View.VISIBLE
                        imvSplash.visibility = View.GONE
                        overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    }
            }
            vModel.splashFlag = false
        }else{
            fragment.visibility = View.VISIBLE
            binding.bottomNavHome.visibility = View.VISIBLE
            imvSplash.visibility = View.GONE
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        setupWithNavController(binding.bottomNavHome, navHostFragment!!.navController)
    }
}