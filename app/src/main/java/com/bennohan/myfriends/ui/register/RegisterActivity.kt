package com.bennohan.myfriends.ui.register

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.databinding.ActivityRegisterBinding
import com.bennohan.myfriends.ui.home.HomeActivity
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding , RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.etName.isEmptyRequired(R.string.mustFill)||binding.etPhone.isEmptyRequired(R.string.mustFill) || binding.etPassword.isEmptyRequired(R.string.mustFill)
            ) {
                return@setOnClickListener
            }
            tos("button work")
            val name = binding.etName.textOf()
            val phone = binding.etPhone.textOf()
            val password = binding.etPassword.textOf()

            viewModel.register(name , phone,password)
        }

    }

    private fun observe(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Register in")
                            ApiStatus.SUCCESS -> {
                                tos(it.message ?: "Berhasil Register")
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            ApiStatus.ERROR -> {
                                loadingDialog.dismiss()
                                tos(it.message ?: "Register Failed")

                            }
                            else -> loadingDialog.setResponse("Else")
                        }
                    }
                }
            }
        }

    }
}