package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;
import com.sun.glass.ui.SystemClipboard;

/**
 * Java_Chess_Engine: Created by mertkarakas on 10/8/2016.
 * https://en.wikipedia.org/wiki/Minimax - recursive min calls max && max calls min to propagate to the lowest value
 */
public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer() + " node depth = " + this.searchDepth);
        int numMoves = board.currentPlayer().getLegalMoves().size();

        for (final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()){
                //white: max player - black: min player | according to current player choose next max or min player
                currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getTransitionBoard(), this.searchDepth - 1) :
                        max(moveTransition.getTransitionBoard(), this.searchDepth - 1);
                if (board.currentPlayer().getAlliance().isWhite() && currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                    bestMove = move;
                }else if (board.currentPlayer().getAlliance().isBlack() && currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            }
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        return bestMove;
    }
    //finds lowest value at that depth/node
    public int min(final Board board, final int depth){
        if (depth == 0 /* game over check*/){
            return this.boardEvaluator.evaluate(board, depth);
        }
        //start w/highest number possible to go lower - no valid board can have this value
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestSeenValue){
                    //finds lowest value at that depth/node
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }
    private static boolean isEndGameScenario(final Board board){
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }

    //finds the highest value at that depth/node
    public int max(final Board board, final int depth){
        if (depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board, depth);
        }
        //start w/lowest number possible to go upper - no valid board can have a value lower that this
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= highestSeenValue){
                    //finds highest value at that depth/node
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }
}
