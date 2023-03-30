package com.bennohan.myfriends.ui.detail

import android.os.Bundle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.database.Cons
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.databinding.ActivityDetailBinding
import com.crocodic.core.extension.tos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {

    private var friends: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnFav.setOnClickListener {
            val userLike = friends?.id
            val like = friends?.likeByYou
            if (like == true) {
                tos("Friends Disliked")
//                binding.btnFav.setBackground(getDrawable(R.drawable.ic_baseline_favorite_24))

            } else {
                tos("Friends Liked")
            }
            viewModel.favourite(userLike ?: return@setOnClickListener)
            setResult(Cons.FRIENDS.RELOAD)
        }


    }

    //Receiving TourData
    private fun getData() {
        friends = intent.getParcelableExtra(Cons.FRIENDS.FRIENDS)
        binding.data = friends
    }
}