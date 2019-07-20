package com.example.maticnetwork.di

import android.app.Application
import android.content.Context
import com.example.maticnetwork.data.*
import com.example.maticnetwork.di.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @PerApplication
  @Provides
  fun providesContext(application: Application): Context = application

  @PerApplication
  @Provides
  fun providesKeyStoreHelper(): KeyStoreHelper = KeyStoreHelperImpl()

  @PerApplication
  @Provides
  fun providesSharedPrefsHelper(): SharedPrefsHelper = SharedPrefsHelperImpl()

  @PerApplication
  @Provides
  fun providesRepository(
    keyStoreHelper: KeyStoreHelper,
    sharedPrefsHelper: SharedPrefsHelper
  ): Repository = RepositoryImpl(keyStoreHelper, sharedPrefsHelper)
}