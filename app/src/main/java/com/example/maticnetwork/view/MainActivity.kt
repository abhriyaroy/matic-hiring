package com.example.maticnetwork.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.main.MainContract.MainView

class MainActivity : AppCompatActivity(), MainView {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()

  }


}
