import Foundation

@objc public class CapacitorContacts: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
