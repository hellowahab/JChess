package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Java_Chess_Engine: Created by mertkarakas on 9/2/2016.
 */
public abstract class Tile {

    protected final int tileCoordinate;
    protected static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    //TILE CONSTRUCTOR
    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();
    public int getTileCoordinate(){
        return this.tileCoordinate;
    }


    //EMPTY TILE

    public static final class EmptyTile extends Tile{
        //EMPTY TILE CONSTRUCTOR
        private EmptyTile(final int tileCoordinate){
            super(tileCoordinate);
        }
        //METHODS
        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccupied(){
            return false;
        }
        @Override
        public Piece getPiece() {
            return null;
        }
    }


    //OCCUPIED TILE

    public static final class OccupiedTile extends Tile{

        private final Piece pieceOnTile;

        //OCCUPIED TILE CONSTRUCTOR
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        //METHODS
        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                   getPiece().toString();
        }
        @Override
        public boolean isTileOccupied() {
            return true;
        }
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
