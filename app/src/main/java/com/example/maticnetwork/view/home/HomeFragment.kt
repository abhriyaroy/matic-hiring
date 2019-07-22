package com.example.maticnetwork.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.utils.showToast
import com.example.maticnetwork.utils.stringRes
import com.example.maticnetwork.view.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject


const val HOME_FRAGMENT_TAG = "HOME_FRAGMENT"

class HomeFragment : BaseFragment(), HomeView {

  @Inject
  internal lateinit var homePresenter: HomePresenter
  @Inject
  internal lateinit var recyclerAdapterPresenter: RecyclerAdapterPresenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    homePresenter.attachView(this)
    attachClickListeners(view)
  }

  override fun onDestroy() {
    homePresenter.detachView()
    super.onDestroy()
  }

  override fun showHashDialog(hash: String) {
    val alert = AlertDialog.Builder(context!!)
    alert.setTitle("Hash")
    alert.setMessage(hash)
    alert.setPositiveButton("Close") { _, _ -> }
    alert.show()
  }

  override fun showGenericExceptionMessage() {
    with(context) {
      this?.showToast(this?.stringRes(R.string.generic_exception_message))
    }
  }

  private fun attachClickListeners(view: View) {
    view.hashCodeButton.setOnClickListener {
      homePresenter.notifyHashButtonClick()
    }
  }

  companion object {
    @JvmStatic
    fun newInstance() = HomeFragment()
  }
}
