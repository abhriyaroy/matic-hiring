package com.example.maticnetwork.presenter.adapter

import androidx.annotation.DrawableRes

interface RecyclerAdapterContract {

  interface RecyclerAdapterView {
    fun populateView(
      @DrawableRes imageRes: Int,
      heading: String,
      abbreviation: String,
      inventoryCount: String
    )

    fun showDialog(@DrawableRes imageRes: Int, heading: String)
  }

  interface RecyclerAdapterPresenter {
    fun handleItemLongClick(view: RecyclerAdapterView, position: Int)
    fun handleGetItemCountCall(): Int
    fun handleBindViewHolderCall(view: RecyclerAdapterView, position: Int)
  }

}