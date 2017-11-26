package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

/**
 * Java_Chess_Engine: Created by mertkarakas on 9/4/2016.
 */
public class JChess {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
