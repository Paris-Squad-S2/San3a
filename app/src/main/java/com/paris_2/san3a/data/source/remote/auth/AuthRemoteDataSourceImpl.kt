package com.paris_2.san3a.data.source.remote.auth

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.paris_2.san3a.presentation.util.ActivityProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val activityProvider: ActivityProvider
) : AuthRemoteDataSource {

    override suspend fun sendOtp(phone: String): String = suspendCancellableCoroutine { cont ->
        Log.d("AuthDebug", "Preparing to send OTP to: $phone")

        val currentActivity = activityProvider.getCurrentActivity()
        Log.d("AuthDebug", "Current Activity: $currentActivity")

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(currentActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("AuthDebug", "Verification completed automatically.")
                    firebaseAuth.signInWithCredential(credential)
                        .addOnSuccessListener {
                            Log.d("AuthDebug", "Auto-sign in success: ${it.user?.uid}")
                        }
                        .addOnFailureListener { ex ->
                            Log.e("AuthDebug", "Auto-sign in failed: ${ex.localizedMessage}")
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("AuthDebug", "Verification failed: ${e.localizedMessage}")
                    cont.resumeWithException(e)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    Log.d("AuthDebug", "Code sent. Verification ID: $verificationId")
                    cont.resume(verificationId)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("AuthDebug", "verifyPhoneNumber called.")
    }


    override suspend fun verifyOtp(verificationId: String, code: String): AuthResult {
        Log.d("AuthDebug", "Verifying code: $code with verificationId: $verificationId")

        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Log.d("AuthDebug", "Manual sign in success: ${result.user?.uid}")
            result
        } catch (e: Exception) {
            Log.e("AuthDebug", "Manual sign in failed: ${e.localizedMessage}")
            throw e
        }
    }
}