package com.example.othello

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import java.util.*

/*
 *  GameControl Module for Othello
 *  Author: Muniker Aragon
 */

class GameControl {
    var gameBoard = Array(8){Array<Cell>(8){Cell()} } // 8x8 board
    var turn = Color.BLACK

    var whiteCells = LinkedList<Cell>()
    var blackCells = LinkedList<Cell>()
    var highlightCells = LinkedList<Cell>()

    // connect Cells with one another
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

        // place initial pieces in board
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

    // check if a specific cell is active
    fun isActive(row: Int, col: Int): Boolean {
        return gameBoard[row][col].active
    }
    // get color for a specific cell
    fun getColor(row: Int, col: Int): Int {
        return gameBoard[row][col].getColor()
    }

    // display legal moves
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


    // update the board after the view has been clicked
    fun update(row: Int, col: Int, context: Context) {
        var cell = gameBoard[row][col]; // selected cell

        if(!highlightCells.contains(cell)){ // if Cell is not a legal move
            Toast.makeText(context, "ILLEGAL MOVE", Toast.LENGTH_LONG).show()
            return
        }

        // unhighlight the cell for the current turn
        var itr = highlightCells.iterator()
        while(itr.hasNext()){
            var cell = itr.next()
            cell.isfilled = false
            cell.active = false

        }

        // capture cells around the selected Cell
        cell.captureCells(turn, blackCells, whiteCells)
        // set cell to be active
        cell.setColor(turn)

        // change turn
        if(turn == Color.BLACK){
            // add to list
            blackCells.add(cell)
            turn = Color.WHITE
            Toast.makeText(context, "Player white TURN", Toast.LENGTH_LONG).show()
        }
        else{
            // add to list
            whiteCells.add(cell)
            turn = Color.BLACK
            Toast.makeText(context, "Player black TURN", Toast.LENGTH_LONG).show()
        }

        highlightCells.clear()
        highLight()

        if(highlightCells.size == 0){ // Game is over
            announceWinner(context)
            reset()
            return
        }

    }

    public fun reset() {
        // reset each cell in each of the lists
        emptyCells(whiteCells);
        emptyCells(blackCells)
        emptyCells(highlightCells)

        turn = Color.BLACK

        // set initial pieces in board
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

    // reset each cell in list and empty list
    private fun emptyCells(list: LinkedList<Cell>) {
        var itr = list.iterator()
        while(itr.hasNext()){
            var cell = itr.next()
            cell.active = false
            cell.isfilled = false
        }
        list.clear()
    }

    private fun announceWinner(context: Context) {
        if(blackCells.size > whiteCells.size){
            Toast.makeText(context, "Player Black Won", Toast.LENGTH_LONG).show()
        }
        else if(whiteCells.size > blackCells.size ){
            Toast.makeText(context, "Player White Won", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(context, "Tied", Toast.LENGTH_LONG).show()
        }

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
        }
    }
}



