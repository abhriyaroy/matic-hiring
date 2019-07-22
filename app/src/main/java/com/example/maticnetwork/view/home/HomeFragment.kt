package com.example.maticnetwork.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.home.HomeContract
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.view.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

const val HOME_FRAGMENT_TAG = "HOME_FRAGMENT"

class HomeFragment : BaseFragment(), HomeView {

  @Inject
  internal lateinit var homePresenter: HomeContract.HomePresenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun showHashDialog(decryptedHash: String) {

  }

  companion object {
    @JvmStatic
    fun newInstance() = HomeFragment()
  }
}
