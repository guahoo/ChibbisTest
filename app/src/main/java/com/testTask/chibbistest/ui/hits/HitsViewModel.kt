package com.testTask.chibbistest.ui.hits

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testTask.chibbistest.data.models.HitModel
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.repos.HitsRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HitsViewModel : ViewModel(), KoinComponent {

    private val hitsRepository: HitsRepository = get()
    val listHits = MutableLiveData<List<HitModel>>()
    val needShowErrorScreen = MutableLiveData<Boolean>(false)
    val isRefreshing = MutableLiveData<Boolean>()
    
     fun loadListHits() {
        viewModelScope.launch {
            try {
                when (val hitResponse = hitsRepository.getHitsListFromApi()) {

                    is AppResult.Success -> {
                        listHits.postValue(hitResponse.successData!!)
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

