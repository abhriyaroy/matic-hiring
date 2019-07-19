package com.example.maticnetwork.view.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.landing.LandingContract.LandingPresenter
import com.example.maticnetwork.presenter.landing.LandingContract.LandingView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LandingFragment : Fragment(), LandingView {

  @Inject
  internal lateinit var landingPresenter: LandingPresenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_landing, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  override fun redirectToNewAccount() {
    
  }

  override fun redirectToSignIn() {

  }

  private fun showFragment(@IdRes fragmentId: Int, fragment: Fragment) {
    activity!!.supportFragmentManager.beginTransaction()
      .replace(fragmentId, fragment)
      .commitAllowingStateLoss()
  }

}
