package com.bennohan.myfriends.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bennohan.myfriends.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ViewBindingHelper {
    companion object {
        @JvmStatic
        @BindingAdapter(value = ["imageUrl"], requireAll = false)
        fun loadImageRecipe(view: ImageView, imageUrl: String?) {

            view.setImageDrawable(null)

            imageUrl?.let {
                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_person_24)
//                    .apply(RequestOptions.circleCropTransform())
//                    .error(R.drawable.error)
                    .into(view)

            }

        }

        @JvmStatic
        @BindingAdapter(value = ["imageUrlCircle"], requireAll = false)
        fun loadImageRecipeCircle(view: ImageView, imageUrl: String?) {

            view.setImageDrawable(null)

            imageUrl?.let {
                Glide
                    .with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .apply(RequestOptions.circleCropTransform())
                    .into(view)


            }

        }

    }
}