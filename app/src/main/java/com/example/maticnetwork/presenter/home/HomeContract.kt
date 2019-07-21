package com.example.maticnetwork.presenter.home

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface HomeContract {

  interface HomeView : BaseView {
    fun showHashDialog(decryptedHash: String)
  }

  interface HomePresenter : BasePresenter<HomeView>{

  }
}