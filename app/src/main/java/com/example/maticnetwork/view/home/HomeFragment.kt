package com.example.maticnetwork.view.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomePresenter
import com.example.maticnetwork.presenter.home.HomeContract.HomeView
import com.example.maticnetwork.utils.showToast
import com.example.maticnetwork.utils.stringRes
import com.example.maticnetwork.view.BaseFragment
import com.example.maticnetwork.view.ImageLoader
import com.example.maticnetwork.view.adapter.RecyclerViewAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

const val HOME_FRAGMENT_TAG = "HOME_FRAGMENT"

class HomeFragment : BaseFragment(), HomeView {

  @Inject
  internal lateinit var homePresenter: HomePresenter
  @Inject
  internal lateinit var recyclerAdapterPresenter: RecyclerAdapterPresenter
  @Inject
  internal lateinit var imageLoader: ImageLoader
  private lateinit var recyclerAdapter: RecyclerViewAdapter
  private var fragmentView: View? = null

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
    fragmentView = view
    attachClickListeners(view)
    initAdapter()
    hideKeyboard(activity!!)
    homePresenter.decorateView()
  }

  override fun onDestroy() {
    homePresenter.detachView()
    fragmentView = null
    super.onDestroy()
  }

  override fun showHashDialog(hash: String) {
    with(AlertDialog.Builder(context!!)) {
      setTitle(context!!.stringRes(R.string.home_fragment_hash_dialog_title))
      setMessage(hash)
      setPositiveButton(context!!.stringRes(R.string.home_fragment_hash_dialog_positive)) { _, _ -> }
      create()
      show()
    }
  }

  override fun showImagesList() {
    with(fragmentView!!) {
      recyclerView.layoutManager = LinearLayoutManager(context!!)
      recyclerView.adapter = recyclerAdapter
    }
  }

  override fun showGenericExceptionMessage() {
    with(context) {
      this?.showToast(stringRes(R.string.generic_exception_message))
    }
  }

  private fun attachClickListeners(view: View) {
    view.hashCodeButton.setOnClickListener {
      homePresenter.notifyHashButtonClick()
    }
  }

  private fun initAdapter() {
    recyclerAdapter = RecyclerViewAdapter(recyclerAdapterPresenter, imageLoader)
  }

  private fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
      activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
      view = View(activity)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
  }

  companion object {
    @JvmStatic
    fun newInstance() = HomeFragment()
  }
}
