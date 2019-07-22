package com.example.maticnetwork.presenter.useraccount

import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.exceptions.UserAlreadyPresentException
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

  override fun handleSubmitClick(userName: String, password: String) {
    if (userName.isBlank()) {
      userAccountView?.showUsernameRequiredMessage()
    } else if (password.isBlank()) {
      userAccountView?.showPasswordRequiredMessage()
    } else {
      when (accountType) {
        NEW_USER -> saveUser(userName, password)
        EXISTING_USER -> verifyUser(userName, password)
      }
    }
  }

  override fun handleBackButtonClick() {
    userAccountView?.showPreviousScreen()
  }

  private fun saveUser(userName: String, password: String) {
    userAccountsUseCase.saveNewUser(userName, password)
      .observeOn(mainScheduler.getMainScheduler())
      .doOnSubscribe {
        userAccountView?.showWaitLoader()
      }
      .autoDisposable(userAccountView!!.getScope())
      .subscribe({
        userAccountView?.hideWaitLoader()
        userAccountView?.redirectToHomeScreen()
      }, {
        userAccountView?.hideWaitLoader()
        if (it is UserAlreadyPresentException) {
          userAccountView?.showUserAlreadyPresentException()
        } else {
          userAccountView?.showGenericException()
        }
      })
  }

  private fun verifyUser(userName: String, password: String) {
    userAccountsUseCase.verifyExistingUser(userName, password)
      .observeOn(mainScheduler.getMainScheduler())
      .doOnSubscribe {
        userAccountView?.showWaitLoader()
      }
      .autoDisposable(userAccountView!!.getScope())
      .subscribe({
        userAccountView?.hideWaitLoader()
        userAccountView?.redirectToHomeScreen()
      }, {
        userAccountView?.hideWaitLoader()
        userAccountView?.showUserNotAuthorizedException()
      })
  }

}