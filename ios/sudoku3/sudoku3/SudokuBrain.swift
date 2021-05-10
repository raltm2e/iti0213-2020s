//
//  SudokuBrain.swift
//  sudoku3
//
//  Created by robert on 25/04/2021.
//  Copyright © 2021 robert. All rights reserved.
//

import Foundation

class SudokuBrain {
    var gameBoardController: GameBoard = GameBoard()
    var difficulty: String = "easy"
    
    func generateBoard() -> Array<String> {
        return gameBoardController.getrandomboard(difficulty: difficulty)
    }
}
