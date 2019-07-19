package com.example.maticnetwork.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.main.MainContract.MainPresenter
import com.example.maticnetwork.presenter.main.MainContract.MainView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

  @Inject
  internal lateinit var mainPresenter: MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mainPresenter.attachView(this)
  }

  override fun onDestroy() {
    mainPresenter.detachView()
    super.onDestroy()
  }


}
