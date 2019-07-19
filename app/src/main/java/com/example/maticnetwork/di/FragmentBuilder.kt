package com.example.maticnetwork.di

import com.example.maticnetwork.view.landing.LandingFragment
import com.example.maticnetwork.view.landing.LandingModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

  @Provides
  @ContributesAndroidInjector(modules = [(LandingModule::class)])
  abstract fun providesLandingFragment(): LandingFragment

}