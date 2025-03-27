import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    Piece[][] board = new Piece[8][8];
    Piece piece_manager = new Piece();

    public Board() {
        piece_manager.createPieces();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (piece_manager.getPiece(j, i) != null) {
                    board[i][j] = piece_manager.getPiece(j, i);
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(8 - i + "|   ");
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].name + " ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("");
        }
        System.out.print("  ----------------------------------");
        System.out.println("\n     A   B   C   D   E   F   G   H\n\n");
    }

    // converts a column (which would be a letter) to its array equivalent (converts to an int and uses zero indexing) 
    public int getCol(String location) {
        return location.charAt(0) - 65;
    }

    // converts a column from the array into a chess column (a letter)
    public String getColChess(int col) {
        return String.valueOf((char) (col + 65));
    }

    // converts a row into an array row (for the chess board the top of the board is 8 but for our array the top of the array is 0)
    public int getRow(String location) {
        return 8 - Integer.parseInt(location.substring(1));
    }

    // this function allows you to move around the board from the perspective of a piece
    // negatives go 'up' the board positives go 'down' the board
    public String tileMath(String start, int colDiff, int rowDiff) {
        int col = getCol(start) + colDiff;
        int row = Integer.parseInt(start.substring(1)) + rowDiff;

        if (-1 < col && col < 8 && -1 < row && row < 8) {
            return getColChess(col) + "" + row; // if the new tile is not out of bounds, return it
        }

        return null;
    }

    // returns what piece is on a specific tile
    public Piece tileToPiece(String location) {
        int col = getCol(location); // converts from chess notation to our array
        int row = getRow(location); // converts from chess notation to our array

        if (-1 < col && col < 8 && -1 < row && row < 8) {
            return board[row][col]; // if the new tile is not out of bounds, return it
        }

        return null;
    }

    // places a piece on a specified tile
    public void placePiece(String location, Piece piece) {
        int col = getCol(location); // converts from chess notation to our array
        int row = getRow(location); // converts from chess notation to our array

        board[row][col] = piece; // sets the row and column to be the piece (can be null)

        if (piece != null) {
            piece.setcol(col);
            piece.setrow(row);
        }
    }

    public void move(String start, String end) {
        placePiece(end, tileToPiece(start)); // move the piece to the specified location

        placePiece(start, null); // make the starting location empty
        printBoard(); // show user board
    }

    // this method will take a piece and run the method that matches with the pieces type
    // all of these methods return all possible moves that particular piece can take- the methods return lists due to their infinite size and ease of use
    public void moveManager(String piece) {
        switch (tileToPiece(piece).type) {
            case "Rook":
                pawnMovement(piece);
                break;
            case "Knight":
                kingMovement(piece);
                break;
            case "Bishop":
                bishopMovement(piece);
                break;
            case "Queen":
                queenMovement(piece);
                break;
            case "King":
                kingMovement(piece);
                break;
            case "Pawn":
                pawnMovement(piece);
                break;
            default:
                break;
        }
    }

    // the messiest method for piece tracking
    public List<String> pawnMovement(String pawnLocation) {
        List<String> possibleMoves = new ArrayList<>();
        int rowMovement = (tileToPiece(pawnLocation).side == 1) ? 1 : -1;

        String diagonalLeft = tileMath(pawnLocation, -1, rowMovement); // left with perspective of the board not the piece
        // if the diagonal left is not off the board and if the diagonal left is an emeny
        if (diagonalLeft != null && tileToPiece(diagonalLeft) != null) {
            if (tileToPiece(diagonalLeft).side != tileToPiece(pawnLocation).side) {
                possibleMoves.add(diagonalLeft);
            }
        }

        String diagonalRight = tileMath(pawnLocation, 1, rowMovement); // right with perspective of the board not the piece
        // if the diagonal right is not off the board and if the diagonal right is an emeny
        if (diagonalRight != null && tileToPiece(diagonalRight) != null) {
            if (tileToPiece(diagonalRight).side != tileToPiece(pawnLocation).side) {
                possibleMoves.add(diagonalRight);
            }
        }

        String move = tileMath(pawnLocation, 0, rowMovement);
        // checks for moving up one
        if (move != null && tileToPiece(move) == null) {
            possibleMoves.add(move);
        }

        String moveMove = tileMath(pawnLocation, 0, rowMovement * 2);
        // checks for moving up 2
        if (moveMove != null && tileToPiece(move) == null && tileToPiece(moveMove) == null) {
            if ((tileToPiece(pawnLocation).side == 0 && getRow(pawnLocation) == 1) || (tileToPiece(pawnLocation).side == 1 && getRow(pawnLocation) == 6)) {
                possibleMoves.add(moveMove);
            }
        }

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray())); // all of these methods print out the possible moves for testing purposes
        return possibleMoves;
    }

    // tool for getting a line 
    public void getLine(String location, List<String> list, int col, int row) {
        String tile = tileMath(location, col, row);

        int curCol = col;
        int curRow = row;

        // if the piece is within board range
        while (tile != null) {
            // we use this method as a helper for the knight but for the knight we need to not stop if we 'run into' a piece so we keep track of if the piece is a knight here
            boolean knight = (tileToPiece(location) != null) ? ((tileToPiece(location).type == "Knight") ? true : false) : false;

            // if the tile is empty or if we are collecting tiles for the knight
            if (tileToPiece(tile) == null || knight) {
                list.add(tile);

                curCol = curCol + col; // the col val will be -1 1 or 0 this method will either do nothing, increase by 1, or decrease by one
                curRow = curRow + row; // the row val will be -1 1 or 0 this method will either do nothing, increase by 1, or decrease by one

                tile = tileMath(location, curCol, curRow);
            // if the tile is occupied by an enemy
            } else if (tileToPiece(tile).side != tileToPiece(location).side) {
                list.add(tile); // add to the possible moves but stop collecting the line
                break;
            } else {
                break;
            }
        }
    }

    public void getPlus(String location, List<String> list) {
        getLine(location, list, 1, 0); // go to the right
        getLine(location, list, -1, 0); // go to the left
        getLine(location, list, 0, 1); // go up
        getLine(location, list, 0, -1); // go down
    }

    public void getCross(String location, List<String> list){
        getLine(location, list, 1, 1); // right up
        getLine(location, list, 1, -1); //  right down
        getLine(location, list, -1, 1); // left up
        getLine(location, list, -1, -1); // left down
    }

    // gets a square from the perspective of the location
    public void getSquare(String location, List<String> list, int start, int end) {
        for (int col = start; col < end; col++) {
            for (int row = start; row < end; row++) {
                String tile = tileMath(location, col, row);
                if (tile != null && tile != location) {
                    if (tileToPiece(tile) == null || (tileToPiece(tile) != null && (tileToPiece(tile).side) != tileToPiece(location).side)) {
                        list.add(tile);
                    }
                }
            }
        }
    }

    public List<String> rookMovement(String rookLocation) {
        List<String> possibleMoves = new ArrayList<>();

        getPlus(rookLocation, possibleMoves);

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

    public List<String> knightMovement(String knightLocation) {
        List<String> possibleMoves = new ArrayList<>();

        List<String> unwanted = new ArrayList<>();

        getPlus(knightLocation, unwanted); // gets all elements in a plus so we can remove them to create the knight circle
        getCross(knightLocation, unwanted); // gets all elements in a cross so we can remove them to create the knight circle
        getSquare(knightLocation, unwanted, -1, 2); // we need to remove the inner square since we use the square function for the knight

        getSquare(knightLocation, possibleMoves, -2, 3); // uses the square function to get a 5 length square

        possibleMoves.removeAll(unwanted); // remove the cross plus and inner square to leave a circle

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

    public List<String> bishopMovement(String bishopLocation) {
        List<String> possibleMoves = new ArrayList<>();

        getCross(bishopLocation, possibleMoves);

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

    public List<String> queenMovement(String queenLocation) {
        List<String> possibleMoves = new ArrayList<>();

        // use the cross and plus together to get movement range
        getPlus(queenLocation, possibleMoves);
        getCross(queenLocation, possibleMoves);

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

    public List<String> kingMovement(String kingLocation) {
        List<String> possibleMoves = new ArrayList<>();

        getSquare(kingLocation, possibleMoves, -1, 2); // get a 3 length square

        Collections.sort(possibleMoves);
        System.out.println(Arrays.toString(possibleMoves.toArray()));
        return possibleMoves;
    }

}
