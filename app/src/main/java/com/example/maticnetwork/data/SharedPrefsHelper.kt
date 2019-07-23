package com.example.maticnetwork.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


interface SharedPrefsHelper {
  @Throws(Exception::class)
  fun getString(key: String, defaultValue: String = ""): String

  fun putString(key: String, value: String)
  fun setIV(iv: String)
  fun getIV(): String
  fun setAesKey(aesKey: String)
  fun getAesKey(): String
}

private const val SHARED_PREFS_NAME = "SHARED_PREFS"
private const val IV_FIELD_NAME = "IV"
private const val AES_FIELD_NAME = "AES_KEY"

class SharedPrefsHelperImpl(context: Context) : SharedPrefsHelper {

  private var sharedPrefs: SharedPreferences =
    context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
  private var sharedPrefsEditor: SharedPreferences.Editor = sharedPrefs.edit()

  override fun getString(key: String, defaultValue: String): String {
    return sharedPrefs.getString(key, "")
  }

  override fun putString(key: String, value: String) {
    sharedPrefsEditor.putString(key, value).apply()
  }

  override fun getIV(): String {
    return sharedPrefs.getString(IV_FIELD_NAME, "")
  }

  override fun setIV(iv: String) {
    sharedPrefsEditor.putString(IV_FIELD_NAME, iv).commit()
  }

  override fun getAesKey(): String {
    return sharedPrefs.getString(AES_FIELD_NAME, "")
  }

  override fun setAesKey(aesKey: String) {
    sharedPrefsEditor.putString(AES_FIELD_NAME, aesKey).commit()
  }

}