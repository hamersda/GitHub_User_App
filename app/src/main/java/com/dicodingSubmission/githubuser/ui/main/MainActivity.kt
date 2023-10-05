package com.dicodingSubmission.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingSubmission.githubuser.data.model.User
import com.dicodingSubmission.githubuser.databinding.ActivityMainBinding
import com.dicodingSubmission.githubuser.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply{
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    viewModel.setSearchUsers(searchBar.text.toString())
                    false
                }
        }

        //Move to Detail User Activity
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        viewModel.getSearchUsers().observe(this, {
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}