package com.vgroup.vgroupstore.di

import android.content.Context
import androidx.room.Room
import com.vgroup.vgroupstore.data.AppDatabase
import com.vgroup.vgroupstore.data.dao.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "vgroup_cart_db"
        ).build()

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}