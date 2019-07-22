package com.example.maticnetwork.view.home

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterPresenterImpl
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

  @PerFragment
  @Provides
  fun providesHomePresenter(): HomePresenter = HomePresenterImpl()

  @PerFragment
  @Provides
  fun providesRecyclerPresenter(): RecyclerAdapterPresenter = RecyclerAdapterPresenterImpl()

}