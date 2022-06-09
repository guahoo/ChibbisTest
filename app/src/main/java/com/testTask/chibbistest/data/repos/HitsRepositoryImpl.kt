package com.testTask.chibbistest.data.repos

import android.content.Context
import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.network.AppApi
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.network.handleApiError
import com.testTask.chibbistest.data.network.handleSuccess
import kotlinx.coroutines.coroutineScope

class HitsRepositoryImpl(private val appApi: AppApi): HitsRepository {
    override suspend fun getHitsListFromApi(): AppResult<List<HitModel>> = coroutineScope {
        return@coroutineScope try {
            val restResponse = appApi.getAllHitsFromServer()

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