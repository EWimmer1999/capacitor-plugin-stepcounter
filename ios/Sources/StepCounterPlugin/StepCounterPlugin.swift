// ios/StepCounterPlugin.swift
import Foundation
import Capacitor
import CoreMotion

@objc(StepCounterPlugin)
public class StepCounterPlugin: CAPPlugin {
    let pedometer = CMPedometer()

    @objc func start(_ call: CAPPluginCall) {
        if CMPedometer.isStepCountingAvailable() {
            pedometer.startPedometerUpdates(from: Date()) { (data, error) in
                if let error = error {
                    call.reject("Error: \(error.localizedDescription)")
                    return
                }
                let steps = data?.numberOfSteps.intValue ?? 0
                call.resolve(["steps": steps])
            }
        } else {
            call.reject("Step counting not available.")
        }
    }

    @objc func stop(_ call: CAPPluginCall) {
        pedometer.stopPedometerUpdates()
        call.resolve()
    }

    @objc func getStepCount(_ call: CAPPluginCall) {
        // Implementiere die Logik hier, um die Anzahl der Schritte zu liefern
    }
}
