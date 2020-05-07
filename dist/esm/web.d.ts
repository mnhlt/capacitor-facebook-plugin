import { WebPlugin } from '@capacitor/core';
import { FacebookPluginPlugin } from './definitions';
export declare class FacebookPluginWeb extends WebPlugin implements FacebookPluginPlugin {
    constructor();
    init(options: {
        appId: string;
    }): Promise<any>;
    login(options: {}): Promise<any>;
    loginWithReadPermissions(options: {}): Promise<any>;
    loginWithPublishPermissions(options: {}): Promise<any>;
}
declare const FacebookPlugin: FacebookPluginWeb;
export { FacebookPlugin };
