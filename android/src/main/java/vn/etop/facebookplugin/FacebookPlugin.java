package vn.etop.facebookplugin;

import android.content.res.Resources;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;


import java.util.Arrays;

import vn.etop.facebookplugin.capacitorfacebookplugin.R;

@NativePlugin()
public class FacebookPlugin extends Plugin {

    @PluginMethod()
    public void echo(PluginCall call) {
        Log.w("CHECK", getContext().getApplicationContext().getPackageName());

        FacebookSdk.setApplicationId("1581362285363031");
        FacebookSdk.sdkInitialize(getContext());
        LoginManager.getInstance().logIn(getActivity(), Arrays.asList("public_profile,pages_show_list,manage_pages,publish_pages"));
    }
}
