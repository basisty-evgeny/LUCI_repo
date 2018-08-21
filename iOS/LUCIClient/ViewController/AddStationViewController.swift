//
//  AddStationViewController.swift
//  LUCIClient
//
//  Created by adamlucas on 8/10/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit
import McPicker

class AddStationViewController: UIViewController {

    @IBOutlet weak var btnProtocol: UIButton!
    @IBOutlet weak var btnFormat: UIButton!
    @IBOutlet weak var btnBitrate: UIButton!
    @IBOutlet weak var btnSamplerate: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    @IBAction func didTapSelectProtocol(_ sender: Any) {
        McPicker.show(data: [["RTP", "SC-RTP", "SIP", "SHOUTcast", "Icecast"]]) {  [weak self] (selections: [Int : String]) -> Void in
            if let name = selections[0] {
                self?.btnProtocol.setTitle(name, for: .normal)
            }
        }
    }
    
    @IBAction func didTapSelectFormat(_ sender: Any) {
        McPicker.show(data: [["AAC", "AAC-ELD", "AAC-HE", "AAC-HEv2", "AAC-LD", "G7111-A", "G711-u", "G722", "Linear 16 bit", "Linear 24 bit", "MP2", "Opus Audio", "Opus Low delay", "Opus Voice", "ULCC", "ULCC-24", "ULCC-s"]]) {  [weak self] (selections: [Int : String]) -> Void in
            if let name = selections[0] {
                self?.btnFormat.setTitle(name, for: .normal)
            }
        }
    }

    @IBAction func didTapSelectBitrate(_ sender: Any) {
        McPicker.show(data: [["40000", "48000", "56000", "64000", "80000", "96000", "112000", "128000", "160000", "192000"]]) {  [weak self] (selections: [Int : String]) -> Void in
            if let name = selections[0] {
                self?.btnBitrate.setTitle(name, for: .normal)
            }
        }
    }

    @IBAction func didTapSelectSamplerate(_ sender: Any) {
        McPicker.show(data: [["16000", "24000", "32000", "44100", "48000"]]) {  [weak self] (selections: [Int : String]) -> Void in
            if let name = selections[0] {
                self?.btnSamplerate.setTitle(name, for: .normal)
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func didTapOk(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func didTapCancel(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)
    }
    
}
