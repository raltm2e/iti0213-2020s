//
//  GameBoard.swift
//  sudoku3
//
//  Created by robert on 10/05/2021.
//  Copyright © 2021 robert. All rights reserved.
//

import Foundation

class GameBoard {
    
    let gameboardeasy1 = ["---68-19-", "26--7---4", "7-1-9-5--", "82---4-5-", "1--6-2--3", "-4-9---28", "--9-4-7-3", "3---5--18", "-74-36---"]
    let gameboardeasy1solution = ["435682197", "269571834", "781493562", "826374951", "195682743", "347915628", "519248763", "326957418", "874136259"]
    //let gameboardseasy = [gameboardeasy1]
    
    func getsolutions() -> Array<Array<String>> {
        return [gameboardeasy1solution]
    }
    
    func getrandomboard(difficulty: String) -> Array<String> {
        """
if (difficulty == "easy") {
            if (gameboardseasy.count > 1) {
                let randomnumber = Int.random(in: 0..<gameboardseasy.count - 1)
                return gameboardseasy[randomnumber]
            }
            return gameboardeasy1
        }
"""
        //TODO create hard board
        return gameboardeasy1
    }
}
