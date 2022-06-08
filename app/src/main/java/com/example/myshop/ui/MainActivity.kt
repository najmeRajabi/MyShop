package com.example.myshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.myshop.R
import com.example.myshop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        supportActionBar?.hide()
        val imvSplash = binding.imvSplashScreen
        val fragment = binding.fragmentContainerView
//        if (vModelList.splashFlag) {
            imvSplash.apply {
                alpha = 0f
                animate().setDuration(3000).alpha(1f)
                    .withEndAction {
                        fragment.visibility = View.VISIBLE
                        imvSplash.visibility = View.GONE
                        overridePendingTransition(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                        )
                    }
            }
//            vModelList.splashFlag = false
//        }else{
//            fragment.visibility = View.VISIBLE
//            imvSplash.visibility = View.GONE
//        }
    }
}