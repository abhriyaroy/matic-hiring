package com.example.maticnetwork.presenter.adapter

import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterView
import com.example.maticnetwork.utils.CryptoCurrencies

class RecyclerAdapterPresenterImpl : RecyclerAdapterPresenter {

  private val itemList = CryptoCurrencies.getImagesList()

  override fun handleItemLongClick(view: RecyclerAdapterView, position: Int) {
    with(itemList[position]) {
      view.showDialog(first, second)
    }
  }

  override fun handleGetItemCountCall() = itemList.size

  override fun handleBindViewHolderCall(view: RecyclerAdapterView, position: Int) {
    with(itemList[position]) {
      view.populateView(first, second, third, (position * 100 - (position * 10)).toString())
    }
  }

}