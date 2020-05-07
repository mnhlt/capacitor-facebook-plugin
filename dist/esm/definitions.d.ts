declare module "@capacitor/core" {
    interface PluginRegistry {
        FacebookPlugin: FacebookPluginPlugin;
    }
}
export interface FacebookPluginPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
