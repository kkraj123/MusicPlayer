package com.kkrajmp3.musicplayer.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.kkrajmp3.musicplayer.R
import java.util.concurrent.Callable

class PermissionManager(context:Context) {
    var sessionManager = SessionManager(context)
    fun requestPermission(
        activity: Activity, fragment: Fragment?, permission:String,
        rationaleMsg: String,
        warningMsg: String, requestCode: Int, callable: Callable<Void>){
        checkPermission(activity,permission, object : PermissionAskListener {
            override fun onNeedPermission() {
                if (fragment != null){
                    fragment.requestPermissions(arrayOf(permission), requestCode)
                }else{
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
                }
            }

            override fun onPermissionPreviouslyDenied() {
                showRationale(
                    activity,
                    fragment!!,
                    permission,
                    requestCode,
                    activity.getString(R.string.permissionDenied),
                    rationaleMsg
                )

            }

            override fun onPermissionPreviouslyDeniedWithNeverAskAgain() {
                dialogForSettings(
                    activity,
                    activity.getString(R.string.permissionDenied),
                    warningMsg
                )
            }

            override fun onPermissionGranted() {
                try {
                    callable.call()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    private fun showRationale(activity: Activity, fragment: Fragment, permission: String, requestCode: Int,title: String, message: String){
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity, R.style.AlertDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message).setCancelable(false).setNegativeButton(activity.getString(R.string.deny)) { dialog: DialogInterface, which: Int -> dialog.dismiss() }.setPositiveButton(activity.getString(R.string.retry)) { dialog: DialogInterface, which: Int ->
            if (fragment != null) {
                fragment.requestPermissions(arrayOf(permission), requestCode
                )
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun dialogForSettings(activity: Activity, title: String, message: String){
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message).setCancelable(false).setNegativeButton(activity.getString(R.string.notnow)) { dialog: DialogInterface, which: Int -> dialog.dismiss() }.setPositiveButton(activity.getString(R.string.settings)
        ) { dialog: DialogInterface, which: Int ->
            goToSettings(activity)
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun goToSettings(activity: Activity){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.parse("package" + activity.packageName)
        intent.data = uri
        activity.startActivity(intent)
    }
    private fun checkPermission(context: Context, permission: String, listener: PermissionAskListener){
        if (shouldAskPermission(context, permission)){
            if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity), permission)) {
                listener.onPermissionPreviouslyDenied()
            } else {
                if (sessionManager.isFirstTimeAskingPermission(permission)) {
                    sessionManager.firstTimeAskingPermission(permission, false)
                    listener.onNeedPermission()
                } else {
                    listener.onPermissionPreviouslyDeniedWithNeverAskAgain()
                }
            }
        }
    }

    public interface PermissionAskListener {
        fun onNeedPermission()
        fun onPermissionPreviouslyDenied()
        fun onPermissionPreviouslyDeniedWithNeverAskAgain()
        fun onPermissionGranted()
    }

    private fun shouldAskPermission(context: Context, permission: String): Boolean{
        if (shouldAskPermission()){
            val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED){
                return true
            }
        }
        return false
    }

    private fun shouldAskPermission(): Boolean{
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}


class SessionManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor? = null

    fun firstTimeAskingPermission(permission: String?, isFirstTime: Boolean?) {
        doEdit()
        if (editor != null) {
            editor!!.putBoolean(permission, isFirstTime!!)
            doCommit()
        }
    }

    fun isFirstTimeAskingPermission(permission: String?): Boolean {
        return sharedPreferences.getBoolean(permission, true)
    }

    fun doEdit() {
        if (editor == null) {
            editor = sharedPreferences.edit()
        }
    }

    fun doCommit() {
        if (editor != null) {
            editor!!.commit()
            editor = null
        }
    }
}

