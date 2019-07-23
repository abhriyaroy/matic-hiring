package com.example.maticnetwork.presenter

import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.presenter.home.HomePresenterImpl
import com.example.maticnetwork.view.MainScheduler
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.uber.autodispose.lifecycle.TestLifecycleScopeProvider
import com.uber.autodispose.lifecycle.TestLifecycleScopeProvider.TestLifecycle.STARTED
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID


@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

  @Mock
  private lateinit var homeViewMock: HomeView
  @Mock
  private lateinit var mainScheduler: MainScheduler
  @Mock
  private lateinit var userAccountsUseCase: UserAccountsUseCase
  private lateinit var homePresenter: HomePresenter
  private lateinit var testScopeProvider: TestLifecycleScopeProvider
  private val randomString = randomUUID().toString()

  @Before
  fun setup() {
    homePresenter = HomePresenterImpl(userAccountsUseCase, mainScheduler)
    testScopeProvider = TestLifecycleScopeProvider.createInitial(STARTED)

    `when`(homeViewMock.getScope()).thenReturn(testScopeProvider)
    `when`(mainScheduler.getMainScheduler()).thenReturn(Schedulers.trampoline())
    homePresenter.attachView(homeViewMock)
  }

  @Test
  fun `should show cryptolist on decorateViewCall success`() {
    homePresenter.decorateView()

    verify(homeViewMock).showImagesList()
  }

  @Test
  fun `should show generic exception message on notifyHashButtonClick call failure`() {
    `when`(userAccountsUseCase.getHash()).thenReturn(Single.error(Exception()))

    homePresenter.notifyHashButtonClick()

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).getHash()
    verify(homeViewMock).getScope()
    verify(homeViewMock).showGenericExceptionMessage()
  }

  @Test
  fun `should show show hash on notifyHashButtonClick call success`() {
    `when`(userAccountsUseCase.getHash()).thenReturn(Single.just(randomString))

    homePresenter.notifyHashButtonClick()

    verify(mainScheduler).getMainScheduler()
    verify(userAccountsUseCase).getHash()
    verify(homeViewMock).getScope()
    verify(homeViewMock).showHashDialog(randomString)
  }

  @After
  fun teardown() {
    verifyNoMoreInteractions(homeViewMock, userAccountsUseCase, mainScheduler)
    homePresenter.detachView()
  }

}
