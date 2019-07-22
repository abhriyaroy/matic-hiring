package com.example.maticnetwork.presenter.home

import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.view.MainScheduler
import com.uber.autodispose.autoDisposable

class HomePresenterImpl(
  private val userAccountsUseCase: UserAccountsUseCase,
  private val mainScheduler: MainScheduler
) : HomePresenter {
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
    userAccountsUseCase.getHash()
      .observeOn(mainScheduler.getMainScheduler())
      .autoDisposable(homeView!!.getScope())
      .subscribe({
        homeView?.showHashDialog(it)
      }, {
        homeView?.showGenericExceptionMessage()
      })
  }

}