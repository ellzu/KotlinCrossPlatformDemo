//
//  EZXX1App.swift
//  EZXX1
//
//  Created by ellzu gu on 2023/9/15.
//

import SwiftUI

#if os(iOS)
import UIKit
#elseif os(OSX)
import AppKit
#endif

@main
struct KotlinCrossPlatformDemoAPP: App {
    @UIApplicationDelegateAdaptor var appDelegate: KotlinCrossPlatformDemoDelegate
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
