package com.example.restretrofit

import retrofit2.Call
import retrofit2.http.*


interface TacheService {
    
    @GET("posts")
    fun getAllTeches() : Call<List<Tache>>

    @GET("posts/{id}")
    fun getTacheById(@Path("id") id: String): Call<Tache>

    @POST("/posts")
    @FormUrlEncoded
    fun addTache(
        @Field("id") id: Int,
        @Field("title") title: String?,
        @Field("body") body: String?,
        @Field("userId") userId: Int
    ): Call<Tache>
}