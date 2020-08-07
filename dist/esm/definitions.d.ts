declare module "@capacitor/core" {
    interface PluginRegistry {
        FacebookPlugin: FacebookPluginPlugin;
    }
}
export interface AccessTokenResponse {
    applicationId: string;
    declinedPermissions: Array<string>;
    expires: string;
    lastRefresh: string;
    permissions: Array<string>;
    token: string;
    userId: string;
    isExpired: boolean;
}
export interface FacebookLoginResponse {
    accessToken: AccessTokenResponse;
    recentlyGrantedPermissions: Array<string>;
    recentlyDeniedPermissions: Array<string>;
}
export interface FacebookPluginPlugin {
    init(options: {
        appId: string;
    }): Promise<any>;
    login(options: {
        permissions: string;
    }): Promise<FacebookLoginResponse>;
    loginWithReadPermissions(options: {
        permissions: string;
    }): Promise<FacebookLoginResponse>;
    loginWithPublishPermissions(options: {
        permission: string;
    }): Promise<FacebookLoginResponse>;
    logout(): Promise<any>;
}
