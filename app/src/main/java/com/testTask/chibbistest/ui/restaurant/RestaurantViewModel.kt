package com.testTask.chibbistest.ui.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testTask.chibbistest.data.models.RestModel
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.repos.RestRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RestaurantViewModel : ViewModel(), KoinComponent {

    private val restRepository: RestRepository = get()
    val listRestaurant = MutableLiveData<List<RestModel>>()
    val needShowErrorScreen = MutableLiveData(false)
    val isRefreshing = MutableLiveData<Boolean>()




    fun loadListRestaurant() {
        viewModelScope.launch {
            try {
                when (val restResponse = restRepository.getRestaurantListFromApi()) {

                    is AppResult.Success -> {
                        listRestaurant.postValue(restResponse.successData!!)
                        needShowErrorScreen.postValue(false)
                    }
                    is AppResult.Error -> {
                        /**
                         *  "Выводим сообщение об ошибке"
                         */
                        needShowErrorScreen.postValue(true)
                    }
                }
            } catch (e: Exception) {
                needShowErrorScreen.postValue(true)
            }
        }
    }
}

