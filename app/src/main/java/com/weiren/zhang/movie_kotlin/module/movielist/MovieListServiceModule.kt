package com.weiren.zhang.movie_kotlin.module.movielist
import com.weiren.zhang.movie_kotlin.api.MovieListApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieListServiceModule {

    @Singleton
    @Provides
    fun proMovieListService(retrofit: Retrofit): MovieListApi {
        return retrofit.create(MovieListApi::class.java)
    }
}