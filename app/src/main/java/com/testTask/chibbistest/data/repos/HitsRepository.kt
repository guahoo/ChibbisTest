package com.testTask.chibbistest.data.repos

import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.network.AppResult

interface HitsRepository {
    suspend fun getHitsListFromApi(): AppResult<List<HitModel>>
}