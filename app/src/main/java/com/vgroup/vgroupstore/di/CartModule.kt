package com.vgroup.vgroupstore.di


import android.content.Context
import com.google.gson.Gson
import com.vgroup.vgroupstore.presentation.cart.CartManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    @Singleton
    fun provideCartManager(
        @ApplicationContext context: Context,
        gson: Gson
    ): CartManager = CartManager(context, gson)

}
