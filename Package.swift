// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Scr2emCapacitorContacts",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "Scr2emCapacitorContacts",
            targets: ["CapacitorContactsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "CapacitorContactsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorContactsPlugin"),
        .testTarget(
            name: "CapacitorContactsPluginTests",
            dependencies: ["CapacitorContactsPlugin"],
            path: "ios/Tests/CapacitorContactsPluginTests")
    ]
)