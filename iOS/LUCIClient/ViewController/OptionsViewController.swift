//
//  OptionsViewController.swift
//  LUCIClient
//
//  Created by adamlucas on 8/10/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit

class OptionsViewController: UIViewController {

    @IBOutlet weak var btnDefaults: UIButton!
    @IBOutlet weak var btnSIP: UIButton!
    @IBOutlet weak var btnGeneral: UIButton!

    @IBOutlet weak var containerDefaults: BorderView!
    @IBOutlet weak var containerSIP: UIView!
    @IBOutlet weak var containerGeneral: UIView!
    
    var viewMode:Int = 0 // 0 - Defaults, 1 - SIP, 2 - General
    
    override func viewDidLoad() {
        super.viewDidLoad()

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

    @IBAction func didTapDefaults(_ sender: Any) {
        viewMode = 0
        updateUI()
    }
    
    @IBAction func didTapSIP(_ sender: Any) {
        viewMode = 1
        updateUI()
    }

    @IBAction func didTapGeneral(_ sender: Any) {
        viewMode = 2
        updateUI()
    }

    func updateUI() {
        containerDefaults.isHidden = (viewMode != 0)
        containerSIP.isHidden = (viewMode != 1)
        containerGeneral.isHidden = (viewMode != 2)
        
        btnDefaults.backgroundColor = (viewMode == 0) ? .white : .lightGray
        btnSIP.backgroundColor = (viewMode == 1) ? .white : .lightGray
        btnGeneral.backgroundColor = (viewMode == 2) ? .white : .lightGray

    }
}
