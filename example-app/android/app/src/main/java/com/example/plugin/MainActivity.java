package com.example.plugin;
import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

import com.scr2em.contacts.CapacitorContactsPlugin;
public class MainActivity extends BridgeActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		registerPlugin(CapacitorContactsPlugin.class);
		super.onCreate(savedInstanceState);
	}

}
