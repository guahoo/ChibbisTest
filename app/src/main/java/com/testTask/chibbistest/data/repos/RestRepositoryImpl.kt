package com.testTask.chibbistest.data.repos

import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.network.AppApi
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.network.handleApiError
import com.testTask.chibbistest.data.network.handleSuccess
import kotlinx.coroutines.coroutineScope

class RestRepositoryImpl(private val appApi: AppApi): RestRepository {
    override suspend fun getRestaurantListFromApi(): AppResult<List<RestModel>> = coroutineScope {
        return@coroutineScope try {
            val restResponse = appApi.getAllRestaurantsFromServer()

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