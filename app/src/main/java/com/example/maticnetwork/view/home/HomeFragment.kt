package com.example.maticnetwork.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.view.BaseFragment

class HomeFragment : BaseFragment(), HomeView {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun showHashDialog(decryptedHash: String) {

  }



  companion object {
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      HomeFragment().apply {

      }
  }
}
