package com.example.maticnetwork.view.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.maticnetwork.R
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterPresenter
import com.example.maticnetwork.presenter.adapter.RecyclerAdapterContract.RecyclerAdapterView
import com.example.maticnetwork.utils.stringRes
import com.example.maticnetwork.view.ImageLoader
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class RecyclerViewAdapter(
  private val recyclerPresenter: RecyclerAdapterPresenter,
  private val imageLoader: ImageLoader
) : Adapter<RecyclerViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    with(LayoutInflater.from(parent.context)) {
      return RecyclerViewHolder(
        inflate(R.layout.item_recycler_view, parent, false),
        inflate(R.layout.dialog_recycler_item_details, parent, false),
        recyclerPresenter, imageLoader
      )
    }
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
  private val alertLayout: View,
  private val recyclerPresenter: RecyclerAdapterPresenter,
  private val imageLoader: ImageLoader
) : ViewHolder(itemView), RecyclerAdapterView {

  init {
    itemView.setOnLongClickListener {
      recyclerPresenter.handleItemLongClick(this, adapterPosition)
      true
    }
  }

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

  override fun showDialog(imageRes: Int, heading: String) {
    if (alertLayout.parent != null) {
      (alertLayout.parent as ViewGroup).removeView(alertLayout)
    }
    with(AlertDialog.Builder(itemView.context)) {
      setTitle(heading)
      setView(alertLayout)
      setPositiveButton(context!!.stringRes(R.string.home_fragment_hash_dialog_positive)) { _, _ -> }
      imageLoader.loadImage(alertLayout.imageView, imageRes)
      show()
    }
  }

}