package com.example.maticnetwork.domain

import com.example.maticnetwork.data.Repository
import com.example.maticnetwork.exceptions.UnauthorizedUserException
import io.reactivex.Completable

interface UserAccountsUseCase {
  fun saveNewUser(userName: String, password: String): Completable
  fun verifyExistingUser(userName: String, password: String): Completable
}

class UserAccountsInteractor(private val repository: Repository) : UserAccountsUseCase {

  override fun saveNewUser(userName: String, password: String): Completable {
    return repository.getHashedCipher(userName + password)
      .flatMapCompletable {
        println("hashcipher $it")
        repository.saveHashCipher(it)
      }.andThen(repository.getEncodedCredentials(Pair(userName, password).toString()))
      .flatMapCompletable {
        println("encodedcredentials $it")
        repository.saveEncodedCredentials(it)
      }
  }

  override fun verifyExistingUser(userName: String, password: String): Completable {
    return repository.getDecodedSavedCredentials()
      .flatMapCompletable {
        Pair(userName, password).toString().let { pairString ->
          println("pairstirn $pairString")
          println("retrieved $it")
          if (pairString == it) {
            Completable.complete()
          } else {
            Completable.error(UnauthorizedUserException())
          }
        }
      }
  }
}