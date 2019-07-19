package com.example.maticnetwork.view.landing

import com.example.maticnetwork.presenter.landing.LandingContract
import com.example.maticnetwork.presenter.landing.LandingContract.LandingPresenter
import com.example.maticnetwork.presenter.landing.LandingPresenterImpl
import dagger.Module
import dagger.Provides

@Module
interface LandingModule {

  @Provides
  fun providesLandingPresenter(): LandingPresenter = LandingPresenterImpl()
}