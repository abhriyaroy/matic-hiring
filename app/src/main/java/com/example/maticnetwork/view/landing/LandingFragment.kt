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
import com.example.maticnetwork.view.BaseFragment
import com.example.maticnetwork.view.useraccount.AccountType.EXISTING_USER
import com.example.maticnetwork.view.useraccount.AccountType.NEW_USER
import com.example.maticnetwork.view.useraccount.USER_ACCOUNT_FRAGMENT_TAG
import com.example.maticnetwork.view.useraccount.UserAccountFragment.Companion.newInstance
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_landing.view.*
import javax.inject.Inject

const val LANDING_FRAGMENT_TAG = "LANDING_FRAGMENT"

class LandingFragment : BaseFragment(), LandingView {

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
    landingPresenter.attachView(this)
    initListeners(view)
  }

  override fun onDestroyView() {
    landingPresenter.detachView()
    super.onDestroyView()
  }

  override fun redirectToNewAccount() {
    showFragment(R.id.mainContainerFragment, newInstance(NEW_USER))
  }

  override fun redirectToSignIn() {
    showFragment(R.id.mainContainerFragment, newInstance(EXISTING_USER))
  }

  private fun initListeners(view: View) {
    with(view) {
      newAccountButton.setOnClickListener {
        landingPresenter.handleNewAccountClick()
      }
      signInButton.setOnClickListener {
        landingPresenter.handleSignInClick()
      }
    }
  }

  private fun showFragment(@IdRes fragmentId: Int, fragment: Fragment) {
    activity!!.supportFragmentManager.beginTransaction()
      .replace(fragmentId, fragment, USER_ACCOUNT_FRAGMENT_TAG)
      .addToBackStack(USER_ACCOUNT_FRAGMENT_TAG)
      .commit()
  }

}
