package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface MainContract {

  interface MainView : BaseView

  interface MainPresenter : BasePresenter<MainView>
}