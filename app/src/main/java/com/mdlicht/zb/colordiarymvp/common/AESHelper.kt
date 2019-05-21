package com.mdlicht.zb.colordiarymvp.common

import android.util.Base64
import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * From
 * https://hyperconnect.github.io/2018/06/03/android-secure-sharedpref-howto.html
 */
object AESHelper {
    private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS5PADDING"

    fun encrypt(plainText: String, initVector: String): String {
        var cipherText: ByteArray? = null
        try {
            cipherText = with(Cipher.getInstance(CIPHER_TRANSFORMATION)) {
                init(
                    Cipher.ENCRYPT_MODE,
                    SecretKeySpec(App.getInstance().getNativeKey1().toByteArray(), "AES"),
                    IvParameterSpec(initVector.toByteArray())
                )
                return@with doFinal(plainText.toByteArray())
            }
        } catch (e: GeneralSecurityException) {
            // 특정 국가 혹은 저사양 기기에서는 알고리즘 지원하지 않을 수 있음. 특히 중국/인도 대상 기기
            e.printStackTrace()
        }

        if (cipherText == null)
            plainText.toByteArray()

        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

    fun decrypt(base64CipherText: String, initVector: String): String {
        var plainTextBytes: ByteArray? = null
        try {
            plainTextBytes = with(Cipher.getInstance(CIPHER_TRANSFORMATION)) {
                init(
                    Cipher.DECRYPT_MODE,
                    SecretKeySpec(App.getInstance().getNativeKey1().toByteArray(), "AES"),
                    IvParameterSpec(initVector.toByteArray())
                )
                val cipherText = Base64.decode(base64CipherText, Base64.DEFAULT)
                return@with doFinal(cipherText)
            }
        } catch (e: GeneralSecurityException) {
            // 특정 국가 혹은 저사양 기기에서는 알고리즘 지원하지 않을 수 있음. 특히 중국/인도 대상 기기
            e.printStackTrace()
        }

        if (plainTextBytes == null)
            plainTextBytes = Base64.decode(base64CipherText, Base64.DEFAULT)

        return String(plainTextBytes!!)
    }
}