package com.bennohan.myfriends.ui.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.databinding.ActivityLoginBinding
import com.bennohan.myfriends.ui.home.HomeActivity
import com.bennohan.myfriends.ui.register.RegisterActivity
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        binding.btnLogin.setOnClickListener {
            if (binding.etPhone.isEmptyRequired(R.string.mustFill) || binding.etPassword.isEmptyRequired(R.string.mustFill)
            ) {
                return@setOnClickListener
            }
            val phone = binding.etPhone.textOf()
            val password = binding.etPassword.textOf()

            viewModel.login(phone , password)
        }
        
        binding.btnRegister.setOnClickListener {
            openActivity<RegisterActivity>()
        }



    }

    private fun observe (){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("login")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "login Success")
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            ApiStatus.ERROR -> {
                                loadingDialog.dismiss()
                                tos(it.message ?: "login Failed")

                            }
                            else -> loadingDialog.setResponse("Else")
                        }
                    }
                }
            }
        }
    }

}