import Foundation
import Capacitor
import FacebookCore
import FacebookLogin
import FBSDKCoreKit
import FBSDKLoginKit
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitor.ionicframework.com/docs/plugins/ios
 */
@objc(FacebookPlugin)
public class FacebookPlugin: CAPPlugin {
    private let loginManager = LoginManager()
    private let dateFormatter = ISO8601DateFormatter()

    private func dateToJS(_ date: Date) -> String {
        return dateFormatter.string(from: date)
    }

    @objc func `init`(_ call: CAPPluginCall) {
        guard let appId = call.getString("appId") else {
            call.reject("invalid appId argument")
            return
        }
        FBSDKCoreKit.Settings.appID = appId

        call.success()
    }

    @objc func login(_ call: CAPPluginCall) {
        guard let permissionsString = call.getString("permissions") else {
            call.error("Missing permissions argument")
            return;
        }

        let permissions = permissionsString.components(separatedBy: ",").map{ Permission.custom($0) }
        DispatchQueue.main.async {
            self.loginManager.logIn(permissions: permissions, viewController: self.bridge.viewController) { loginResult in
                switch loginResult {
                case .failed(let error):
                    print(error)
                    call.reject("LoginManager.logIn failed")

                case .cancelled:
                    print("User cancelled login")
                    call.success()

                case .success(let grantedPermissions, let declinedPermissions, let accessToken):
                    print("Logged in")
                    return self.getCurrentAccessToken(call)
                }
            }
        }
    }

    @objc func loginWithReadPermissions(_ call: CAPPluginCall) {
        call.reject("TODO implement")
    }

    @objc func loginWithPublishPermissions(_ call: CAPPluginCall) {
        call.reject("TODO implement")
    }

    private func accessTokenToJson(_ accessToken: AccessToken) -> [String: Any?] {
        return [
            "applicationId": accessToken.appID,
            "declinedPermissions": accessToken.declinedPermissions.map{$0.name},
            "expires": dateToJS(accessToken.expirationDate),
            "lastRefresh": dateToJS(accessToken.refreshDate),
            "permissions": accessToken.permissions.map{$0.name},
            "token": accessToken.tokenString,
            "userId": accessToken.userID
        ]
    }

    @objc func getCurrentAccessToken(_ call: CAPPluginCall) {
        guard let accessToken = AccessToken.current else {
            call.success()
            return
        }
        call.success([ "accessToken": accessTokenToJson(accessToken) ])
    }

    @objc func logout(_ call: CAPPluginCall) {
        loginManager.logOut()
        call.success()
    }
}
