package org.dlo.myapplication.data.network

import kotlinx.coroutines.experimental.Deferred
import org.dlo.myapplication.data.GithubUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Accept: application/json")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubUser>

    @GET("search/users")
    @Headers("Accept: application/json")
    fun getDefer(
        @Query("q") query: String
    ): Deferred<Response<GithubUser>>
}