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
    @IBOutlet weak var newGame: UIButton!
    @IBOutlet weak var undo: UIButton!
    var smallBoardList = [UIStackView]()
    
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
