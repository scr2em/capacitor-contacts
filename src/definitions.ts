import type { PluginListenerHandle, PermissionState } from '@capacitor/core';

export interface CapacitorContactsPlugin {
  openAddContact(options: { vcardString: string }): Promise<void>;
  checkPermissions(): Promise<{ contacts: PermissionState }>;
  requestPermissions(): Promise<{ contacts: PermissionState }>;
  addListener(
    eventName: 'contactAdded',
    listenerFunc: (result: { success: boolean }) => void,
  ): Promise<PluginListenerHandle>;
}
