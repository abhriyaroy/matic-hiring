package com.example.maticnetwork.view.useraccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountPresenter
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountView
import com.example.maticnetwork.utils.showToast
import com.example.maticnetwork.utils.stringRes
import com.example.maticnetwork.view.BaseFragment
import com.example.maticnetwork.view.home.HOME_FRAGMENT_TAG
import com.example.maticnetwork.view.home.HomeFragment
import com.example.maticnetwork.view.useraccount.AccountType.EXISTING_USER
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

const val USER_ACCOUNT_FRAGMENT_TAG = "USER_ACCOUNT_FRAGMENT"

class UserAccountFragment : BaseFragment(), UserAccountView {

  @Inject
  internal lateinit var userAccountPresenter: UserAccountPresenter
  private var accountType = EXISTING_USER
  private var fragmentView: View? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_user_account, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fragmentView = view
    userAccountPresenter.attachView(this)
    userAccountPresenter.decorateView(accountType)
    initClickListeners()
  }

  override fun onDestroy() {
    userAccountPresenter.detachView()
    fragmentView = null
    super.onDestroy()
  }

  override fun showNewAccountButton() {
    fragmentView?.findViewById<Button>(R.id.submitButton)?.text =
      context!!.stringRes(R.string.user_account_fragment_button_create_account_text)
  }

  override fun showExistingAccountButton() {
    fragmentView?.findViewById<Button>(R.id.submitButton)?.text =
      context!!.stringRes(R.string.user_account_fragment_button_signin_text)
  }

  override fun getUsername(): String {
    return fragmentView?.findViewById<EditText>(R.id.usernameEditText)?.text.toString()
  }

  override fun getPassword(): String {
    return fragmentView?.findViewById<EditText>(R.id.passwordEditText)?.text.toString()
  }

  override fun showUsernameRequiredMessage() {
    with(context!!) {
      showToast(stringRes(R.string.user_account_fragment_username_required_message))
    }
  }

  override fun showPasswordRequiredMessage() {
    with(context!!) {
      showToast(stringRes(R.string.user_account_fragment_password_required_message))
    }
  }

  override fun redirectToHomeScreen() {
    activity?.supportFragmentManager?.beginTransaction()
      ?.replace(R.id.mainContainerFragment, HomeFragment.newInstance(), HOME_FRAGMENT_TAG)
      ?.addToBackStack(HOME_FRAGMENT_TAG)
      ?.commit()
  }

  override fun showUserNotAuthorizedException() {
    with(context!!) {
      showToast(stringRes(R.string.user_account_user_not_authorized_message))
    }
  }

  override fun showGenericException() {
    with(context!!) {
      showToast(stringRes(R.string.generic_exception_message))
    }
  }

  override fun showPreviousScreen() {
    activity?.onBackPressed()
  }

  private fun initClickListeners() {
    fragmentView?.findViewById<Button>(R.id.submitButton)?.setOnClickListener {
      userAccountPresenter.handleSubmitClick()
    }

    fragmentView?.findViewById<ImageView>(R.id.backImageView)?.setOnClickListener {
      userAccountPresenter.handleBackButtonClick()
    }
  }

  companion object {
    @JvmStatic
    fun newInstance(accountType: AccountType): UserAccountFragment {
      with(UserAccountFragment().apply {
        this.accountType = accountType
      }) {
        return this
      }
    }
  }
}

enum class AccountType {
  NEW_USER,
  EXISTING_USER
}
