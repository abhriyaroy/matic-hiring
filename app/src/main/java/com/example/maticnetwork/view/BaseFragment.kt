package com.example.maticnetwork.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.maticnetwork.presenter.BaseView
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class BaseFragment : Fragment(), BaseView {
  override fun getScope(): ScopeProvider {
    return AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
  }
}