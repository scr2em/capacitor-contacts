import type { PluginListenerHandle } from '@capacitor/core';

export interface CapacitorContactsPlugin {
  openAddContact(options: { vcardString: string }): Promise<void>;
  checkPermissions(): Promise<{ contacts: boolean }>;
  requestPermissions(): Promise<{ contacts: boolean }>;
  addListener(
    eventName: 'contactAdded',
    listenerFunc: (result: { success: boolean }) => void,
  ): Promise<PluginListenerHandle>;
}
