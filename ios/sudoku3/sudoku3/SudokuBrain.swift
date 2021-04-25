//
//  SudokuBrain.swift
//  sudoku3
//
//  Created by robert on 25/04/2021.
//  Copyright © 2021 robert. All rights reserved.
//

import Foundation

class SudokuBrain {
    private var gameBoard: [[Tile?]] = Array(repeating: Array(repeating: nil, count: 3), count: 3)
    
    func getTile(columnNo col: Int, rowNo row: Int) -> Tile? {
        return gameBoard[col][row]
    }
    
    func makeMove(columnNo col: Int, rowNo row: Int) {
        //var x = getTile(columnNo: col, rowNo: row)
        // Change value of tile, +1
        
    }
}
