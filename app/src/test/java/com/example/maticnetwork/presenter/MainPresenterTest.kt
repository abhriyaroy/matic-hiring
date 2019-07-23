package com.example.maticnetwork.presenter

import com.example.maticnetwork.presenter.main.MainContract.MainView
import com.example.maticnetwork.presenter.main.MainPresenterImpl
import com.example.maticnetwork.view.useraccount.USER_ACCOUNT_FRAGMENT_TAG
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

  @Mock
  private lateinit var mainViewMock: MainView
  private val mainPresenter = MainPresenterImpl()

  @Before
  fun setup() {
    mainPresenter.attachView(mainViewMock)
  }

  @Test
  fun `should show landing screen on decorateView call success`() {
    mainPresenter.decorateView()

    verify(mainViewMock).showLandingScreen()
  }

  @Test
  fun `should show previous screen on handleBackPress call success with user account fragment on top`() {
    `when`(mainViewMock.getCurrentScreenTag()).thenReturn(USER_ACCOUNT_FRAGMENT_TAG)

    mainPresenter.handleBackPress()

    verify(mainViewMock).getCurrentScreenTag()
    verify(mainViewMock).showPreviousScreen()
  }

  @Test
  fun `should show exit confirmation message and start timer on handleBackPress call success with fragment other than user account`() {
    `when`(mainViewMock.getCurrentScreenTag()).thenReturn(randomUUID().toString())

    mainPresenter.handleBackPress()

    assertTrue(mainPresenter.isExitConfirmationShown)
    verify(mainViewMock).getCurrentScreenTag()
    verify(mainViewMock).showExitConfirmation()
    verify(mainViewMock).startBackPressedFlagResetTimer()
  }

  @Test
  fun `should show exit app on handleBackPress call success when exit confirmation was already shown`() {
    mainPresenter.isExitConfirmationShown = true

    mainPresenter.handleBackPress()

    verify(mainViewMock).exitApp()
  }

  @Test
  fun `should reset timer flag on notifyTimerExpired call success`() {
    mainPresenter.notifyTimerExpired()

    assertFalse(mainPresenter.isExitConfirmationShown)
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(mainViewMock)
    mainPresenter.detachView()
  }

}