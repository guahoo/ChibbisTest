package com.testTask.chibbistest.data.repos

import com.testTask.chibbistest.data.models.ReviewModel
import com.testTask.chibbistest.data.network.AppApi
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.network.handleApiError
import com.testTask.chibbistest.data.network.handleSuccess
import kotlinx.coroutines.coroutineScope

class ReviewsRepositoryImpl(private val appApi: AppApi): ReviewsRepository {
    override suspend fun getAllReviewsFromApi(): AppResult<List<ReviewModel>> = coroutineScope {
        return@coroutineScope try {
            val restResponse = appApi.getAllReviews()

            if (restResponse.isSuccessful){
                handleSuccess(restResponse)
            } else {
                handleApiError(restResponse)
            }

        } catch (e: Exception){
            AppResult.Error(e)
        }
    }
}