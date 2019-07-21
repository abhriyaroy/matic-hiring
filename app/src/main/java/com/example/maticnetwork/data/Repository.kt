package com.example.maticnetwork.data

import com.example.maticnetwork.exceptions.UnauthorizedUserException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface Repository {
  fun getHashedCipher(plainText: String): Single<String>
  fun saveHashCipher(cipher: String): Completable
  fun getEncodedCredentials(data: String): Single<String>
  fun getDecodedSavedCredentials(): Single<String>
  fun saveEncodedCredentials(credentials: String): Completable
}

private const val CREDENTIALS_FIELD = "CREDENTIALS"
private const val ENCRYPTED_HASH_FIELD = "ENCRYPTED_HASH"

class RepositoryImpl(
  private val keyStoreHelper: KeyStoreHelper,
  private val sharedPrefsHelper: SharedPrefsHelper,
  private val backgroundSchedulers: BackgroundSchedulers
) : Repository {

  override fun getHashedCipher(plainText: String): Single<String> {
    return keyStoreHelper.getAesEncryptedText(plainText)
      .subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun saveHashCipher(cipher: String): Completable {
    return Completable.create {
      sharedPrefsHelper.putString(ENCRYPTED_HASH_FIELD, cipher)
      println("savvvedddd")
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
        keyStoreHelper.getPlainText(it)
      }.subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  override fun saveEncodedCredentials(credentials: String): Completable {
    return Completable.create {
      sharedPrefsHelper.putString(CREDENTIALS_FIELD, credentials)
      it.onComplete()
    }.subscribeOn(backgroundSchedulers.getIoScheduler())
  }

  private fun getSavedCredentials(): Single<String> {
    return Single.create {
      with(sharedPrefsHelper.getString(CREDENTIALS_FIELD)) {
        ifBlank {
          it.onError(UnauthorizedUserException())
        }
        it.onSuccess(this)
      }
    }
  }
}