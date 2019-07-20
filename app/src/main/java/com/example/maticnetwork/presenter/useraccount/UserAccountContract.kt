package com.example.maticnetwork.presenter.useraccount

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView
import com.example.maticnetwork.view.useraccount.AccountType

interface UserAccountContract {

  interface UserAccountView : BaseView {
    fun showNewAccountButton()
    fun showExistingAccountButton()
  }

  interface UserAccountPresenter : BasePresenter<UserAccountView> {
    fun decorateView(accountType: AccountType)
  }
}