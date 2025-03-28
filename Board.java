import java.util.ArrayList;
import java.util.List;


public class Board {
    public static Tile[][] board = new Tile[8][8];
    Tile tile_manager = new Tile();

    public Board() {
        board = tile_manager.setupBoard(board);
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(8 - i + "|   ");
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].piece.name + " ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("");
        }
        System.out.print("  ----------------------------------");
        System.out.println("\n     A   B   C   D   E   F   G   H\n\n");
    }

    public Tile tileMath(Tile start, int colDiff, int rowDiff) {
        int newCol = start.col + colDiff;
        int newRow = start.row + rowDiff;

        if (-1 < newCol && newCol < 8 && -1 < newRow && newRow < 8) {
            return board[newRow][newCol];
        }

        return null;
    }

    public void moveManager(Tile tile) {
        switch (tile.piece.type) {
            case "Rook" -> pawnMovement(tile);
            //case "Knight" -> kingMovement(tile.piece);
            //case "Bishop" -> bishopMovement(tile.piece);
            //case "Queen" -> queenMovement(tile.piece);
            //case "King" -> kingMovement(tile.piece);
            //case "Pawn" -> pawnMovement(tile.piece);
            default -> {
            }
        }
    }

    // the messiest method for piece tracking
    public List<Tile> pawnMovement(Tile tile_with_pawn) {
        List<Tile> possibleMoves = new ArrayList<>();
        int rowMovement = (tile_with_pawn.piece.side == 'W') ? 1 : -1;

        Tile diagonalLeft = tileMath(tile_with_pawn, -1, rowMovement); // left with perspective of the board not the piece
        // if the diagonal left is not off the board and if the diagonal left is an emeny
        if (diagonalLeft != null && diagonalLeft.piece != null) {
            if (diagonalLeft.piece.side != tile_with_pawn.piece.side) {
                possibleMoves.add(diagonalLeft);
            }
        }

        Tile diagonalRight = tileMath(tile_with_pawn, 1, rowMovement); // right with perspective of the board not the piece
        // if the diagonal right is not off the board and if the diagonal right is an emeny
        if (diagonalRight != null && diagonalRight.piece.type != null) {
            if (diagonalRight.piece.side != tile_with_pawn.piece.side) {
                possibleMoves.add(diagonalRight);
            }
        }

        Tile forward = tileMath(tile_with_pawn, 0, rowMovement);
        // checks for moving up one
        if (forward != null && forward.piece.type == null) {
            possibleMoves.add(forward);

            Tile forwardForward = tileMath(tile_with_pawn, 0, rowMovement * 2);
            if (forwardForward != null && forwardForward.piece.type == null) {
                if ((tile_with_pawn.piece.side == 'W' && tile_with_pawn.row == 1) || (tile_with_pawn.piece.side == 'B' && tile_with_pawn.row == 6)) {
                    possibleMoves.add(forwardForward);
                } 
            } 
        }

        return possibleMoves;
    }

    // tool for getting a line 
    public List<Tile> getLine(Tile start, int colInc, int rowInc) {
        List<Tile> line = new ArrayList<>();

        int curCol = colInc;
        int curRow = rowInc;

        int inc = 1;
        while (tileMath(start, 0, inc) != null) {
            
            System.out.println(tileMath(start, 0, inc).location);
            inc--;
            
        }
        

        // // if the piece is within board range
        // while (nextTile != null) {
        //     System.out.println(curCol + " " + curRow);
        //     // we use this method as a helper for the knight but for the knight we need to not stop if we 'run into' a piece so we keep track of if the piece is a knight here
        //     boolean knight = (nextTile.piece.type != null) ? (nextTile.piece.type.equals("Knight")) : false;

        //     // if the tile is empty or if we are collecting tiles for the knight
        //     if (nextTile.piece.type == null || knight) {
        //         line.add(nextTile);

        //         curCol = curCol + colInc; // the col val will be -1 1 or 0 this method will either do nothing, increase by 1, or decrease by one
        //         curRow = curRow + rowInc; // the row val will be -1 1 or 0 this method will either do nothing, increase by 1, or decrease by one

        //         nextTile = tileMath(nextTile, curCol, curRow);
        //     // if the tile is occupied by an enemy
        //     } else if ((nextTile.piece.side == 'W' && start.piece.side == 'B') || (nextTile.piece.side == 'B' && start.piece.side == 'W')) {
        //         line.add(nextTile); // add to the possible moves but stop collecting the line
        //         break;
        //     } else {
        //         break;
        //     }
        // }

        return line;
    }

    // public void getPlus(String location, List<String> list) {
    //     getLine(location, list, 1, 0); // go to the right
    //     getLine(location, list, -1, 0); // go to the left
    //     getLine(location, list, 0, 1); // go up
    //     getLine(location, list, 0, -1); // go down
    // }

    // public void getCross(String location, List<String> list){
    //     getLine(location, list, 1, 1); // right up
    //     getLine(location, list, 1, -1); //  right down
    //     getLine(location, list, -1, 1); // left up
    //     getLine(location, list, -1, -1); // left down
    // }

    // // gets a square from the perspective of the location
    // public void getSquare(String location, List<String> list, int start, int end) {
    //     for (int col = start; col < end; col++) {
    //         for (int row = start; row < end; row++) {
    //             String tile = tileMath(location, col, row);
    //             if (tile != null && tile != location) {
    //                 if (tileToPiece(tile) == null || (tileToPiece(tile) != null && (tileToPiece(tile).side) != tileToPiece(location).side)) {
    //                     list.add(tile);
    //                 }
    //             }
    //         }
    //     }
    // }

    // public List<String> rookMovement(Piece rook) {
    //     List<String> possibleMoves = new ArrayList<>();

    //     getPlus(rookLocation, possibleMoves);

    //     Collections.sort(possibleMoves);
    //     System.out.println(Arrays.toString(possibleMoves.toArray()));
    //     return possibleMoves;
    // }

    // public List<String> knightMovement(String knightLocation) {
    //     List<String> possibleMoves = new ArrayList<>();

    //     List<String> unwanted = new ArrayList<>();

    //     getPlus(knightLocation, unwanted); // gets all elements in a plus so we can remove them to create the knight circle
    //     getCross(knightLocation, unwanted); // gets all elements in a cross so we can remove them to create the knight circle
    //     getSquare(knightLocation, unwanted, -1, 2); // we need to remove the inner square since we use the square function for the knight

    //     getSquare(knightLocation, possibleMoves, -2, 3); // uses the square function to get a 5 length square

    //     possibleMoves.removeAll(unwanted); // remove the cross plus and inner square to leave a circle

    //     Collections.sort(possibleMoves);
    //     System.out.println(Arrays.toString(possibleMoves.toArray()));
    //     return possibleMoves;
    // }

    // public List<String> bishopMovement(Piece bishop) {
    //     List<String> possibleMoves = new ArrayList<>();

    //     getCross(bishopLocation, possibleMoves);

    //     Collections.sort(possibleMoves);
    //     System.out.println(Arrays.toString(possibleMoves.toArray()));
    //     return possibleMoves;
    // }

    // public List<String> queenMovement(Piece queen) {
    //     List<String> possibleMoves = new ArrayList<>();

    //     // use the cross and plus together to get movement range
    //     getPlus(queenLocation, possibleMoves);
    //     getCross(queenLocation, possibleMoves);

    //     Collections.sort(possibleMoves);
    //     System.out.println(Arrays.toString(possibleMoves.toArray()));
    //     return possibleMoves;
    // }

    // public List<String> kingMovement(Piece king) {
    //     List<String> possibleMoves = new ArrayList<>();

    //     getSquare(kingLocation, possibleMoves, -1, 2); // get a 3 length square

    //     Collections.sort(possibleMoves);
    //     System.out.println(Arrays.toString(possibleMoves.toArray()));
    //     return possibleMoves;
    // }

}
