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
    
    let gameboardeasy2 = ["1--73----", "489-----1", "--6-4-295", "--75----6", "12-7-3-95", "6----87--", "914-2-8--", "6-----512", "----37--4"]
    let gameboardeasy2solution = ["152739468", "489256371", "376841295", "387591246", "124763895", "659428713", "914625873", "637948512", "582137964"]
    
    func getsolutions() -> Array<Array<String>> {
        return [gameboardeasy1solution, gameboardeasy2solution]
    }
    
    func getrandomboard(difficulty: String) -> Array<String> {
        let gameboardseasy = [gameboardeasy1, gameboardeasy2]
        if (difficulty == "easy") {
            if (gameboardseasy.count > 1) {
                let randomnumber = Int.random(in: 0..<gameboardseasy.count)
                return gameboardseasy[randomnumber]
            }
        }
        print("Something bad")
        //TODO create hard board
        return gameboardeasy1
    }
}
