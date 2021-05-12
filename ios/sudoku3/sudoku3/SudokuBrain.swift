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
    
    func generateBoard(difficulty: String) -> Array<String> {
        return gameBoardController.getrandomboard(difficulty: difficulty)
    }
    
    func checkIfSolved(board: Array<String>) -> Bool {
        let solutions = gameBoardController.getsolutions()
        for solution in solutions {
            if solution == board {
                print("Solved")
                return true
            }
        }
        print("Not solved")
        return false
    }
}
