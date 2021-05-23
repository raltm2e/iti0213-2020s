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
    
    var timer = Timer()
    var secondsSpent = 0
    var gameActive = false
    var newGameStarted = false
    @IBOutlet weak var textSecondsSpent: UILabel!
    
    @IBOutlet weak var smallBoard1: UIStackView!
    @IBOutlet weak var smallBoard2: UIStackView!
    @IBOutlet weak var smallBoard3: UIStackView!
    @IBOutlet weak var smallBoard4: UIStackView!
    @IBOutlet weak var smallBoard5: UIStackView!
    @IBOutlet weak var smallBoard6: UIStackView!
    @IBOutlet weak var smallBoard7: UIStackView!
    @IBOutlet weak var smallBoard8: UIStackView!
    @IBOutlet weak var smallBoard9: UIStackView!
    @IBOutlet weak var textStatus: UILabel!
    
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
                            btn.setTitle("-", for: .normal)
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
        textStatus.text = "Started new game"
        gameActive = true
        newGameStarted = true
        secondsSpent = 0
    }
    
    @IBAction func checkSolved(_ sender: UIButton) {
        if checkSolved() {
            gameActive = false
            textStatus.text = "Solved!"
            
        } else {
            textStatus.text = "Not solved yet"
        }
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
        return game.checkIfSolved(board: boardvalues, difficulty: difficulty, secondsSpent: secondsSpent)
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
        scheduledTimerWithTimeInterval()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    func scheduledTimerWithTimeInterval(){
        // Scheduling timer to Call the function "updateCounting" with the interval of 1 seconds
        timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(ViewController.updateCounting), userInfo: nil, repeats: true)
    }
    
    @objc func updateCounting(){
        if gameActive {
            updateTextSeconds()
        }
    }
    
    func updateTextSeconds() {
        let timeArray = textSecondsSpent.text!.split{$0 == ":"}.map(String.init)
        var hours = Int(timeArray[0])!
        var minutes = Int(timeArray[1])!
        var seconds = Int(timeArray[2])!
        seconds += 1
        secondsSpent = seconds
        if seconds == 59 {
            seconds = 0
            minutes += 1
        }
        if minutes == 59 {
            minutes = 0
            hours += 1
        }
        if newGameStarted {
            seconds = 0
            minutes = 0
            hours = 0
            newGameStarted = false
        }
        var textSeconds = String(seconds)
        var textMinutes = String(minutes)
        var textHours = String(hours)
        if textSeconds.count == 1 {
            textSeconds = "0" + textSeconds
        }
        if textMinutes.count == 1 {
            textMinutes = "0" + textMinutes
        }
        if textHours.count == 1 {
            textHours = "0" + textHours
        }
        let timeText = textHours + ":" + textMinutes + ":" + textSeconds
        textSecondsSpent.text = timeText
    }
    
    func getNewGameBoard() -> Array<String> {
        appendSmallBoards()
        let gameBoard = self.game.generateBoard(difficulty: difficulty)
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
