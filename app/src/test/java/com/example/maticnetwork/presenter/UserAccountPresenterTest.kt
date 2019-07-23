package com.example.maticnetwork.presenter

import android.security.keystore.UserNotAuthenticatedException
import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.exceptions.UserAlreadyPresentException
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountView
import com.example.maticnetwork.presenter.useraccount.UserAccountPresenterImpl
import com.example.maticnetwork.view.MainScheduler
import com.example.maticnetwork.view.useraccount.AccountType.EXISTING_USER
import com.example.maticnetwork.view.useraccount.AccountType.NEW_USER
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.uber.autodispose.lifecycle.TestLifecycleScopeProvider
import com.uber.autodispose.lifecycle.TestLifecycleScopeProvider.TestLifecycle.STARTED
import com.uber.autodispose.lifecycle.TestLifecycleScopeProvider.createInitial
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID

@RunWith(MockitoJUnitRunner::class)
class UserAccountPresenterTest {

  @Mock
  private lateinit var userAccountViewMock: UserAccountView
  @Mock
  private lateinit var mainScheduler: MainScheduler
  @Mock
  private lateinit var userAccountsUseCase: UserAccountsUseCase
  private lateinit var userAccountPresenter: UserAccountPresenterImpl
  private lateinit var testScopeProvider: TestLifecycleScopeProvider
  private val randomUsername = randomUUID().toString()
  private val randomPassword = randomUUID().toString()

  @Before
  fun setup() {
    userAccountPresenter = UserAccountPresenterImpl(userAccountsUseCase, mainScheduler)
    testScopeProvider = createInitial(STARTED)

    `when`(userAccountViewMock.getScope()).thenReturn(testScopeProvider)
    `when`(mainScheduler.getMainScheduler()).thenReturn(Schedulers.trampoline())
    userAccountPresenter.attachView(userAccountViewMock)
  }

  @Test
  fun `should show new account screen on decorateView call success with new user type argument`() {
    userAccountPresenter.decorateView(NEW_USER)

    assertEquals(userAccountPresenter.accountType, NEW_USER)
    verify(userAccountViewMock).showNewAccountButton()
  }

  @Test
  fun `should show existing user screen on decorateView call success with existing user type argument`() {
    userAccountPresenter.decorateView(EXISTING_USER)

    assertEquals(userAccountPresenter.accountType, EXISTING_USER)
    verify(userAccountViewMock).showExistingAccountButton()
  }

  @Test
  fun `should show previous screen on handleBackButtonClick call success`() {
    userAccountPresenter.handleBackButtonClick()

    verify(userAccountViewMock).showPreviousScreen()
  }

  @Test
  fun `should show username required message on handleSubmitClick call failure due to missing username`() {
    userAccountPresenter.handleSubmitClick("", randomPassword)

    verify(userAccountViewMock).showUsernameRequiredMessage()
  }

  @Test
  fun `should show password required message on handleSubmitClick of type new user call failure due to missing password`() {
    userAccountPresenter.handleSubmitClick(randomUsername, "")

    verify(userAccountViewMock).showPasswordRequiredMessage()
  }

  @Test
  fun `should show user already present message on handleSubmitClick of type new user call failure due to existing user`() {
    `when`(userAccountsUseCase.saveNewUser(randomUsername, randomPassword))
      .thenReturn(Completable.error(UserAlreadyPresentException()))
    userAccountPresenter.accountType = NEW_USER

    userAccountPresenter.handleSubmitClick(randomUsername, randomPassword)

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).saveNewUser(randomUsername, randomPassword)
    verify(userAccountViewMock).getScope()
    verify(userAccountViewMock).showWaitLoader()
    verify(userAccountViewMock).hideWaitLoader()
    verify(userAccountViewMock).showUserAlreadyPresentException()
  }

  @Test
  fun `should show generic exception on handleSubmitClick of type new user call failure`() {
    `when`(userAccountsUseCase.saveNewUser(randomUsername, randomPassword))
      .thenReturn(Completable.error(Exception()))
    userAccountPresenter.accountType = NEW_USER

    userAccountPresenter.handleSubmitClick(randomUsername, randomPassword)

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).saveNewUser(randomUsername, randomPassword)
    verify(userAccountViewMock).getScope()
    verify(userAccountViewMock).showWaitLoader()
    verify(userAccountViewMock).hideWaitLoader()
    verify(userAccountViewMock).showGenericException()
  }

  @Test
  fun `should redirect to home screen on handleSubmitClick of type new user call success`() {
    `when`(userAccountsUseCase.saveNewUser(randomUsername, randomPassword))
      .thenReturn(Completable.complete())
    userAccountPresenter.accountType = NEW_USER

    userAccountPresenter.handleSubmitClick(randomUsername, randomPassword)

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).saveNewUser(randomUsername, randomPassword)
    verify(userAccountViewMock).getScope()
    verify(userAccountViewMock).showWaitLoader()
    verify(userAccountViewMock).hideWaitLoader()
    verify(userAccountViewMock).redirectToHomeScreen()
  }

  @Test
  fun `should show user not authorized message on handleSubmitClick of type existing user call failure`() {
    `when`(userAccountsUseCase.verifyExistingUser(randomUsername, randomPassword))
      .thenReturn(Completable.error(UserNotAuthenticatedException()))
    userAccountPresenter.accountType = EXISTING_USER

    userAccountPresenter.handleSubmitClick(randomUsername, randomPassword)

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).verifyExistingUser(randomUsername, randomPassword)
    verify(userAccountViewMock).getScope()
    verify(userAccountViewMock).showWaitLoader()
    verify(userAccountViewMock).hideWaitLoader()
    verify(userAccountViewMock).showUserNotAuthorizedException()
  }

  @Test
  fun `should redirect to home screen on handleSubmitClick of type existing user call success`() {
    `when`(userAccountsUseCase.verifyExistingUser(randomUsername, randomPassword))
      .thenReturn(Completable.complete())
    userAccountPresenter.accountType = EXISTING_USER

    userAccountPresenter.handleSubmitClick(randomUsername, randomPassword)

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).verifyExistingUser(randomUsername, randomPassword)
    verify(userAccountViewMock).getScope()
    verify(userAccountViewMock).showWaitLoader()
    verify(userAccountViewMock).hideWaitLoader()
    verify(userAccountViewMock).redirectToHomeScreen()
  }

  @After
  fun tearDown() {
    userAccountPresenter.detachView()
    verifyNoMoreInteractions(userAccountViewMock, mainScheduler, userAccountsUseCase)
  }

}