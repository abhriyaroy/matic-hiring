package com.example.maticnetwork.di

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.view.home.HomeFragment
import com.example.maticnetwork.view.home.HomeModule
import com.example.maticnetwork.view.landing.LandingFragment
import com.example.maticnetwork.view.landing.LandingModule
import com.example.maticnetwork.view.useraccount.UserAccountFragment
import com.example.maticnetwork.view.useraccount.UserAccountModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

  @PerFragment
  @ContributesAndroidInjector(modules = [(LandingModule::class)])
  abstract fun contributesLandingFragment(): LandingFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(UserAccountModule::class)])
  abstract fun contributesUserAccountFragment(): UserAccountFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(HomeModule::class)])
  abstract fun contributesHomeFragment(): HomeFragment
}