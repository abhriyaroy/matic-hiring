package com.example.maticnetwork.di

import com.example.maticnetwork.di.scopes.PerFragment
import com.example.maticnetwork.view.landing.LandingFragment
import com.example.maticnetwork.view.landing.LandingModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

  @PerFragment
  @ContributesAndroidInjector(modules = [(LandingModule::class)])
  abstract fun providesLandingFragment(): LandingFragment

}