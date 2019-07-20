package com.example.maticnetwork.view.useraccount

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountPresenter
import com.example.maticnetwork.presenter.useraccount.UserAccountPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class UserAccountModule {

  @PerFragment
  @Provides
  fun providesUserAccountPresenter(): UserAccountPresenter = UserAccountPresenterImpl()

}