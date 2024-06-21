//
//  APPDelegate.swift
//  EZXX1
//
//  Created by ellzu gu on 2023/9/15.
//

import Foundation
import EZKotlinCrossPlatform

#if os(iOS)
import UIKit
class KotlinCrossPlatformDemoDelegate: NSObject, UIApplicationDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        print("\(EZKotlinCrossPlatform.Greeting().greet())")
        return true
    }
}

#endif

