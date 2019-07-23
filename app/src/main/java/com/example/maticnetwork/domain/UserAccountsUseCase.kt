package com.example.maticnetwork.domain

import com.example.maticnetwork.data.Repository
import com.example.maticnetwork.exceptions.UnauthorizedUserException
import com.example.maticnetwork.exceptions.UserAlreadyPresentException
import io.reactivex.Completable
import io.reactivex.Single

interface UserAccountsUseCase {
  fun saveNewUser(userName: String, password: String): Completable
  fun verifyExistingUser(userName: String, password: String): Completable
  fun getHash(): Single<String>
}

class UserAccountsInteractor(private val repository: Repository) : UserAccountsUseCase {

  override fun saveNewUser(userName: String, password: String): Completable {
    return repository.getDecodedSavedCredentials()
      .flatMap {
        println(it)
        if (!it.isBlank() && Pair(userName, password).toString() == it) {
          Single.error(UserAlreadyPresentException())
        } else {
          repository.getEncryptedHash(userName + password)
        }
      }.flatMapCompletable {
        repository.saveHashCipher(it)
      }.andThen(repository.getEncodedCredentials(Pair(userName, password).toString()))
      .flatMapCompletable {
        repository.saveEncodedCredentials(it)
      }
  }

  override fun verifyExistingUser(userName: String, password: String): Completable {
    return repository.getDecodedSavedCredentials()
      .flatMapCompletable {
        Pair(userName, password).toString().let { pairString ->
          if (pairString == it) {
            Completable.complete()
          } else {
            Completable.error(UnauthorizedUserException())
          }
        }
      }
  }

  override fun getHash(): Single<String> {
    return repository.getSavedHash()
  }
}