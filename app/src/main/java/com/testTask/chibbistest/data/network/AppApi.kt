package com.testTask.chibbistest.data.network

import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.models.ReviewModel
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("api/v1/restaurants")
    suspend fun getAllRestaurantsFromServer(): Response<List<RestModel>>

    @GET("api/v1/hits")
    suspend fun getAllHitsFromServer(): Response<List<HitModel>>

    @GET("api/v1/reviews")
    suspend fun getAllReviews(): Response<List<ReviewModel>>

}