package com.example.maticnetwork.presenter.home

import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomeView

class HomePresenterImpl : HomePresenter {
  private var homeView: HomeView? = null

  override fun attachView(view: HomeView) {
    homeView = view
  }

  override fun detachView() {
    homeView = null
  }

  override fun decorateView() {

  }

  override fun notifyHashButtonClick() {
    homeView?.showHashDialog("asdbasjbdjk")
  }

}