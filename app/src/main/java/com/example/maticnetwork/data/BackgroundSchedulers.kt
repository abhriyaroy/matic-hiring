package com.example.maticnetwork.data

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

interface BackgroundSchedulers {
  fun getIoScheduler(): Scheduler
}

class BackgroundSchedulersImpl : BackgroundSchedulers {
  override fun getIoScheduler(): Scheduler {
    return Schedulers.io()
  }
}