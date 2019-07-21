package com.example.maticnetwork.view.useraccount

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.domain.UserAccountsUseCase
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountPresenter
import com.example.maticnetwork.presenter.useraccount.UserAccountPresenterImpl
import com.example.maticnetwork.view.MainScheduler
import dagger.Module
import dagger.Provides

@Module
class UserAccountModule {

  @PerFragment
  @Provides
  fun providesUserAccountPresenter(
    userAccountsUseCase: UserAccountsUseCase,
    mainScheduler: MainScheduler
  ): UserAccountPresenter = UserAccountPresenterImpl(userAccountsUseCase, mainScheduler)

}