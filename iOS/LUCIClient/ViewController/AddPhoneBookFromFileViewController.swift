//
//  AddPhoneBookFromFileViewController.swift
//  LUCIClient
//
//  Created by adamlucas on 8/14/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit

class AddPhoneBookFromFileViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
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
