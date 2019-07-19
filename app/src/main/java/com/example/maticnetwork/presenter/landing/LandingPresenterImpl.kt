package com.example.maticnetwork.presenter.landing

import com.example.maticnetwork.presenter.landing.LandingContract.LandingPresenter
import com.example.maticnetwork.presenter.landing.LandingContract.LandingView

class LandingPresenterImpl : LandingPresenter {

  private var landingView: LandingView? = null

  override fun attachView(view: LandingView) {
    landingView = view
  }

  override fun detachView() {
    landingView = null
  }

  override fun handleNewAccountClick() {
    landingView?.redirectToNewAccount()
  }

  override fun handleSignInClick() {
    landingView?.redirectToSignIn()
  }

}