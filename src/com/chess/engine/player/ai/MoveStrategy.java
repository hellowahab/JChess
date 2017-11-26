package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/**
 * Java_Chess_Engine: Created by mertkarakas on 10/8/2016.
 */
public interface MoveStrategy {

    Move execute(Board board);

}
