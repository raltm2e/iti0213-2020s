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
            var i = 1
            for view in element.subviews as [UIView] {
                for view2 in view.subviews as [UIView] {
                    if let btn = view2 as? UIButton {
                        // TODO var buttonvalue = newboard[j]
                        btn.setTitle(String(i), for: .normal)
                        i += 1
                    }
                }
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    func getNewGameBoard() -> Array<Any> {
        appendSmallBoards()
        let gameBoard = game.generateBoard()
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
