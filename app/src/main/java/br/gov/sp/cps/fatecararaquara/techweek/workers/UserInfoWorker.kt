package br.gov.sp.cps.fatecararaquara.techweek.workers

import android.Manifest
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class UserInfoWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val userId = getDeviceId()
        val phoneNumber = getPhoneNumber()
        val email = getPrimaryEmail()

        val output = Data.Builder()
            .putString("userId", userId)
            .putString("phoneNumber", phoneNumber)
            .putString("email", email)
            .build()

        return Result.success(output)
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceId(): String? {
        return if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val telephonyManager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            } else {
                telephonyManager.deviceId
            }
        } else {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun getPhoneNumber(): String? {
        return if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val telephonyManager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.line1Number
        } else {
            null
        }
    }

    private fun getPrimaryEmail(): String? {
        return if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            val accounts = AccountManager.get(applicationContext).accounts
            accounts.firstOrNull { it.type.equals("com.google") }?.name
        } else {
            null
        }
    }
}
