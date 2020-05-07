import { WebPlugin } from '@capacitor/core';
import { FacebookPluginPlugin } from './definitions';

export class FacebookPluginWeb extends WebPlugin implements FacebookPluginPlugin {
  constructor() {
    super({
      name: 'FacebookPlugin',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const FacebookPlugin = new FacebookPluginWeb();

export { FacebookPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(FacebookPlugin);
