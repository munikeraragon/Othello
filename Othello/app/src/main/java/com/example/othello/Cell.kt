package com.example.othello

import android.graphics.Color
import java.util.*

class Cell {
    private var color = Color.RED
    public var active = false // is shaded or filled
    public var isfilled = false

    lateinit var leftCell: Cell
    lateinit var rightCell: Cell
    lateinit var upCell: Cell
    lateinit var downCell: Cell

    lateinit var topRightCell: Cell
    lateinit var topLeftCell: Cell
    lateinit var bottomRightCell: Cell
    lateinit var bottomLeftCell: Cell

    public fun setColor(c: Int) {
        color = c
        active = true
    }

    public fun getColor(): Int {
        return color
    }

    /* Highlights the cells that can be selected based on a corresponding cell */
    public fun highlightCells(list: LinkedList<Cell>) {
        // check adjacent cells
        checkLeft(list)
        checkRight(list)
        checkUp(list)
        checkDown(list)

        checkTopLeft(list)
        checkTopRight(list)
        checkBottomLeft(list)
        checkBottomRight(list)

    }

    public fun checkLeft(list: LinkedList<Cell>) {
        if (leftCell.isfilled && leftCell.color != color && !leftCell.leftCell.active) {
            leftCell.leftCell.setColor(Color.BLUE)
            list.add(leftCell.leftCell)
        }
    }

    public fun checkRight(list: LinkedList<Cell>) {
        if (rightCell.isfilled && rightCell.color != color && !rightCell.rightCell.active) {
            rightCell.rightCell.setColor(Color.BLUE)
            list.add(rightCell.rightCell)
        }
    }

    public fun checkUp(list: LinkedList<Cell>) {
        if (upCell.isfilled && upCell.color != color && !upCell.upCell.active) {
            upCell.upCell.setColor(Color.BLUE)
            list.add(upCell.upCell)
        }
    }

    public fun checkDown(list: LinkedList<Cell>) {
        if (downCell.isfilled && downCell.color != color && !downCell.downCell.active) {
            downCell.downCell.setColor(Color.BLUE)
            list.add(downCell.downCell)
        }
    }

    public fun checkTopRight(list: LinkedList<Cell>) {
        if (topRightCell.isfilled && topRightCell.color != color && !topRightCell.topRightCell.active) {
            topRightCell.topRightCell.setColor(Color.BLUE)
            list.add(topRightCell.topRightCell)
        }
    }

    public fun checkTopLeft(list: LinkedList<Cell>) {
        if (topLeftCell.isfilled && topLeftCell.color != color && !topLeftCell.topLeftCell.active) {
            topLeftCell.topLeftCell.setColor(Color.BLUE)
            list.add(topLeftCell.topLeftCell)
        }
    }

    public fun checkBottomRight(list: LinkedList<Cell>) {
        if (bottomRightCell.isfilled && bottomRightCell.color != color && !bottomRightCell.bottomRightCell.active) {
            bottomRightCell.bottomRightCell.setColor(Color.BLUE)
            list.add(bottomRightCell.bottomRightCell)
        }
    }

    public fun checkBottomLeft(list: LinkedList<Cell>) {
        if (bottomLeftCell.isfilled && bottomLeftCell.color != color && !bottomLeftCell.bottomLeftCell.active) {
            bottomLeftCell.bottomLeftCell.setColor(Color.BLUE)
            list.add(bottomLeftCell.bottomLeftCell)
        }
    }

    // check highlighted cell and capture cells that are adjacent
    // remove those cells from ---list and put in other list
    fun captureCells( turnColor: Int, blackCells: LinkedList<Cell>, whiteCells: LinkedList<Cell>) {
        // setup linked list for removing and inserting
        var outList: LinkedList<Cell>
        var inList: LinkedList<Cell>
        if(turnColor == Color.BLACK){
            outList = whiteCells
            inList = blackCells
        }
        else{
            outList = blackCells
            inList = whiteCells
        }

        when(true) {
            capture(leftCell, turnColor, inList, outList) -> return
            capture(rightCell, turnColor, inList, outList) -> return
            capture (upCell, turnColor, inList, outList) -> return
            capture(downCell, turnColor, inList, outList) -> return
            capture(topLeftCell, turnColor, inList, outList) -> return
            capture (topRightCell, turnColor, inList, outList) -> return
            capture(bottomLeftCell, turnColor, inList, outList) -> return
            capture(bottomRightCell, turnColor, inList, outList) -> return
        }
    }


    fun capture(cell: Cell, turnColor: Int, inList: LinkedList<Cell>, outList: LinkedList<Cell>): Boolean {
        if(cell.isfilled && cell.color != turnColor){
            outList.remove(cell)
            cell.color = turnColor
            inList.add(cell)
            return true
        }
        return false
    }
}