package com.example.maticnetwork.view.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maticnetwork.R
import com.example.maticnetwork.view.signin.AccountType.EXISTING_USER

const val USER_ACCOUNT_FRAGMENT_TAG= "USER_ACCOUNT_FRAGMENT"

class UserAccountFragment : Fragment() {

  private var accountType = EXISTING_USER

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_user_account, container, false)
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
