package com.bennohan.myfriends.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.databinding.ActivityMainBinding
import com.bennohan.myfriends.ui.home.HomeActivity
import com.bennohan.myfriends.ui.login.LoginActivity
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding , MainViewModel>(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getUser { isLogin ->
                if (isLogin == null) {
                    openActivity<LoginActivity>()
                    finish()
                } else {
                    openActivity<HomeActivity>()
                    finish()
                }
                finish()
            }

        }, 4000)

    }
}