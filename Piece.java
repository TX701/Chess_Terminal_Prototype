class Piece {
    String type;
    String name;
    char side;

    public Piece(String type, String name, char side) {
        this.type = type;
        this.name = name; 
        this.side = side;
    }

    // // converts a column from the array into a chess column (a letter)
    // public String getColChess(int col) {
    //     return String.valueOf((char) (col + 65));
    // }

    // // converts a column (which would be a letter) to its array equivalent (converts to an int and uses zero indexing) 
    // public int getCol(String location) {
    //     return location.charAt(0) - 65;
    // }

    // // converts a row into an array row (for the chess board the top of the board is 8 but for our array the top of the array is 0)
    // public int getRow(String location) {
    //     return 8 - Integer.parseInt(location.substring(1));
    // }

    // // converts a row into an array row (for the chess board the top of the board is 8 but for our array the top of the array is 0)
    // public int getRow() {
    //     return 8 - Integer.parseInt(location.substring(1));
    // }

    // public String getLocation(int col, int row) {
    //     return getColChess(col) + (8- row);
    // }

    // public String getLocation() {
    //     return location;
    // }

    // public void setCol(int col) {
    //     this.col = col;
    //     this.location = getLocation(col, row);
    // }

    // public void setrow(int row) {
    //     this.row = row;
    //     this.location = getLocation(col, row);
    // }

    // public void setLocation(String location) {
    //     this.location = location;
    //     this.col = getCol(location);
    //     this.row = getRow(location);
    // }

    // public Piece getPiece(int col, int row) {
    //     for (Piece piece : pieces) {
    //         if (piece.col == col && piece.row == row) {
    //             return piece;
    //         }
    //     }

    //     return null;
    // }

    // public void moveManager(Piece piece, Board board) {
    //     switch (piece.type) {
    //         case "Rook":
    //             break;
    //         case "knight":
    //             break;
    //         case "Bishop":
    //             break;
    //         case "Queen":
    //             break;
    //         case "King":
    //             break;
    //         case "Pawn":
    //             break;
    //         default:
    //             break;
    //     }
    // }
}