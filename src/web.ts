import { WebPlugin } from '@capacitor/core';
import { FacebookPluginPlugin } from './definitions';

export class FacebookPluginWeb extends WebPlugin implements FacebookPluginPlugin {
  constructor() {
    super({
      name: 'FacebookPlugin',
      platforms: ['web']
    });
  }
  async init(options: { appId: string; }): Promise<any> {
    return options;
  }
  async login(options: {}): Promise<any> {
    return options;
  }
  async loginWithReadPermissions(options: {}): Promise<any> {
    return options;
  }
  async loginWithPublishPermissions(options: {}): Promise<any> {
    return options;
  }
}

const FacebookPlugin = new FacebookPluginWeb();

export { FacebookPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(FacebookPlugin);
