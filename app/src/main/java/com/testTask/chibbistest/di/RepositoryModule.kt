package com.testTask.chibbistest.di
import com.testTask.chibbistest.data.network.AppApi
import com.testTask.chibbistest.data.repos.*
import org.koin.dsl.module

val repositoryModule = module {

    fun provideRestRepository(
        api: AppApi
    ): RestRepository {
        return RestRepositoryImpl(api)
    }

    single {
        provideRestRepository(get())
    }

    fun provideHitRepository(
        api: AppApi
    ): HitsRepository {
        return HitsRepositoryImpl(api)
    }

    single {
        provideHitRepository(get())
    }

    fun provideReviewsRepository(
        api: AppApi
    ): ReviewsRepository {
        return ReviewsRepositoryImpl(api)
    }

    single {
        provideReviewsRepository(get())
    }

}