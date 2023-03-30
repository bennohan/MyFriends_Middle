package com.bennohan.myfriends.ui.home

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bennohan.myfriends.R
import com.bennohan.myfriends.base.BaseActivity
import com.bennohan.myfriends.database.Cons
import com.bennohan.myfriends.database.User
import com.bennohan.myfriends.database.UserDao
import com.bennohan.myfriends.databinding.ActivityHomeBinding
import com.bennohan.myfriends.databinding.ItemFriendsBinding
import com.bennohan.myfriends.ui.detail.DetailActivity
import com.bennohan.myfriends.ui.profile.ProfileActivity
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home),
    SearchView.OnQueryTextListener {

    @Inject
    lateinit var userDao: UserDao
    private var friends = ArrayList<User?>()
    private var friendsAll = ArrayList<User?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        adapter()
        getFriends()
        search()

        registerForContextMenu(binding.btnMenu)

        binding.SwipeLayout.setOnRefreshListener {
            observe()
            adapter()
            getFriends()
            search()
        }

        binding.btnMenu.setOnClickListener {
            openActivity<ProfileActivity>()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getFriends()
                    observe()
                    adapter()
                }
            }
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_item, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnProfile -> openActivity<ProfileActivity>()
        }
        return super.onContextItemSelected(item)
    }

    private fun getFriends() {
        userDao.getUser().observe(this) {
            viewModel.getfriendsList(it.id)
            binding.user = it
        }
    }


    private fun adapter() {
        binding.rvFriends.adapter = CoreListAdapter<ItemFriendsBinding, User>(R.layout.item_friends)
            .initItem(friends) { position, data ->
                openActivity<DetailActivity> {
                    putExtra(Cons.FRIENDS.FRIENDS, data)
                }
            }
    }

    private fun observe() {
        viewModel.friends.observe(this) {
            binding.SwipeLayout.isRefreshing = false
            friends.clear()
            friendsAll.clear()

            friends.addAll(it)
            friendsAll.addAll(it)
            binding.rvFriends.adapter?.notifyDataSetChanged()
        }
    }

    private fun search() {
        binding.searchView.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()) {
                val filter = friendsAll.filter { it?.name?.contains("$text", true) == true }
                Log.d("CekFilter", "Keyword $text Data : $filter")
                friends.clear()
                filter.forEach {
                    friends.add(it)
                }
                binding?.rvFriends?.adapter?.notifyDataSetChanged()
                binding?.rvFriends?.adapter?.notifyItemInserted(0)

                if (filter.isEmpty()) {
                    binding.tvDataKosong.visibility = View.VISIBLE
                } else {
                    binding.tvDataKosong.visibility = View.GONE

                }

            } else {
                friends.clear()
                binding?.rvFriends?.adapter?.notifyDataSetChanged()
                friends.addAll(friendsAll)
                Log.d("ceknoteall", "noteall : $friendsAll")
                binding?.rvFriends?.adapter?.notifyItemInserted(0)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("Keyword", "$newText")
        return false
    }

}