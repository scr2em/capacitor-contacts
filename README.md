# @scr2em/capacitor-contacts

A capacitor plugin for contacts

## Install

```bash
npm install @scr2em/capacitor-contacts
npx cap sync
```

## API

<docgen-index>

* [`openAddContact(...)`](#openaddcontact)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('contactAdded', ...)`](#addlistenercontactadded-)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### openAddContact(...)

```typescript
openAddContact(options: { vcardString: string; }) => Promise<void>
```

| Param         | Type                                  |
| ------------- | ------------------------------------- |
| **`options`** | <code>{ vcardString: string; }</code> |

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<{ contacts: boolean; }>
```

**Returns:** <code>Promise&lt;{ contacts: boolean; }&gt;</code>

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<{ contacts: boolean; }>
```

**Returns:** <code>Promise&lt;{ contacts: boolean; }&gt;</code>

--------------------


### addListener('contactAdded', ...)

```typescript
addListener(eventName: 'contactAdded', listenerFunc: (result: { success: boolean; }) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                                    |
| ------------------ | ------------------------------------------------------- |
| **`eventName`**    | <code>'contactAdded'</code>                             |
| **`listenerFunc`** | <code>(result: { success: boolean; }) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>
