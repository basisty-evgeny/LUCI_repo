//
//  ChooseStationViewController.swift
//  LUCIClient
//
//  Created by adamlucas on 8/10/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit

class ChooseStationViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func didTapDone(_ sender: Any) {
        self.navigationController?.popViewController(animated: true)

    }
    

}
