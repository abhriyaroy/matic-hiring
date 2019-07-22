package com.example.maticnetwork.data

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.*
import android.util.Base64.*
import androidx.annotation.RequiresApi
import com.example.maticnetwork.exceptions.DecryptionNotPossibleException
import io.reactivex.Single
import java.math.BigInteger
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.security.auth.x500.X500Principal


interface KeyStoreHelper {
  fun getAesEncryptedText(plainText: String): Single<String>
  fun getAesDecryptedText(cipherText: String): Single<String>
  fun getSecurePlainText(plainText: String): Single<String>
  fun getPlainText(cipherText: String): Single<String>
}

class KeyStoreHelperImpl(
  private val context: Context,
  private val prefsHelper: SharedPrefsHelper
) : KeyStoreHelper {

  private val keyStoreProvider = "AndroidKeyStore"
  private val aesMode = "AES/GCM/NoPadding"
  private val rsaMode = "RSA/ECB/PKCS1Padding"
  private val alias = "MATIC_NETWORK"
  private var keyStore: KeyStore

  init {
    keyStore = KeyStore.getInstance(keyStoreProvider)
    keyStore.load(null)
  }

  override fun getAesEncryptedText(plainText: String): Single<String> {
    return Single.create {
      if (!keyStore.containsAlias(alias)) {
        genKeyStoreKey(context)
        genAESKey()
      }
      with(encryptAES(plainText)) {
        it.onSuccess(this)
      }
    }
  }

  override fun getAesDecryptedText(cipherText: String): Single<String> {
    return Single.create {
      if (!keyStore.containsAlias(alias)) {
        it.onError(DecryptionNotPossibleException())
      }
      with(decryptAES(cipherText)) {
        it.onSuccess(this)
      }
    }
  }

  override fun getSecurePlainText(plainText: String): Single<String> {
    return Single.create {
      with(encryptRSA(plainText.toByteArray())) {
        it.onSuccess(this)
      }
    }
  }

  override fun getPlainText(cipherText: String): Single<String> {
    return Single.create {
      with(decryptRSA(cipherText)) {
        it.onSuccess(String(this))
      }
    }
  }

  @Throws(Exception::class)
  private fun genKeyStoreKey(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      generateRsaKeyAboveVersionM()
    } else {
      generateRsaKeyBelowVersionM(context)
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Throws(Exception::class)
  private fun generateRsaKeyAboveVersionM() {
    val keyPairGenerator = KeyPairGenerator
      .getInstance(KEY_ALGORITHM_RSA, keyStoreProvider)
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
      alias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT
    ).setDigests(DIGEST_SHA256, DIGEST_SHA512)
      .setEncryptionPaddings(ENCRYPTION_PADDING_RSA_PKCS1)
      .build()
    keyPairGenerator.initialize(keyGenParameterSpec)
    keyPairGenerator.generateKeyPair()
  }

  @Throws(
    NoSuchAlgorithmException::class,
    NoSuchProviderException::class,
    InvalidAlgorithmParameterException::class
  )
  private fun generateRsaKeyBelowVersionM(context: Context) {
    val start = Calendar.getInstance()
    val end = Calendar.getInstance()
    end.add(Calendar.YEAR, 30)
    val spec = KeyPairGeneratorSpec.Builder(context)
      .setAlias(alias)
      .setSubject(X500Principal("CN=$alias"))
      .setSerialNumber(BigInteger.TEN)
      .setStartDate(start.time)
      .setEndDate(end.time)
      .build()
    val keyPairGenerator = KeyPairGenerator
      .getInstance(KEY_ALGORITHM_RSA, keyStoreProvider)
    keyPairGenerator.initialize(spec)
    keyPairGenerator.generateKeyPair()
  }

  @Throws(Exception::class)
  private fun encryptRSA(plainText: ByteArray): String {
    val publicKey = keyStore.getCertificate(alias).publicKey
    val cipher = Cipher.getInstance(rsaMode)
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    val encryptedByte = cipher.doFinal(plainText)
    return encodeToString(encryptedByte, DEFAULT)
  }


  @Throws(Exception::class)
  private fun decryptRSA(encryptedText: String): ByteArray {
    val privateKey = keyStore.getKey(alias, null) as PrivateKey
    val cipher = Cipher.getInstance(rsaMode)
    cipher.init(Cipher.DECRYPT_MODE, privateKey)
    val encryptedBytes = decode(encryptedText, DEFAULT)
    return cipher.doFinal(encryptedBytes)
  }

  @Throws(Exception::class)
  private fun genAESKey() {
    val aesKey = ByteArray(16)
    val secureRandom = SecureRandom()
    secureRandom.nextBytes(aesKey)
    // Generates random 20 byte long hash as required
    val generated = secureRandom.generateSeed(20)
    val iv = encodeToString(generated, DEFAULT)
    prefsHelper.setIV(iv)
    val encryptAESKey = encryptRSA(aesKey)
    prefsHelper.setAesKey(encryptAESKey)
  }

  @Throws(Exception::class)
  private fun encryptAES(plainText: String): String {
    val cipher = Cipher.getInstance(aesMode)
    cipher.init(Cipher.ENCRYPT_MODE, getAESKey(), IvParameterSpec(getIV()))
    val encryptedBytes = cipher.doFinal(plainText.toByteArray())
    return encodeToString(encryptedBytes, DEFAULT)
  }


  @Throws(Exception::class)
  private fun decryptAES(encryptedText: String): String {
    val decodedBytes = decode(encryptedText.toByteArray(), DEFAULT)
    val cipher = Cipher.getInstance(aesMode)
    cipher.init(Cipher.DECRYPT_MODE, getAESKey(), IvParameterSpec(getIV()))
    return String(cipher.doFinal(decodedBytes))
  }

  private fun getIV(): ByteArray {
    val prefIV = prefsHelper.getIV()
    return decode(prefIV, DEFAULT)
  }

  @Throws(Exception::class)
  private fun getAESKey(): SecretKeySpec {
    val encryptedKey = prefsHelper.getAesKey()
    val aesKey = decryptRSA(encryptedKey)
    return SecretKeySpec(aesKey, aesMode)
  }

}