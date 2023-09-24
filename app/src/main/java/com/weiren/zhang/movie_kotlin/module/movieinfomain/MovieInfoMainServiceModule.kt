package com.weiren.zhang.movie_kotlin.module.movieinfomain
import com.weiren.zhang.movie_kotlin.api.MovieInfoMainApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieInfoMainServiceModule {

    @Singleton
    @Provides
    fun proMovieInfoMainService(retrofit: Retrofit): MovieInfoMainApi {
        return retrofit.create(MovieInfoMainApi::class.java)
    }
}