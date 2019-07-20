package com.example.maticnetwork.data

interface Repository {

}

class RepositoryImpl(
  private val keyStoreHelper: KeyStoreHelper,
  private val sharedPrefsHelper: SharedPrefsHelper
) : Repository {

}