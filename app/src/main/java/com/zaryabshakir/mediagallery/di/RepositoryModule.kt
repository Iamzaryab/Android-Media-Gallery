package com.zaryabshakir.mediagallery.di

import android.content.Context
import com.zaryabshakir.mediagallery.data.repository.MediaRepository
import com.zaryabshakir.mediagallery.data.repository.MediaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMediaRepository(@ApplicationContext app: Context): MediaRepository {
        return MediaRepositoryImpl(app)
    }

}
