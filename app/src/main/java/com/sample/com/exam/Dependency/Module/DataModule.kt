package com.sample.com.exam.Dependency.Module

import com.sample.com.exam.Model.Repository.Local.ProductsData
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Singleton
    @Provides
    fun provideProductsData(realm: Realm): ProductsData {
        return ProductsData(realm)
    }

}