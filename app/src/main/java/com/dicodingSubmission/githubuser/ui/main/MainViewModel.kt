package com.dicodingSubmission.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingSubmission.githubuser.api.RetrofitClient
import com.dicodingSubmission.githubuser.data.model.User
import com.dicodingSubmission.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers:LiveData<List<User>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUsers(query: String){
        _isLoading.value = true
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        Log.d("MainViewModel", "Data response: ${response.body()?.items}")
                        _listUsers.value = response.body()?.items
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                    _isLoading.value = true
                }

            })
    }
    fun getSearchUsers(): LiveData<List<User>> {
        return listUsers
    }
}