import java.util.ArrayList;
import java.util.List;


public class Board {
    public Tile[][] board = new Tile[8][8];
    Tile tile_manager = new Tile();

    public Board() { }

    public void createBoard() {
        board = tile_manager.setupBoard(board);
    }

    public void printBoard() {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(8 - i + "|   ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j].getPiece().getName() + "   ");
                //System.out.print(board[i][j].getLocation() + "   ");
            }
            System.out.println("");
        }
        System.out.print("  ----------------------------------");
        System.out.println("\n     A   B   C   D   E   F   G   H\n\n");
    }

    public Tile tileMath(Tile start, int colDiff, int rowDiff) {
        int newCol = start.getCol() + colDiff;
        int newRow = start.getRow() + rowDiff;

        if (-1 < newCol && newCol < 8 && -1 < newRow && newRow < 8) {
            return board[newRow][newCol];
        }

        return null;
    }

    public Tile getTile(String location) {
        for (Tile[] row : board) {
            for (int col = 0; col < board.length; col++) {
                if (row[col].getLocation().equals(location)) {
                    return row[col];
                }
            }
        }

        return null;
    }

    public String standard(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase().trim();
    }

    public boolean contains(String type, String arr[]) {
        for (String value : arr) {
            if (value.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public void move(Tile start, Tile end) {
        Piece moving = start.getPiece();

        start.setPiece(new Piece(null, " ", 'N'));
        end.setPiece(moving);

        // something here to check if a pawn has reached the edge of the board

        printBoard();
    }

    public List<Tile> moveManager(Tile tile) {
        switch (tile.getPiece().getType()) {
            case "Rook" -> {
                return pawnMovement(tile);
            }
            case "Knight" -> {
                return knightMovement(tile);
            }
            case "Bishop" -> {
                return bishopMovement(tile);
            }
            case "Queen" -> {
                return queenMovement(tile);
            }
            case "King" -> {
                return kingMovement(tile);
            }
            case "Pawn" -> {
                return pawnMovement(tile);
            }
            default -> {
                System.err.println("err");
                return null;
            }
        }
    }

    // the messiest method for piece tracking
    public List<Tile> pawnMovement(Tile tile_with_pawn) {
        List<Tile> possibleMoves = new ArrayList<>();
        int rowMovement = (tile_with_pawn.getPiece().getSide() == 'W') ? -1 : (tile_with_pawn.getPiece().getSide() == 'B') ? 1 : 0;

        Tile diagonalLeft = tileMath(tile_with_pawn, -1, rowMovement); // left with perspective of the board not the piece
        // if the diagonal left is not off the board and if the diagonal left is an emeny
        if (diagonalLeft != null && diagonalLeft.getPiece() != null) {
            if (diagonalLeft.getPiece().getSide() != tile_with_pawn.getPiece().getSide() && diagonalLeft.getPiece().getSide() != 'N') {
                possibleMoves.add(diagonalLeft);
            }
        }

        Tile diagonalRight = tileMath(tile_with_pawn, 1, rowMovement); // right with perspective of the board not the piece
        // if the diagonal right is not off the board and if the diagonal right is an emeny
        if (diagonalRight != null && diagonalRight.getPiece().getType() != null) {
            if (diagonalRight.getPiece().getSide() != tile_with_pawn.getPiece().getSide() && diagonalRight.getPiece().getSide() != 'N') {
                possibleMoves.add(diagonalRight);
            }
        }

        Tile forward = tileMath(tile_with_pawn, 0, rowMovement);
        // checks for forward up one
        if (forward != null && forward.getPiece().getType() == null) {
            possibleMoves.add(forward);

            Tile forwardForward = tileMath(tile_with_pawn, 0, rowMovement * 2);
            // checks for forward up two
            if (forwardForward != null && forwardForward.getPiece().getType() == null) {
                if ((tile_with_pawn.getPiece().getSide() == 'W' && tile_with_pawn.getRow() == 6) || (tile_with_pawn.getPiece().getSide() == 'B' && tile_with_pawn.getRow() == 1)) {
                    possibleMoves.add(forwardForward);
                } 
            } 
        }

        return possibleMoves;
    }
    
    // tool for getting a line 
    public List<Tile> getLine(Tile start, int colInc, int rowInc) {
        List<Tile> line = new ArrayList<>();

        boolean knight = (start.getPiece().getType() != null) ? (start.getPiece().getType().equals("Knight")) : false;

        Tile newTile = tileMath(start, colInc, rowInc);
        while (newTile != null) {

            if (newTile.getPiece().getType() == null || knight) {
                line.add(newTile);
                newTile = tileMath(newTile, colInc, rowInc);
            } else if (newTile.getPiece().getSide() != start.getPiece().getSide() && newTile.getPiece().getSide() != 'N') {
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
                    square.add(tile); // we dont have to worry about collision with squares (ie we dont have to worry about removing pieces that are behind other pieces as this wont happen for a 3x3 square and we dont care about collision with the knight)
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

        possibleMoves.addAll(getSquare(tile_with_knight, 5)); // uses the square function to get a 5 length square

        unwanted.addAll(getPlus(tile_with_knight)); // gets all elements in a plus so we can remove them to create the knight circle
        unwanted.addAll(getCross(tile_with_knight)); // gets all elements in a cross so we can remove them to create the knight circle
        unwanted.addAll(getSquare(tile_with_knight, 3)); // we need to remove the inner square since we use the square function for the knight

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
