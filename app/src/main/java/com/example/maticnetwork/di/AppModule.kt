package com.example.maticnetwork.di

import android.app.Application
import android.content.Context
import com.example.maticnetwork.data.*
import com.example.maticnetwork.di.scopes.PerApplication
import com.example.maticnetwork.domain.UserAccountsInteractor
import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.view.MainScheduler
import com.example.maticnetwork.view.MainSchedulerImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @PerApplication
  @Provides
  fun providesContext(application: Application): Context = application

  @PerApplication
  @Provides
  fun providesMainScheduler(): MainScheduler = MainSchedulerImpl()

  @PerApplication
  @Provides
  fun providesBackgroundScheduler(): BackgroundSchedulers = BackgroundSchedulersImpl()

  @PerApplication
  @Provides
  fun providesSharedPrefsHelper(context: Context): SharedPrefsHelper =
    SharedPrefsHelperImpl(context)

  @PerApplication
  @Provides
  fun providesKeyStoreHelper(
    context: Context,
    sharedPrefsHelper: SharedPrefsHelper
  ): KeyStoreHelper = KeyStoreHelperImpl(context, sharedPrefsHelper)

  @PerApplication
  @Provides
  fun providesRepository(
    keyStoreHelper: KeyStoreHelper,
    sharedPrefsHelper: SharedPrefsHelper,
    backgroundSchedulers: BackgroundSchedulers
  ): Repository = RepositoryImpl(keyStoreHelper, sharedPrefsHelper, backgroundSchedulers)

  @PerApplication
  @Provides
  fun providesUserAccountUseCase(repository: Repository): UserAccountsUseCase =
    UserAccountsInteractor(repository)
}