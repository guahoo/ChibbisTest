package com.testTask.chibbistest.ui.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testTask.chibbistest.data.models.ReviewModel
import com.testTask.chibbistest.data.network.AppResult
import com.testTask.chibbistest.data.repos.ReviewsRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ReviewViewModel : ViewModel(), KoinComponent {

    private val reviewsRepository: ReviewsRepository = get()
    val listReviews = MutableLiveData<List<ReviewModel>>()
    val needShowErrorScreen = MutableLiveData<Boolean>(false)
    val isRefreshing = MutableLiveData<Boolean>()



 fun loadListReviews() {
        viewModelScope.launch {
            try {


                when (val reviewResp = reviewsRepository.getAllReviewsFromApi()) {

                    is AppResult.Success -> {
                        listReviews.postValue(reviewResp.successData!!)
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

