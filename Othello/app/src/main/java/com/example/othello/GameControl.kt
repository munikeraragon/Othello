package com.example.othello

import android.R
import android.graphics.Color
import java.util.*


class GameControl {
    var gameBoard = Array(8){Array<Cell>(8){Cell()} } // 8x8 board
    var turn = Color.BLACK

    var whiteCells = LinkedList<Cell>()
    var blackCells = LinkedList<Cell>()
    var highlightCells = LinkedList<Cell>()

    init{
        for(i in 0..7){
            for(d in 0..7){
                connectLeft(i,d)
                connectRight(i, d)
                connectUp(i, d)
                connectDown(i, d)

                connectTopRight(i, d)
                connectTopLeft(i, d)
                connectBottomRight(i, d)
                connectBottomLeft(i, d)
            }
        }


        gameBoard[3][3].setColor(Color.WHITE)
        whiteCells.add(gameBoard[3][3])
        gameBoard[3][3].isfilled = true

        gameBoard[4][4].setColor(Color.WHITE)
        whiteCells.add(gameBoard[4][4])
        gameBoard[4][4].isfilled = true

        gameBoard[3][4].setColor(Color.BLACK)
        blackCells.add(gameBoard[3][4])
        gameBoard[3][4].isfilled = true

        gameBoard[4][3].setColor(Color.BLACK)
        blackCells.add(gameBoard[4][3])
        gameBoard[4][3].isfilled = true


        highLight()
    }

    private fun connectBottomLeft(row: Int, col: Int) {
        if((row+1) < 8 && (col-1) > 0){
            gameBoard[row][col].bottomLeftCell = gameBoard[row+1][col-1]
        }
    }

    private fun connectBottomRight(row: Int, col: Int) {
        if((row+1) < 8 && (col+1) < 8){
            gameBoard[row][col].bottomRightCell = gameBoard[row+1][col+1]
        }
    }

    private fun connectTopLeft(row: Int, col: Int) {
        if((row-1) > 0 && (col-1) > 0){
            gameBoard[row][col].topLeftCell = gameBoard[row-1][col-1]
        }

    }

    private fun connectTopRight(row: Int, col: Int) {
        if((row-1) > 0 && (col+1) < 8){
            gameBoard[row][col].topRightCell = gameBoard[row-1][col+1]
        }

    }

    private fun connectDown(row: Int, col: Int) {
        if((row+1) < 8){
            gameBoard[row][col].downCell = gameBoard[row+1][col]
        }

    }

    private fun connectUp(row: Int, col: Int) {
        if((row-1) > 0){
            gameBoard[row][col].upCell = gameBoard[row-1][col]
        }
    }

    private fun connectRight(row: Int, col: Int) {
        if((col+1) < 8) {
            gameBoard[row][col].rightCell = gameBoard[row][col + 1]
        }
    }

    private fun connectLeft(row: Int, col: Int) {
        if((col-1) > 0){
            gameBoard[row][col].leftCell = gameBoard[row][col-1]
            print("connecting [row: %d col: %d] to [row: %d col: %d]\n".format(row,col,row,col-1))

        }

    }

    fun isActive(row: Int, col: Int): Boolean {
        return gameBoard[row][col].active
    }

    fun getColor(row: Int, col: Int): Int {
        return gameBoard[row][col].getColor()
    }

    private fun highLight(){
        var itr: Iterator<Cell>

        if(turn == Color.BLACK){
            itr = blackCells.iterator()
        }
        else{
            itr = whiteCells.iterator()
        }

        while(itr.hasNext()){
            itr.next().highlightCells(highlightCells)
        }
    }



    fun update(row: Int, col: Int) {
        // unhighlight cells
        var itr = highlightCells.iterator()
        while(itr.hasNext()){
            var cell = itr.next()
            cell.isfilled = false
            cell.active = false

        }

        gameBoard[row][col].captureCells(turn, blackCells, whiteCells)

        // set cell to be active
        gameBoard[row][col].setColor(turn)

        // change turn
        if(turn == Color.BLACK){
            // update black cells
            blackCells.add(gameBoard[row][col])
            turn = Color.WHITE
        }
        else{
            // update white cell
            whiteCells.add(gameBoard[row][col])
            turn = Color.BLACK
        }

        highlightCells.clear()
        highLight()
        println("List of black cells: " + blackCells.size)
        println("List of white cells: " + whiteCells.size)

    }


}



