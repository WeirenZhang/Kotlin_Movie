package com.weiren.zhang.movie_kotlin.module.theaterlist
import com.weiren.zhang.movie_kotlin.api.TheaterListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TheaterListServiceModule {

    @Singleton
    @Provides
    fun proTheaterListService(retrofit: Retrofit): TheaterListApi {
        return retrofit.create(TheaterListApi::class.java)
    }
}