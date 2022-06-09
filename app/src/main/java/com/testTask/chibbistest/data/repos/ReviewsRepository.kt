package com.testTask.chibbistest.data.repos

import com.testTask.chibbistest.data.models.ReviewModel
import com.testTask.chibbistest.data.network.AppResult

interface ReviewsRepository {
    suspend fun getAllReviewsFromApi(): AppResult<List<ReviewModel>>
}