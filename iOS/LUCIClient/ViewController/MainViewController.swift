//
//  MainViewController.swift
//  LUCIClient
//
//  Created by adamlucas on 8/10/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit
import VerticalSteppedSlider

class MainViewController: UIViewController {

    @IBOutlet weak var slider1: VSSlider!
    @IBOutlet weak var slider2: VSSlider!
    
    @IBOutlet weak var sliderIn: VSSlider!
    @IBOutlet weak var sliderOut: VSSlider!
    
    @IBOutlet weak var buttonBroadcast: UIButton!
    @IBOutlet weak var buttonHeadphone: UIButton!
    @IBOutlet weak var buttonMic: UIButton!
    
    @IBOutlet weak var viewCommands: UIView!
    
    var isBroadcasting = false
    var isHeadphoneLocked = false
    var isMicLocked = false
    
    var audioMeter:SCAudioMeter?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        slider1.backgroundColor = UIColor(white: 1, alpha: 0)
        slider2.backgroundColor = UIColor(white: 1, alpha: 0)
        sliderIn.backgroundColor = UIColor(white: 1, alpha: 0)
        sliderOut.backgroundColor = UIColor(white: 1, alpha: 0)
        
        viewCommands.layer.cornerRadius = 3.0
        
        audioMeter = SCAudioMeter(samplePeriod: 0.1)
    }

    @IBAction func didTapStartBroadcast(_ sender: Any) {
        if isBroadcasting {
            // stop broadcasting
            audioMeter?.endAudioMetering()
            self.sliderIn.value = self.sliderIn.minimumValue
        } else {
            // start broadcasting
            audioMeter?.beginAudioMetering { (value) in
                let dBValue = 10 * log10(value)
                //print(dBValue)
                self.sliderIn.value = Float(dBValue)
                self.sliderOut.value = Float(dBValue / 2)
            }
        }
        isBroadcasting = !isBroadcasting
        
        let image = UIImage(named: isBroadcasting ? "mic_red" : "mic_grey") as UIImage?
        buttonBroadcast.setImage(image, for: .normal)
    }

    @IBAction func didTapWifi(_ sender: Any) {
    }

    @IBAction func didTapSetting(_ sender: Any) {
        viewCommands.isHidden = !viewCommands.isHidden
    }
    
    @IBAction func didTapHeadphone(_ sender: Any) {
        isHeadphoneLocked = !isHeadphoneLocked
        let image = UIImage(named: isHeadphoneLocked ? "ic_headphone_disable" : "ic_headphone") as UIImage?
        buttonHeadphone.setImage(image, for: .normal)
    }
    
    @IBAction func didTapMic(_ sender: Any) {
        isMicLocked = !isMicLocked
        let image = UIImage(named: isMicLocked ? "ic_mic_headphone" : "ic_mic") as UIImage?
        buttonMic.setImage(image, for: .normal)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

}
