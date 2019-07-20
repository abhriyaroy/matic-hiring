package com.example.maticnetwork.view.landing

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.presenter.landing.LandingContract.LandingPresenter
import com.example.maticnetwork.presenter.landing.LandingPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class LandingModule {

  @PerFragment
  @Provides
  fun providesLandingPresenter(): LandingPresenter = LandingPresenterImpl()
}