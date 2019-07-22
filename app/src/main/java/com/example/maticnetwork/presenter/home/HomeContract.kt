package com.example.maticnetwork.presenter.home

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface HomeContract {

  interface HomeView : BaseView {
    fun showHashDialog(hash: String)
    fun showGenericExceptionMessage()
  }

  interface HomePresenter : BasePresenter<HomeView>{
    fun decorateView()
    fun notifyHashButtonClick()
  }
}