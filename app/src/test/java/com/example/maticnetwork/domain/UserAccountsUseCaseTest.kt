package com.example.maticnetwork.domain

import com.example.maticnetwork.data.Repository
import com.example.maticnetwork.exceptions.UnauthorizedUserException
import com.example.maticnetwork.exceptions.UserAlreadyPresentException
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID

@RunWith(MockitoJUnitRunner::class)
class UserAccountsUseCaseTest {

  @Mock
  private lateinit var repository: Repository
  private lateinit var userAccountsUseCase: UserAccountsUseCase
  private val userName = randomUUID().toString()
  private val password = randomUUID().toString()
  private val pair = Pair(userName, password)

  @Before
  fun setup() {
    userAccountsUseCase = UserAccountsInteractor(repository)
  }

  @Test
  fun `should return exception on saveNewUser call failure due to getDecodedSavedCredentials error`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.error(Exception()))
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(""))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncodedCredentials(pair.toString())
  }

  @Test
  fun `should return user already present exception on saveNewUser call failure due to user already registered`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(pair.toString()))
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(""))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(UserAlreadyPresentException::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncodedCredentials(pair.toString())
  }

  @Test
  fun `should return exception on saveNewUser call failure due to getEncryptedHash call failure`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(""))
    `when`(repository.getEncryptedHash(userName + password))
      .thenReturn(Single.error(Exception()))
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(""))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncryptedHash(userName + password)
    verify(repository).getEncodedCredentials(pair.toString())
  }

  @Test
  fun `should return exception on saveNewUser call failure due to saveHashCipher call failure`() {
    val randomEncryptedHash = randomUUID().toString()
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(""))
    `when`(repository.getEncryptedHash(userName + password))
      .thenReturn(Single.just(randomEncryptedHash))
    `when`(repository.saveHashCipher(randomEncryptedHash))
      .thenReturn(Completable.error(Exception()))
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(""))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncryptedHash(userName + password)
    verify(repository).saveHashCipher(randomEncryptedHash)
    verify(repository).getEncodedCredentials(pair.toString())
  }

  @Test
  fun `should return exception on saveNewUser call failure due to getEncodedCredentials call failure`() {
    val randomEncryptedHash = randomUUID().toString()
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(""))
    `when`(repository.getEncryptedHash(userName + password))
      .thenReturn(Single.just(randomEncryptedHash))
    `when`(repository.saveHashCipher(randomEncryptedHash))
      .thenReturn(Completable.complete())
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.error(Exception()))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncryptedHash(userName + password)
    verify(repository).saveHashCipher(randomEncryptedHash)
    verify(repository).getEncodedCredentials(pair.toString())
  }

  @Test
  fun `should return exception on saveNewUser call failure due to saveEncodedCredentials call failure`() {
    val randomEncryptedHash = randomUUID().toString()
    val randomEncodedCredentials = randomUUID().toString()
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(""))
    `when`(repository.getEncryptedHash(userName + password))
      .thenReturn(Single.just(randomEncryptedHash))
    `when`(repository.saveHashCipher(randomEncryptedHash))
      .thenReturn(Completable.complete())
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(randomEncodedCredentials))
    `when`(repository.saveEncodedCredentials(randomEncodedCredentials))
      .thenReturn(Completable.error(Exception()))

    userAccountsUseCase.saveNewUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncryptedHash(userName + password)
    verify(repository).saveHashCipher(randomEncryptedHash)
    verify(repository).getEncodedCredentials(pair.toString())
    verify(repository).saveEncodedCredentials(randomEncodedCredentials)
  }

  @Test
  fun `should complete on saveNewUser call success`() {
    val randomEncryptedHash = randomUUID().toString()
    val randomEncodedCredentials = randomUUID().toString()
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(""))
    `when`(repository.getEncryptedHash(userName + password))
      .thenReturn(Single.just(randomEncryptedHash))
    `when`(repository.saveHashCipher(randomEncryptedHash))
      .thenReturn(Completable.complete())
    `when`(repository.getEncodedCredentials(pair.toString()))
      .thenReturn(Single.just(randomEncodedCredentials))
    `when`(repository.saveEncodedCredentials(randomEncodedCredentials))
      .thenReturn(Completable.complete())

    userAccountsUseCase.saveNewUser(userName, password).test().assertComplete()

    verify(repository).getDecodedSavedCredentials()
    verify(repository).getEncryptedHash(userName + password)
    verify(repository).saveHashCipher(randomEncryptedHash)
    verify(repository).getEncodedCredentials(pair.toString())
    verify(repository).saveEncodedCredentials(randomEncodedCredentials)
  }

  @Test
  fun `should return exception on verifyExistingUser call failure due to getDecodedSavedCredentials error`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.error(Exception()))

    userAccountsUseCase.verifyExistingUser(userName, password).test()
      .assertError(Exception::class.java)

    verify(repository).getDecodedSavedCredentials()
  }

  @Test
  fun `should return unauthorized user exception on verifyExistingUser call failure`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(randomUUID().toString()))

    userAccountsUseCase.verifyExistingUser(userName, password).test()
      .assertError(UnauthorizedUserException::class.java)

    verify(repository).getDecodedSavedCredentials()
  }

  @Test
  fun `should complete on verifyExistingUser call success`() {
    `when`(repository.getDecodedSavedCredentials()).thenReturn(Single.just(pair.toString()))

    userAccountsUseCase.verifyExistingUser(userName, password).test()
      .assertComplete()

    verify(repository).getDecodedSavedCredentials()
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(repository)
  }
}