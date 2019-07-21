package com.example.maticnetwork.view

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

interface MainScheduler {
  fun getMainScheduler(): Scheduler
}

class MainSchedulerImpl : MainScheduler {
  override fun getMainScheduler(): Scheduler {
    return AndroidSchedulers.mainThread()
  }
}