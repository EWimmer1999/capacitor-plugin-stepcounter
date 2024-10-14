// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorStepcounter",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorStepcounter",
            targets: ["StepCounterPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "StepCounterPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/StepCounterPlugin"),
        .testTarget(
            name: "StepCounterPluginTests",
            dependencies: ["StepCounterPlugin"],
            path: "ios/Tests/StepCounterPluginTests")
    ]
)