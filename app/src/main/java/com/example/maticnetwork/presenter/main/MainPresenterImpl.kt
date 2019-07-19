package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.main.MainContract.MainPresenter
import com.example.maticnetwork.presenter.main.MainContract.MainView

class MainPresenterImpl : MainPresenter {

  private var mainView: MainView? = null

  override fun attachView(view: MainView) {
    mainView = view
  }

  override fun detachView() {
    mainView = null
  }

}