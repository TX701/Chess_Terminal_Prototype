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
            case "Knight" -> kingMovement(tile);
            case "Bishop" -> bishopMovement(tile);
            case "Queen" -> queenMovement(tile);
            case "King" -> kingMovement(tile);
            case "Pawn" -> pawnMovement(tile);
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
            if (diagonalLeft.piece.side != tile_with_pawn.piece.side && diagonalLeft.piece.side != 'N') {
                possibleMoves.add(diagonalLeft);
            }
        }

        Tile diagonalRight = tileMath(tile_with_pawn, 1, rowMovement); // right with perspective of the board not the piece
        // if the diagonal right is not off the board and if the diagonal right is an emeny
        if (diagonalRight != null && diagonalRight.piece.type != null) {
            if (diagonalRight.piece.side != tile_with_pawn.piece.side && diagonalRight.piece.side != 'N') {
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

        boolean knight = (start.piece.type != null) ? (start.piece.type.equals("Knight")) : false;

        Tile newTile = tileMath(start, colInc, rowInc);
        while (newTile != null) {

            if (newTile.piece.type == null || knight) {
                line.add(newTile);
                newTile = tileMath(newTile, colInc, rowInc);
            } else if (newTile.piece.side != start.piece.side && newTile.piece.side != 'N') {
                line.add(newTile);
                return line;
            } else {
                return line;
            }
        }

        return line;
    }

    public List<Tile> getPlus(Tile start) {
        List<Tile> lines = new ArrayList<>();

        lines.addAll(getLine(start, 1, 0)); // go to the right
        lines.addAll(getLine(start, -1, 0)); // go to the left
        lines.addAll(getLine(start, 0, 1)); // go up
        lines.addAll(getLine(start, 0, -1)); // go down

        return lines;
    }

    public List<Tile> getCross(Tile start){
        List<Tile> lines = new ArrayList<>();

        lines.addAll(getLine(start, 1, 1)); // right up
        lines.addAll(getLine(start, 1, -1)); //  right down
        lines.addAll(getLine(start, -1, 1)); // left up
        lines.addAll(getLine(start, -1, -1)); // left down

        return lines;
    }

    // gets a square from the perspective of the location
    public List<Tile> getSquare(Tile piece, int length) {
        int start = 0 - (length/2);
        int end = 0 + (length/2) + 1;
        List<Tile> square = new ArrayList<>();
        for (int col = start; col < end; col++) {
            for (int row = start; row < end; row++) {
                Tile tile = tileMath(piece, col, row);
                if (tile != null && tile != piece) {
                    if (tile.piece == null || (tile.piece != null && (tile.piece.side) != piece.piece.side && tile.piece.side != 'N')) {
                        square.add(tile);
                    }
                }
            }
        }

        return square;
    }

    public List<Tile> rookMovement(Tile tile_with_rook) {
        List<Tile> possibleMoves = new ArrayList<>();

        possibleMoves.addAll(getPlus(tile_with_rook));

        return possibleMoves;
    }

    public List<Tile> knightMovement(Tile tile_with_knight) {
        List<Tile> possibleMoves = new ArrayList<>();

        List<Tile> unwanted = new ArrayList<>();

        unwanted.addAll(getPlus(tile_with_knight)); // gets all elements in a plus so we can remove them to create the knight circle
        unwanted.addAll(getCross(tile_with_knight)); // gets all elements in a cross so we can remove them to create the knight circle
        unwanted.addAll(getSquare(tile_with_knight, 3)); // we need to remove the inner square since we use the square function for the knight

        possibleMoves.addAll(getSquare(tile_with_knight, 5)); // uses the square function to get a 5 length square

        possibleMoves.removeAll(unwanted); // remove the cross plus and inner square to leave a circle

        return possibleMoves;
    }

    public List<Tile> bishopMovement(Tile tile_with_bishop) {
        List<Tile> possibleMoves = new ArrayList<>();

        possibleMoves.addAll(getCross(tile_with_bishop));

        return possibleMoves;
    }

    public List<Tile> queenMovement(Tile tile_with_queen) {
        List<Tile> possibleMoves = new ArrayList<>();

        // use the cross and plus together to get movement range
        possibleMoves.addAll(getPlus(tile_with_queen));
        possibleMoves.addAll(getCross(tile_with_queen));

        return possibleMoves;
    }

    public List<Tile> kingMovement(Tile tile_with_king) {
        List<Tile> possibleMoves = new ArrayList<>();

        possibleMoves.addAll(getSquare(tile_with_king, 3)); // get a 3 length square

        return possibleMoves;
    }

}
