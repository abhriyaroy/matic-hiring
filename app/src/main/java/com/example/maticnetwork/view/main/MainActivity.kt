package com.example.maticnetwork.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.main.MainContract.MainPresenter
import com.example.maticnetwork.presenter.main.MainContract.MainView
import com.example.maticnetwork.view.landing.LandingFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, HasSupportFragmentInjector {

  @Inject
  internal lateinit var supportFragmentInjector: AndroidInjector<Fragment>
  @Inject
  internal lateinit var mainPresenter: MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mainPresenter.attachView(this)
    mainPresenter.decorateView()
  }

  override fun onDestroy() {
    mainPresenter.detachView()
    super.onDestroy()
  }

  override fun showLandingScreen() {
    supportFragmentManager.beginTransaction()
      .replace(R.id.mainContainerFragment, LandingFragment())
      .commitAllowingStateLoss()
  }

  override fun supportFragmentInjector() = supportFragmentInjector

}
