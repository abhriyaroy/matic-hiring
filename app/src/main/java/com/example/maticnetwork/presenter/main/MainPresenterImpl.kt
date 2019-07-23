package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.main.MainContract.MainPresenter
import com.example.maticnetwork.presenter.main.MainContract.MainView
import com.example.maticnetwork.view.useraccount.USER_ACCOUNT_FRAGMENT_TAG

class MainPresenterImpl : MainPresenter {

  private var mainView: MainView? = null
  internal var isExitConfirmationShown = false

  override fun attachView(view: MainView) {
    mainView = view
  }

  override fun detachView() {
    mainView = null
  }

  override fun decorateView() {
    mainView?.showLandingScreen()
  }

  override fun handleBackPress() {
    if (!isExitConfirmationShown) {
      with(mainView?.getCurrentScreenTag()) {
        when (this) {
          USER_ACCOUNT_FRAGMENT_TAG -> mainView?.showPreviousScreen()
          else -> {
            mainView?.showExitConfirmation()
            isExitConfirmationShown = true
            mainView?.startBackPressedFlagResetTimer()
          }
        }
      }
    } else {
      mainView?.exitApp()
    }
  }

  override fun notifyTimerExpired() {
    isExitConfirmationShown = false
  }
}