//
//  ViewController.swift
//  sudoku3
//
//  Created by robert on 12/04/2021.
//  Copyright © 2021 robert. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    var game: SudokuBrain = SudokuBrain()
    var difficulty: String = "easy"

    @IBOutlet weak var smallBoard1: UIStackView!
    @IBOutlet weak var smallBoard2: UIStackView!
    @IBOutlet weak var smallBoard3: UIStackView!
    @IBOutlet weak var smallBoard4: UIStackView!
    @IBOutlet weak var smallBoard5: UIStackView!
    @IBOutlet weak var smallBoard6: UIStackView!
    @IBOutlet weak var smallBoard7: UIStackView!
    @IBOutlet weak var smallBoard8: UIStackView!
    @IBOutlet weak var smallBoard9: UIStackView!
    
    var smallBoardList = [UIStackView]()
    
    @IBAction func startNewGame(_ sender: UIButton) {
        print("Starting new game")
        appendSmallBoards()
        let newboard = getNewGameBoard()
        var j = 0
        for element in smallBoardList {
            var i = 0
            for view in element.subviews as [UIView] {
                for view2 in view.subviews as [UIView] {
                    if let btn = view2 as? UIButton {
                        let buttonvalue = newboard[j][i]
                        if buttonvalue == "-" {
                            btn.setTitle("", for: .normal)
                        } else {
                            btn.setTitle(String(buttonvalue), for: .normal)
                        }
                        btn.addTarget(self, action: #selector(ViewController.buttonClicked), for: .touchUpInside)
                        i += 1
                    }
                }
            }
            j += 1
        }
    }
    
    @IBAction func checkSolved(_ sender: UIButton) {
        checkSolved()
    }
    
    @IBAction func changeDifficulty(_ sender: UIButton) {
        if sender.titleLabel?.text == "easy" {
            sender.setTitle("hard", for: .normal)
            difficulty = "hard"
        } else {
            sender.setTitle("easy", for: .normal)
            difficulty = "easy"
        }
    }
    
    func checkSolved() -> Bool {
        appendSmallBoards()
        var boardvalues = [String]()
        for smallboard in smallBoardList {
            var smallstring = ""
            for view in smallboard.subviews as [UIView] {
                for view2 in view.subviews as [UIView] {
                    if let btn = view2 as? UIButton {
                        smallstring = smallstring + ((btn.titleLabel?.text!)!)
                    }
                }
            }
            boardvalues.append(smallstring)
        }
        return game.checkIfSolved(board: boardvalues)
    }
    
    @objc func buttonClicked(_ sender: UIButton) {
        if sender.titleLabel?.text == "9" {
            sender.setTitle("-", for: .normal)
        } else if ((sender.titleLabel?.text) == nil) || sender.titleLabel?.text == "-" {
            sender.setTitle("1", for: .normal)
        } else {
            var value: Int = Int(sender.titleLabel?.text! ?? "0")!
            value += 1
            sender.setTitle(String(value), for: .normal)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    func getNewGameBoard() -> Array<String> {
        appendSmallBoards()
        let gameBoard = game.generateBoard(difficulty: difficulty)
        return gameBoard
    }
    
    private func appendSmallBoards() {
        if (smallBoardList.count <= 0) {
            smallBoardList.append(smallBoard1)
            smallBoardList.append(smallBoard2)
            smallBoardList.append(smallBoard3)
            smallBoardList.append(smallBoard4)
            smallBoardList.append(smallBoard5)
            smallBoardList.append(smallBoard6)
            smallBoardList.append(smallBoard7)
            smallBoardList.append(smallBoard8)
            smallBoardList.append(smallBoard9)
        }
    }
    

}

extension String {
    subscript (index: Int) -> Character {
        let charIndex = self.index(self.startIndex, offsetBy: index)
        return self[charIndex]
    }
    
    subscript (range: Range<Int>) -> Substring {
        let startIndex = self.index(self.startIndex, offsetBy: range.startIndex)
        let stopIndex = self.index(self.startIndex, offsetBy: range.startIndex + range.count)
        return self[startIndex..<stopIndex]
    }
}
