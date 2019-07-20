package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface MainContract {

  interface MainView : BaseView {
    fun showLandingScreen()
  }

  interface MainPresenter : BasePresenter<MainView> {
    fun decorateView()
  }
}