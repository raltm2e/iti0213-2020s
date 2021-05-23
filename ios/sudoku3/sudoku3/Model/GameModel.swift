//
//  GameModel.swift
//  sudoku3
//
//  Created by robert on 23/05/2021.
//  Copyright © 2021 robert. All rights reserved.
//

import Foundation

class Game {
    var difficulty: String
    var secondsSpent: Int
    var playerName: String
    
    init(difficulty: String, secondsSpent: Int, playerName: String) {
        self.difficulty = difficulty
        self.secondsSpent = secondsSpent
        self.playerName = playerName
    }
    
    func toString() {
        print("playername: " + playerName + ", secondsspent: " + String(secondsSpent) + ", difficulty: " + difficulty)
    }
}
