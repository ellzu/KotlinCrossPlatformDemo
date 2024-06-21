//
//  APPDelegate.swift
//  EZXX1
//
//  Created by ellzu gu on 2023/9/15.
//

import Foundation
import EZKotlinCrossPlatform

#if os(OSX)
import AppKit
class KotlinCrossPlatformDemoDelegate: NSObject, NSApplicationDelegate {
    public var viewModel: ContentViewModel = ContentViewModel(helloText: "")
    
    func applicationDidFinishLaunching(_ notification: Notification) {
        print("run osx")
    }
}
#endif
