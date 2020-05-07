import { WebPlugin } from '@capacitor/core';
import { FacebookPluginPlugin } from './definitions';
export declare class FacebookPluginWeb extends WebPlugin implements FacebookPluginPlugin {
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
declare const FacebookPlugin: FacebookPluginWeb;
export { FacebookPlugin };
