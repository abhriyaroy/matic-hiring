package com.example.maticnetwork.presenter.landing

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface LandingContract {

  interface LandingView : BaseView {
    fun redirectToNewAccount()
    fun redirectToSignIn()
  }

  interface LandingPresenter : BasePresenter<LandingView> {
    fun handleNewAccountClick()
    fun handleSignInClick()
  }
}