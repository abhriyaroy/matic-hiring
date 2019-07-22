package com.example.maticnetwork.presenter.adapter

import androidx.annotation.DrawableRes
import com.example.maticnetwork.view.home.CryptoCurrencies

interface RecyclerAdapterContract {

  interface RecyclerAdapterView {
    fun populateView(@DrawableRes imageRes: Int, heading: String, abbreviation: String)
  }

  interface RecyclerAdapterPresenter {
    fun setList(list: List<CryptoCurrencies>)
    fun handleItemClick(position: Int)
    fun handleGetItemCountCall(): Int
    fun handleBindViewHolderCall(view : RecyclerAdapterView, position: Int)
  }

}