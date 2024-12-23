import Capacitor
import Contacts
import ContactsUI
import Foundation

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorContactsPlugin)
public class CapacitorContactsPlugin: CAPPlugin, CAPBridgedPlugin, CNContactViewControllerDelegate {
	public let identifier = "CapacitorContactsPlugin"
	public let jsName = "CapacitorContacts"
	public let pluginMethods: [CAPPluginMethod] = [
		CAPPluginMethod(name: "openAddContact", returnType: CAPPluginReturnPromise),
		CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
		CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
	]

	@objc override public func checkPermissions(_ call: CAPPluginCall) {
		let permissionState: String

		switch CNContactStore.authorizationStatus(for: .contacts) {
		case .notDetermined:
			permissionState = "prompt"
		case .restricted, .denied:
			permissionState = "denied"
		case .authorized:
			permissionState = "granted"
		@unknown default:
			permissionState = "prompt"
		}

		call.resolve([
			"contacts": permissionState
		])
	}

	@objc override public func requestPermissions(_ call: CAPPluginCall) {
		CNContactStore().requestAccess(for: .contacts) { [weak self] _, _ in
			self?.checkPermissions(call)
		}
	}

	@objc func openAddContact(_ call: CAPPluginCall) {
		guard let vcardString = call.getString("vcardString") else {
			call.reject("VCard string is required")
			return
		}

		DispatchQueue.main.async {
			// Convert vCard string to CNContact
			guard let data = vcardString.data(using: .utf8),
			      let contacts = try? CNContactVCardSerialization.contacts(with: data),
			      let contact = contacts.first
			else {
				call.reject("Could not parse vCard data")
				return
			}

			// Create the contact view controller
			let contactVC = CNContactViewController(forNewContact: contact)
			contactVC.delegate = self
			contactVC.allowsActions = true
			contactVC.allowsEditing = true

			// Present it
			if let viewController = self.bridge?.viewController {
				let navigationController = UINavigationController(rootViewController: contactVC)
				viewController.present(navigationController, animated: true)
				call.resolve()
			} else {
				call.reject("Could not get view controller")
			}
		}
	}

	// CNContactViewControllerDelegate methods
	@objc public func contactViewController(_ viewController: CNContactViewController, didCompleteWith contact: CNContact?) {
		// Dismiss the view controller
		viewController.dismiss(animated: true)
		// Notify JS side about the result
		if let contact = contact {
			print(contact.identifier)
			self.notifyListeners("contactAdded", data: ["success": true])
		} else {
			self.notifyListeners("contactAdded", data: ["success": false])
		}
	}
}
