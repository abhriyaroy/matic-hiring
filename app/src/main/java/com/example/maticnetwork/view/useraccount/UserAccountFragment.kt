package com.example.maticnetwork.view.useraccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountPresenter
import com.example.maticnetwork.presenter.useraccount.UserAccountContract.UserAccountView
import com.example.maticnetwork.utils.stringRes
import com.example.maticnetwork.view.useraccount.AccountType.EXISTING_USER
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

const val USER_ACCOUNT_FRAGMENT_TAG = "USER_ACCOUNT_FRAGMENT"

class UserAccountFragment : Fragment(), UserAccountView {

  @Inject
  internal lateinit var userAccountPresenter: UserAccountPresenter
  private var accountType = EXISTING_USER
  private var inflatedView: View? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_user_account, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    inflatedView = view
    userAccountPresenter.attachView(this)
    userAccountPresenter.decorateView(accountType)
  }

  override fun onDestroy() {
    userAccountPresenter.detachView()
    super.onDestroy()
  }

  override fun showNewAccountButton() {
    inflatedView?.findViewById<Button>(R.id.submitButton)?.text =
      context!!.stringRes(R.string.user_account_fragment_button_create_account_text)
  }

  override fun showExistingAccountButton() {
    inflatedView?.findViewById<Button>(R.id.submitButton)?.text =
      context!!.stringRes(R.string.user_account_fragment_button_signin_text)
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
