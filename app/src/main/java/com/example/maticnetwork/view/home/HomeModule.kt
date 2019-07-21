package com.example.maticnetwork.view.home

import com.example.maticnetwork.di.scopes.PerActivity
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

  @PerActivity
  @Provides
  fun providesHomePresenter(): HomePresenter = HomePresenterImpl()
}