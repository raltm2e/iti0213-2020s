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
    
    func generateBoard() -> Array<Any> {
        let gameboard1 = ["---68-19-", "26--7---4", "7-1-9-5--", "82---4-5-", "1--6-2--3", "-4-9---28", "--9-4-7-3", "3---5--18", "-74-36---"]
        let gameboards = [gameboard1]
        let randomnumber = Int.random(in: 0..<gameboards.count - 1)
        return gameboards[randomnumber]
    }
}
