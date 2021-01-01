package org.dlo.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.dlo.myapplication.data.GithubUser
import org.dlo.myapplication.data.network.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (
        val usersData: MutableLiveData<GithubUser>,
        val error: MutableLiveData<String>
) {

    private val TAG: String = UserRepository::class.java.simpleName

    fun searchUsersService(query: String = "") {

        Service.api.getUser(query).enqueue(object : Callback<GithubUser?> {
            override fun onFailure(call: Call<GithubUser?>?, t: Throwable?) {
                error.postValue("Error happened")
            }
            override fun onResponse(call: Call<GithubUser?>?, response: Response<GithubUser?>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        usersData.postValue(response.body())

                    } else {
                        error.postValue(response.errorBody()!!.string())
                    }
                }
            }
        })
    }


    fun searchUsersDeferredService(query: String){
        launch {
            try {
                val response = Service.api.getDefer(query).await()
                withContext(UI) {
                    if (response.isSuccessful) {
                        usersData.value = response.body()
                    } else {
                        Log.e("Exception", "${response.errorBody()}")
                        error.value = response.errorBody()?.string()
                    }
                }
            }
            catch (e: Exception) {
                withContext(UI) {
                    e.message?.let { Log.e("Exception", it) }
                    error.value = e.message
                    error.postValue(e.message)
                }
            }
        }
    }


}