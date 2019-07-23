package com.example.maticnetwork.presenter

import com.example.maticnetwork.presenter.landing.LandingContract.LandingPresenter
import com.example.maticnetwork.presenter.landing.LandingContract.LandingView
import com.example.maticnetwork.presenter.landing.LandingPresenterImpl
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LandingPresenterTest {

  @Mock
  private lateinit var landingViewMock: LandingView
  private val landingPresenter: LandingPresenter = LandingPresenterImpl()

  @Before
  fun setup() {
    landingPresenter.attachView(landingViewMock)
  }

  @Test
  fun `should redirect to new user account page`() {
    landingPresenter.handleNewAccountClick()

    verify(landingViewMock).redirectToNewAccount()
  }

  @Test
  fun `should redirect to sign in page`() {
    landingPresenter.handleSignInClick()

    verify(landingViewMock).redirectToSignIn()
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(landingViewMock)
    landingPresenter.detachView()
  }

}