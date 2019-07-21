package com.example.maticnetwork.presenter.useraccount

import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountPresenter
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountView
import com.example.maticnetwork.view.MainScheduler
import com.example.maticnetwork.view.useraccount.AccountType
import com.example.maticnetwork.view.useraccount.AccountType.EXISTING_USER
import com.example.maticnetwork.view.useraccount.AccountType.NEW_USER
import com.uber.autodispose.autoDisposable

class UserAccountPresenterImpl(
  private val userAccountsUseCase: UserAccountsUseCase,
  private val mainScheduler: MainScheduler
) : UserAccountPresenter {

  private var userAccountView: UserAccountView? = null
  private var accountType: AccountType = EXISTING_USER

  override fun attachView(view: UserAccountView) {
    userAccountView = view
  }

  override fun detachView() {
    userAccountView = null
  }

  override fun decorateView(accountType: AccountType) {
    this.accountType = accountType
    when (accountType) {
      NEW_USER -> userAccountView?.showNewAccountButton()
      EXISTING_USER -> userAccountView?.showExistingAccountButton()
    }
  }

  override fun handleSubmitClick() {
    val username = userAccountView?.getUsername()
    val password = userAccountView?.getPassword()
    if (username == null || username.isBlank()) {
      userAccountView?.showUsernameRequiredMessage()
    } else if (password == null || password.isBlank()) {
      userAccountView?.showPasswordRequiredMessage()
    } else {
      when (accountType) {
        NEW_USER -> saveUser(username, password)
        EXISTING_USER -> verifyUser(username, password)
      }
    }
  }

  private fun saveUser(userName: String, password: String) {
    userAccountsUseCase.saveNewUser(userName, password)
      .observeOn(mainScheduler.getMainScheduler())
      .autoDisposable(userAccountView!!.getScope())
      .subscribe({
        userAccountView?.redirectToHomeScreen()
      }, {
        println(it.stackTrace)
        println(it.message)
        userAccountView?.showGenericException()
      })
  }

  private fun verifyUser(userName: String, password: String) {
    userAccountsUseCase.verifyExistingUser(userName, password)
      .observeOn(mainScheduler.getMainScheduler())
      .autoDisposable(userAccountView!!.getScope())
      .subscribe({
        userAccountView?.redirectToHomeScreen()
      }, {
        userAccountView?.showUserNotAuthorizedException()
      })
  }

}