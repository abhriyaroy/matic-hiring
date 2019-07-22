package com.example.maticnetwork.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterView
import com.example.maticnetwork.view.ImageLoader
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class RecyclerViewAdapter(
  private val recyclerPresenter: RecyclerAdapterPresenter,
  private val imageLoader: ImageLoader
) : Adapter<RecyclerViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    return RecyclerViewHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.item_recycler_view, parent, false
      ), recyclerPresenter, imageLoader
    )
  }

  override fun getItemCount(): Int {
    return recyclerPresenter.handleGetItemCountCall()
  }

  override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    recyclerPresenter.handleBindViewHolderCall(holder, position)
  }

}

class RecyclerViewHolder(
  itemView: View,
  private val recyclerPresenter: RecyclerAdapterPresenter,
  private val imageLoader: ImageLoader
) : ViewHolder(itemView), RecyclerAdapterView {

  override fun populateView(
    imageRes: Int,
    heading: String,
    abbreviation: String,
    inventoryCount: String
  ) {
    with(itemView) {
      imageLoader.loadImage(imageView, imageRes)
      headerTextView.text = heading
      abbreviationTextView.text = abbreviation
      inventoryCountTextView.text = inventoryCount
    }
  }

}