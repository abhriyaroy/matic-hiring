package com.example.maticnetwork.di

import android.app.Application
import com.example.maticnetwork.MaticApplication
import com.example.maticnetwork.di.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@PerApplication
@Component(
  modules = [(AndroidInjectionModule::class), (AppModule::class)]
)
interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: MaticApplication)

}