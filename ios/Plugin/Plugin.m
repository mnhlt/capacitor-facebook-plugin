#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(FacebookPlugin, "FacebookPlugin",
           CAP_PLUGIN_METHOD(init, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(login, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(loginWithReadPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(loginWithPublishPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getCurrentAccessToken, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(logout, CAPPluginReturnPromise);

)
