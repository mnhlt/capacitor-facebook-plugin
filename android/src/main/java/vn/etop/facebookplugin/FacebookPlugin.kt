package vn.etop.facebookplugin

import android.content.Intent
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.getcapacitor.*
import java.text.SimpleDateFormat
import java.util.*


@NativePlugin(
        requestCodes = intArrayOf(0xface)
)
class FacebookPlugin : Plugin() {

    lateinit var callbackManager: CallbackManager

    private fun dateToJson(date: Date): String? {
        var simpleDateFormat: SimpleDateFormat
        try {
            simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        } catch (e: java.lang.Exception) {
            simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
        }
        return simpleDateFormat.format(date)
    }

    private fun collectionToJson(list: Collection<String>): JSArray? {
        val json = JSArray()
        for (item in list) {
            json.put(item)
        }
        return json
    }

    private fun accessTokenToJson(accessToken: AccessToken): JSObject? {
        val ret = JSObject()
        ret.put("applicationId", accessToken.applicationId)
        ret.put("declinedPermissions", collectionToJson(accessToken.declinedPermissions))
        ret.put("expires", dateToJson(accessToken.expires))
        ret.put("lastRefresh", dateToJson(accessToken.lastRefresh))
        ret.put("permissions", collectionToJson(accessToken.permissions))
        ret.put("token", accessToken.token)
        ret.put("userId", accessToken.userId)
        ret.put("isExpired", accessToken.isExpired)
        return ret
    }

    private fun getPermission(call: PluginCall?): Collection<String> {
        val permissionsString = call?.getString("permissions")
        if (permissionsString == null) {
            throw Exception("permissions is required")
        }
        return Arrays.asList(permissionsString)
    }

    override fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(getLogTag(), "Entering handleOnActivityResult(" + requestCode + ", " + resultCode + ")");

        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            Log.d(getLogTag(), "onActivityResult succeeded");
        } else {
            Log.w(getLogTag(), "onActivityResult failed");
        }
    }

    @PluginMethod
    fun init(call: PluginCall?) {
        try {
            val appId = call?.getString("appId")
            if (appId == null) {
                throw Exception("invalid appId argument")
            }
            FacebookSdk.setApplicationId(appId)
            FacebookSdk.sdkInitialize(context)

            this.callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            Log.d(logTag, "LoginManager.onSuccess")
                            val savedCall = savedCall
                            if (savedCall == null) {
                                Log.e(logTag, "LoginManager.onSuccess: no plugin saved call found.")
                            } else {
                                val ret = JSObject()
                                ret.put("accessToken", accessTokenToJson(loginResult.accessToken))
                                ret.put("recentlyGrantedPermissions", collectionToJson(loginResult.recentlyGrantedPermissions))
                                ret.put("recentlyDeniedPermissions", collectionToJson(loginResult.recentlyDeniedPermissions))
                                savedCall.success(ret)
                                saveCall(null)
                            }
                        }

                        override fun onCancel() {
                            Log.d(logTag, "LoginManager.onCancel")
                            val savedCall = savedCall
                            if (savedCall == null) {
                                Log.e(logTag, "LoginManager.onCancel: no plugin saved call found.")
                            } else {
                                val ret = JSObject()
                                ret.put("accessToken", null)
                                savedCall.success(ret)
                                saveCall(null)
                            }
                        }

                        override fun onError(exception: FacebookException) {
                            Log.e(logTag, "LoginManager.onError", exception)
                            val savedCall = savedCall
                            if (savedCall == null) {
                                Log.e(logTag, "LoginManager.onError: no plugin saved call found.")
                            } else {
                                savedCall.reject(exception.toString())
                                saveCall(null)
                            }
                        }
                    })

            val result = JSObject()
            result.put("message", "OK")
            call?.resolve(result)
        } catch (e: Exception) {
            Log.e(getLogTag(), e.message)
            call?.reject(e.message, e)
        }
    }

    @PluginMethod
    fun login(call: PluginCall?) {
        try {
            val permissions: Collection<String> = this.getPermission(call)
            LoginManager.getInstance().logIn(activity, permissions)
            saveCall(call);
        }catch (e: Exception) {
            Log.e(logTag, "Login: ${e.message}", e)
            call?.reject(e.message, e)
        }
    }

    @PluginMethod
    fun loginWithReadPermissions(call: PluginCall?) {
        try {
            val permissions: Collection<String> = this.getPermission(call)
            LoginManager.getInstance().logInWithReadPermissions(activity, permissions)
            saveCall(call);
        }catch (e: Exception) {
            Log.e(logTag, "Login: ${e.message}", e)
            call?.reject(e.message, e)
        }
    }

    @PluginMethod
    fun loginWithPublishPermissions(call: PluginCall?) {
        try {
            val permissions: Collection<String> = this.getPermission(call)
            LoginManager.getInstance().logInWithPublishPermissions(activity, permissions)
            saveCall(call);
        }catch (e: Exception) {
            Log.e(logTag, "Login: ${e.message}", e)
            call?.reject(e.message, e)
        }
    }

    @PluginMethod
    fun logout(call: PluginCall) {
        Log.d(logTag, "Entering logout()")
        LoginManager.getInstance().logOut()
        call.success()
    }

    @PluginMethod
    fun getCurrentAccessToken(call: PluginCall) {
        Log.d(logTag, "Entering getCurrentAccessToken()")
        val accessToken = AccessToken.getCurrentAccessToken()
        val ret = JSObject()
        if (accessToken == null) {
            Log.d(logTag, "getCurrentAccessToken: accessToken is null")
        } else {
            Log.d(logTag, "getCurrentAccessToken: accessToken found")
            ret.put("accessToken", accessTokenToJson(accessToken))
        }
        call.success(ret)
    }
}