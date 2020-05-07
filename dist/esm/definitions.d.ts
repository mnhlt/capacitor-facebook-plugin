declare module "@capacitor/core" {
    interface PluginRegistry {
        FacebookPlugin: FacebookPluginPlugin;
    }
}
export interface FacebookLoginResult {
    applicationId: string;
    declinedPermissions: Array<string>;
    expires: string;
    lastRefresh: string;
    permissions: Array<string>;
    token: string;
    userId: string;
    isExpired: boolean;
}
export interface FacebookLoginResult {
    accessToken: FacebookLoginResult;
    recentlyGrantedPermissions: Array<string>;
    recentlyDeniedPermissions: Array<string>;
}
export interface FacebookPluginPlugin {
    init(options: {
        appId: string;
    }): Promise<any>;
    login(options: {
        permissions: string;
    }): Promise<FacebookLoginResult>;
    loginWithReadPermissions(options: {
        permissions: string;
    }): Promise<FacebookLoginResult>;
    loginWithPublishPermissions(options: {
        permission: string;
    }): Promise<FacebookLoginResult>;
}
