package com.vgroup.vgroupstore.di




import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StorefrontClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdminClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StorefrontRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AdminRetrofit
