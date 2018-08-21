//
//  BorderView.swift
//  LUCIClient
//
//  Created by adamlucas on 8/13/18.
//  Copyright © 2018 Adrian. All rights reserved.
//

import UIKit

class BorderView: UIView {

    enum BorderPos {
        case top
        case bottom
        case left
        case right
    }
    
    fileprivate var topLine: UIView!
    fileprivate var bottomLine: UIView!
    fileprivate var leftLine: UIView!
    fileprivate var rightLine: UIView!
    
    @IBInspectable var cornerRadius: CGFloat {
        get {
            return layer.cornerRadius
        }
        set {
            layer.cornerRadius = newValue
            layer.masksToBounds = newValue > 0
        }
    }
    
    @IBInspectable var borderWidth: CGFloat {
        get {
            return layer.borderWidth
        }
        set {
            layer.borderWidth = newValue
        }
    }
    
    @IBInspectable var borderColor: UIColor? {
        get {
            return UIColor(cgColor: layer.borderColor!)
        }
        set {
            layer.borderColor = newValue?.cgColor
        }
    }
    
    @IBInspectable var leftBorderWidth: CGFloat {
        get {
            return 0.0   // Just to satisfy property
        }
        set {
            self.removeLine(leftLine)
            leftLine = UIView(frame: CGRect(x: 0.0, y: 0.0, width: newValue, height: bounds.height))
            leftLine.translatesAutoresizingMaskIntoConstraints = false
            leftLine.backgroundColor = UIColor(cgColor: layer.borderColor!)
            self.addSubview(leftLine)
            
            let views = ["line": leftLine]
            let metrics = ["lineWidth": newValue]
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "|[line(==lineWidth)]", options: [], metrics: metrics, views: views))
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[line]|", options: [], metrics: nil, views: views))
        }
    }
    
    @IBInspectable var topBorderWidth: CGFloat {
        get {
            return 0.0   // Just to satisfy property
        }
        set {
            self.removeLine(topLine)
            topLine = UIView(frame: CGRect(x: 0.0, y: 0.0, width: bounds.width, height: newValue))
            topLine.translatesAutoresizingMaskIntoConstraints = false
            topLine.backgroundColor = borderColor
            self.addSubview(topLine)
            
            let views = ["line": topLine]
            let metrics = ["lineWidth": newValue]
            
            
            
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "|[line]|", options: [], metrics: nil, views: views))
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[line(==lineWidth)]", options: [], metrics: metrics, views: views))
        }
    }
    
    @IBInspectable var rightBorderWidth: CGFloat {
        get {
            return 0.0   // Just to satisfy property
        }
        set {
            self.removeLine(rightLine)
            rightLine = UIView(frame: CGRect(x: bounds.width, y: 0.0, width: newValue, height: bounds.height))
            rightLine.translatesAutoresizingMaskIntoConstraints = false
            rightLine.backgroundColor = borderColor
            self.addSubview(rightLine)
            
            let views = ["line": rightLine]
            let metrics = ["lineWidth": newValue]
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "[line(==lineWidth)]|", options: [], metrics: metrics, views: views))
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:|[line]|", options: [], metrics: nil, views: views))
        }
    }
    @IBInspectable var bottomBorderWidth: CGFloat {
        get {
            return 0.0   // Just to satisfy property
        }
        set {
            self.removeLine(bottomLine)
            bottomLine = UIView(frame: CGRect(x: 0.0, y: bounds.height, width: bounds.width, height: newValue))
            bottomLine.translatesAutoresizingMaskIntoConstraints = false
            bottomLine.backgroundColor = borderColor
            self.addSubview(bottomLine)
            
            let views = ["line": bottomLine]
            let metrics = ["lineWidth": newValue]
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "|[line]|", options: [], metrics: nil, views: views))
            addConstraints(NSLayoutConstraint.constraints(withVisualFormat: "V:[line(==lineWidth)]|", options: [], metrics: metrics, views: views))
        }
    }
    
    fileprivate func removeLine(_ line: UIView!) {
        guard let line = line else {
            return
        }
        line.removeFromSuperview()
    }

}
