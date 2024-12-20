package com.scr2em.contacts;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.PermissionState;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.ActivityCallback;
import java.util.Arrays;
import java.util.stream.Collectors;
import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.annotation.PermissionCallback;

import android.Manifest;

import android.provider.ContactsContract;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.StructuredName;

import android.content.Intent;
import android.util.Log;

@CapacitorPlugin(
	name = "CapacitorContacts",
	permissions = {
		@Permission(
			alias = "contacts",
			strings = {
				Manifest.permission.READ_CONTACTS,
				Manifest.permission.WRITE_CONTACTS
			}
		)
	}
)
public class CapacitorContactsPlugin extends Plugin {

	@PermissionCallback()
	private void contactsPermsCallback(PluginCall call) {
		if (getPermissionState("contacts") == PermissionState.GRANTED) {
			openAddContact(call);
		} else {
			call.reject("Permission is required to take a picture");
		}
	}

	@PluginMethod()
	public void openAddContact(PluginCall call) {

		if (getPermissionState("contacts") != PermissionState.GRANTED) {
			Log.d("d", "ddd");
			requestPermissionForAlias("contacts", call, "contactsPermsCallback");
			return;
		}

		String vcardString = call.getString("vcardString");
		if (vcardString == null) {
			call.reject("VCard string is required");
			return;
		}
		Log.d("vcardString", vcardString);
		String cleanVcard = Arrays.stream(vcardString.split("\n"))
			.map(String::trim)
			.filter(line -> !line.isEmpty())
			.collect(Collectors.joining("\r\n"));

		try {

			// Parse vCard using ez-vcard library
			VCard vcard = Ezvcard.parse(cleanVcard).first();

			if (vcard == null) {
				call.reject("Failed to parse vCard: parsed result is null");
				return;
			}

			// Create intent to insert a contact
			Intent intent = new Intent(Intent.ACTION_INSERT);
			intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

			// Add name
			StructuredName name = vcard.getStructuredName();
			if (name != null) {
				String fullName = String.format("%s %s",
					name.getGiven() != null ? name.getGiven() : "",
					name.getFamily() != null ? name.getFamily() : "").trim();
				intent.putExtra(ContactsContract.Intents.Insert.NAME, fullName);
			}

			// Add phone numbers
			if (!vcard.getTelephoneNumbers().isEmpty()) {
				intent.putExtra(ContactsContract.Intents.Insert.PHONE,
					vcard.getTelephoneNumbers().get(0).getText());
				intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
					ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
			}

			// Add emails
			if (!vcard.getEmails().isEmpty()) {
				intent.putExtra(ContactsContract.Intents.Insert.EMAIL,
					vcard.getEmails().get(0).getValue());
				intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
					ContactsContract.CommonDataKinds.Email.TYPE_WORK);
			}

			// Add organization
			if (!vcard.getOrganizations().isEmpty()) {
				intent.putExtra(ContactsContract.Intents.Insert.COMPANY,
					vcard.getOrganizations().get(0).getValues().get(0));
			}

			// Add title
			if (!vcard.getTitles().isEmpty()) {
				intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE,
					vcard.getTitles().get(0).getValue());
			}

			// Start activity for result
			startActivityForResult(call, intent, "handleContactResult");
		} catch (Exception e) {
			call.reject("Error opening contact add UI: " + e.getMessage());
		}
	}

	@ActivityCallback
	private void handleContactResult(PluginCall call, ActivityResult result) {
		JSObject ret = new JSObject();
		if (result.getResultCode() == android.app.Activity.RESULT_OK) {
			ret.put("success", true);
			notifyListeners("contactAdded", ret);
			call.resolve(ret);
		} else {
			ret.put("success", false);
			notifyListeners("contactAdded", ret);
			call.resolve(ret);
		}
	}
}
