package com.example.maticnetwork.presenter.useraccount

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView
import com.example.maticnetwork.view.useraccount.AccountType

interface UserAccountContract {

  interface UserAccountView : BaseView {
    fun showNewAccountButton()
    fun showExistingAccountButton()
    fun showWaitLoader()
    fun hideWaitLoader()
    fun showUsernameRequiredMessage()
    fun showPasswordRequiredMessage()
    fun redirectToHomeScreen()
    fun showUserNotAuthorizedException()
    fun showUserAlreadyPresentException()
    fun showGenericException()
    fun showPreviousScreen()
  }

  interface UserAccountPresenter : BasePresenter<UserAccountView> {
    fun decorateView(accountType: AccountType)
    fun handleSubmitClick(userName : String, password : String)
    fun handleBackButtonClick()
  }
}