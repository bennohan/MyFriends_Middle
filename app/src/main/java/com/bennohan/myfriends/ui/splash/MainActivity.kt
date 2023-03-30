package com.bennohan.myfriends.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.databinding.ActivityMainBinding
import com.bennohan.myfriends.ui.home.HomeActivity
import com.bennohan.myfriends.ui.login.LoginActivity
import com.crocodic.core.extension.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.navigationBarColor = resources.getColor(R.color.mainColor)


        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getUser { isLogin ->
                if (isLogin) {
                    openActivity<HomeActivity>()
                    finish()
                } else {
                    openActivity<LoginActivity>()
                    finish()
                }

            }

        }, 4000)

    }


}