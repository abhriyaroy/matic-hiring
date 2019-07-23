package com.example.maticnetwork.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

  @Mock
  internal lateinit var keyStoreHelper: KeyStoreHelper
  @Mock
  internal lateinit var sharedPrefsHelper: SharedPrefsHelper
  @Mock
  internal lateinit var backgroundSchedulers: BackgroundSchedulers
  private lateinit var repository: Repository
  private var randomArgumentString = randomUUID().toString()
  private var randomResultString = randomUUID().toString()

  @Before
  fun setup() {
    repository = RepositoryImpl(keyStoreHelper, sharedPrefsHelper, backgroundSchedulers)

    `when`(backgroundSchedulers.getIoScheduler()).thenReturn(Schedulers.trampoline())
  }

  @Test
  fun `should return single of encrypted hash on getEncryptedHash call success`() {
    `when`(keyStoreHelper.getAesEncryptedText(randomArgumentString)).thenReturn(
      Single.just(randomResultString)
    )

    val result = repository.getEncryptedHash(randomArgumentString).test().values()[0]

    assertEquals(randomResultString, result)
    verify(keyStoreHelper).getAesEncryptedText(randomArgumentString)
  }

  @Test
  fun `should return single of error on getEncryptedHash call failure`() {
    `when`(keyStoreHelper.getAesEncryptedText(randomArgumentString))
      .thenReturn(Single.error(Exception()))

    repository.getEncryptedHash(randomArgumentString).test().assertError(Exception::class.java)

    verify(keyStoreHelper).getAesEncryptedText(randomArgumentString)
  }

  @Test
  fun `should return single of hash on getSavedHash call success`() {
    `when`(sharedPrefsHelper.getString(ENCRYPTED_HASH_FIELD)).thenReturn(randomResultString)

    val result = repository.getSavedHash().test().values()[0]

    assertEquals(randomResultString, result)
    verify(sharedPrefsHelper).getString(ENCRYPTED_HASH_FIELD)
  }


  @Test
  fun `should  call shared prefs helper to save hashed cipher on saveHashCipher call success`() {
    repository.saveHashCipher(randomArgumentString).test().assertComplete()

    verify(sharedPrefsHelper).putString(ENCRYPTED_HASH_FIELD, randomArgumentString)
  }

  @Test
  fun `should return single of encoded credentials on getEncodedCredentials call success`() {
    `when`(keyStoreHelper.getSecurePlainText(randomArgumentString))
      .thenReturn(Single.just(randomResultString))

    val result = repository.getEncodedCredentials(randomArgumentString).test().values()[0]

    assertEquals(randomResultString, result)
    verify(keyStoreHelper).getSecurePlainText(randomArgumentString)
  }

  @Test
  fun `should return single of error on getEncodedCredentials call failure`() {
    `when`(keyStoreHelper.getSecurePlainText(randomArgumentString))
      .thenReturn(Single.error(Exception()))

    repository.getEncodedCredentials(randomArgumentString).test().assertError(Exception::class.java)

    verify(keyStoreHelper).getSecurePlainText(randomArgumentString)

  }

  @Test
  fun `should return single of empty string on getDecodedSavedCredentials call success`() {
    `when`(sharedPrefsHelper.getString(CREDENTIALS_FIELD)).thenReturn("")

    val result = repository.getDecodedSavedCredentials().test().values()[0]

    assertEquals("", result)
    verify(sharedPrefsHelper).getString(CREDENTIALS_FIELD)
  }

  @Test
  fun `should return single of saved credentials on getDecodedSavedCredentials call success`() {
    `when`(sharedPrefsHelper.getString(CREDENTIALS_FIELD)).thenReturn(randomArgumentString)
    `when`(keyStoreHelper.getPlainText(randomArgumentString))
      .thenReturn(Single.just(randomResultString))

    val result = repository.getDecodedSavedCredentials().test().values()[0]

    assertEquals(randomResultString, result)
    verify(sharedPrefsHelper).getString(CREDENTIALS_FIELD)
    verify(keyStoreHelper).getPlainText(randomArgumentString)
  }

  @Test fun `should call shared prefs helper and save credentials on saveEncodedCredentials call success`(){
    repository.saveEncodedCredentials(randomArgumentString).test().assertComplete()

    verify(sharedPrefsHelper).putString(CREDENTIALS_FIELD, randomArgumentString)
  }

  @After
  fun tearDown() {
    verify(backgroundSchedulers).getIoScheduler()
    verifyNoMoreInteractions(keyStoreHelper, sharedPrefsHelper, backgroundSchedulers)
  }
}