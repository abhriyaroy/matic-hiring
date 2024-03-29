package com.example.maticnetwork.data

import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
  fun getEncryptedHash(plainText: String): Single<String>
  fun getSavedHash(): Single<String>
  fun saveHashCipher(cipher: String): Completable
  fun getEncodedCredentials(data: String): Single<String>
  fun getDecodedSavedCredentials(): Single<String>
  fun saveEncodedCredentials(credentials: String): Completable
}

internal const val CREDENTIALS_FIELD = "CREDENTIALS"
internal const val ENCRYPTED_HASH_FIELD = "ENCRYPTED_HASH"

class RepositoryImpl(
  private val keyStoreHelper: KeyStoreHelper,
  private val sharedPrefsHelper: SharedPrefsHelper,
  private val backgroundSchedulers: BackgroundSchedulers
) : Repository {

  override fun getEncryptedHash(plainText: String): Single<String> {
    return keyStoreHelper.getAesEncryptedText(plainText)
      .subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun getSavedHash(): Single<String> {
    return getHash()
      .subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun saveHashCipher(cipher: String): Completable {
    return Completable.create {
      sharedPrefsHelper.putString(ENCRYPTED_HASH_FIELD, cipher)
      it.onComplete()
    }.subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun getEncodedCredentials(data: String): Single<String> {
    return keyStoreHelper.getSecurePlainText(data)
      .subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun getDecodedSavedCredentials(): Single<String> {
    return getSavedCredentials()
      .flatMap {
        if (it.isBlank()) {
          Single.just("")
        } else {
          keyStoreHelper.getPlainText(it)
        }
      }.subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun saveEncodedCredentials(credentials: String): Completable {
    return Completable.create {
      sharedPrefsHelper.putString(CREDENTIALS_FIELD, credentials)
      it.onComplete()
    }.subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  private fun getSavedCredentials(): Single<String> {
    return Single.just(sharedPrefsHelper.getString(CREDENTIALS_FIELD))
  }

  private fun getHash(): Single<String> {
    return Single.just(sharedPrefsHelper.getString(ENCRYPTED_HASH_FIELD, ""))
  }
}