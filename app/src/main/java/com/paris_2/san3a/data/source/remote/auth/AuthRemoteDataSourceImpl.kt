package com.paris_2.san3a.data.source.remote.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val activity: Activity
) : AuthRemoteDataSource {

    override suspend fun sendOtp(phone: String): String = suspendCancellableCoroutine { cont ->
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    firebaseAuth.signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    cont.resumeWithException(e)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    cont.resume(verificationId)
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        firebaseAuth.signInWithCredential(credential).await()
    }
}