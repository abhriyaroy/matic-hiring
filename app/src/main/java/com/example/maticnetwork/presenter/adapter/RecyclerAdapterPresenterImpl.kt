package com.example.maticnetwork.presenter.adapter

import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.view.home.CryptoCurrencies

class RecyclerAdapterPresenterImpl : RecyclerAdapterPresenter {

  private val itemList = CryptoCurrencies.getImagesList()

  override fun handleItemLongClick(position: Int) {
  }

  override fun handleGetItemCountCall() = itemList.size

  override fun handleBindViewHolderCall(
    view: RecyclerAdapterContract.RecyclerAdapterView,
    position: Int
  ) {
    with(itemList[position]) {
      view.populateView(first, second, third, (position * 100 - (position * 10)).toString())
    }
  }

}