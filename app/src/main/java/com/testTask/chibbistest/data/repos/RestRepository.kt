package com.testTask.chibbistest.data.repos

import android.content.Context
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.network.AppApi
import com.testTask.chibbistest.data.network.AppResult


interface RestRepository {
     suspend fun getRestaurantListFromApi(): AppResult<List<RestModel>>
}