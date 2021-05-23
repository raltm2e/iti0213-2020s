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
    var leaderBoard: Array<String> = []
    
    func generateBoard(difficulty: String) -> Array<String> {
        //return gameBoardController.gameboardeasy1solution
        return gameBoardController.getrandomboard(difficulty: difficulty)
    }
    
    func checkIfSolved(board: Array<String>, difficulty: String, secondsSpent: Int) -> Bool {
        let solutions = gameBoardController.getsolutions()
        for solution in solutions {
            if solution == board {
                print("Solved")
                saveGameStatistics(difficulty: difficulty, secondsSpent: secondsSpent)
                return true
            }
        }
        return false
    }
    
    func saveGameStatistics(difficulty: String, secondsSpent: Int) {
        leaderBoard.append(String(secondsSpent))
    }
}
