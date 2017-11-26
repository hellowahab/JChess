package com.chess.engine.player.ai;

import com.chess.engine.board.Board;

/**
 * Java_Chess_Engine: Created by mertkarakas on 10/8/2016.
 */
public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
