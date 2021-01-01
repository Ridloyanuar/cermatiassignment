package org.dlo.myapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.dlo.myapplication.data.GithubUser
import org.dlo.myapplication.repository.UserRepository


class HomeViewModel: ViewModel(){

    private val TAG: String = HomeViewModel::class.java.simpleName
    var userResponse: UserRepository

    val usersData: MutableLiveData<GithubUser> by lazy { MutableLiveData<GithubUser>() }
    val error : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    init {
        userResponse = UserRepository(usersData, error)
    }

    fun fetchUsers(query: String = "") {
        //old retrofit call
//        userResponse.searchUsersService(query)
        userResponse.searchUsersDeferredService(query)

    }



}