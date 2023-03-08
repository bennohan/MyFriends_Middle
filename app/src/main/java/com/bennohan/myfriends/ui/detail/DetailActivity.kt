package com.bennohan.myfriends.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.database.Cons
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.databinding.ActivityDetailBinding
import com.crocodic.core.extension.tos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding,DetailViewModel>(R.layout.activity_detail) {

    private var friends: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()



        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnFav.setOnClickListener {
            tos("Friends Liked")
            val userLike = friends?.id
            viewModel.favourite(userLike ?: return@setOnClickListener)
        }

//        binding.btnUnFav.setOnClickListener {
//            tos("Friends Disliked")
//            val userLike = friends?.id
//            viewModel.favourite(userLike ?: return@setOnClickListener)
//        }

    }

    //Receiving TourData
    private fun getData() {
        friends = intent.getParcelableExtra(Cons.FRIENDS.FRIENDS)
        binding.data = friends
    }
}