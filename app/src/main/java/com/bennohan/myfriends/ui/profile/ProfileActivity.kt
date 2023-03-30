package com.bennohan.myfriends.ui.profile

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.database.UserDao
import com.bennohan.myfriends.databinding.ActivityProfileBinding
import com.bennohan.myfriends.ui.editProfile.EditActivity
import com.crocodic.core.extension.openActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfileBinding, ProfileViewModel>(R.layout.activity_profile) {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUser()

        binding.btnEdit.setOnClickListener {
            openActivity<EditActivity>()
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getUser()
                }
            }
        }


    }

    private fun getUser() {
        userDao.getUser().observe(this) {
            binding.user = it
        }
    }
}