package com.jiangcm.common.api.config

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

@SuppressLint("CustomX509TrustManager")
class TrustAllCerts : X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    @Throws(CertificateException::class)
    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    @Throws(CertificateException::class)
    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }

    companion object{
        fun createSSLSocketFactory(): SSLSocketFactory {
            val context = SSLContext.getInstance("TLS")
            context.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
            return context.socketFactory
        }
    }

    class TrustAllHostnameVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }
}