package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface MainContract {

  interface MainView : BaseView {
    fun showLandingScreen()
    fun getCurrentScreenTag(): String
    fun showPreviousScreen()
    fun showExitConfirmation()
    fun startBackPressedFlagResetTimer()
    fun exitApp()
  }

  interface MainPresenter : BasePresenter<MainView> {
    fun decorateView()
    fun handleBackPress()
    fun notifyTimerExpired()
  }
}