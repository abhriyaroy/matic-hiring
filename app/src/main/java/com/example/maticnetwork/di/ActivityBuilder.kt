package com.example.maticnetwork.di

import com.example.maticnetwork.di.scopes.PerActivity
import com.example.maticnetwork.view.main.MainActivity
import com.example.maticnetwork.view.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

  @PerActivity
  @ContributesAndroidInjector(modules = [(MainActivityModule::class), (FragmentProvider::class)])
  abstract fun mainActivityInjector(): MainActivity
}