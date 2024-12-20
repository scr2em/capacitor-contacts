import { registerPlugin } from '@capacitor/core';

import type { CapacitorContactsPlugin } from './definitions';

export const CapacitorContacts = registerPlugin<CapacitorContactsPlugin>('CapacitorContacts' );

export * from './definitions';
