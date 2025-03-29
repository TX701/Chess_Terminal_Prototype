class Tile {
    int col;
    int row;
    String location;
    Piece piece;

    Tile[] starting_tiles = new Tile[32];

    public Tile () { }

    public Tile (int col, int row, String location, Piece piece) {
        this.col = col;
        this.row = row;
        this.location = location;
        this.piece = piece;
    }

    public String getColChess(int col) {
        return String.valueOf((char) (col + 65));
    }

    public String getLocation() {
        return location;
    }

    public String getLocation(int col, int row) {
        return getColChess(col) + (8 - row); 
    }

    public void tileSetUp() {
        starting_tiles[0] = new Tile(0, 0, "A8", new Piece("Rook", "♜", 'B'));
        starting_tiles[1] = new Tile(1, 0, "B8", new Piece("Knight", "♞", 'B'));
        starting_tiles[2] = new Tile(2, 0, "C8", new Piece("Bishop", "♝", 'B'));
        starting_tiles[3] = new Tile(3, 0, "D8", new Piece("Queen", "♛", 'B'));
        starting_tiles[4] = new Tile(4, 0, "E8", new Piece("King", "♚", 'B'));
        starting_tiles[5] = new Tile(5, 0, "F8", new Piece("Bishop", "♝", 'B'));
        starting_tiles[6] = new Tile(6, 0, "G8", new Piece("Knight", "♞", 'B'));
        starting_tiles[7] = new Tile(7, 0, "H8", new Piece("Rook", "♜", 'B'));

        for (int i = 8; i < 16; i++) {
            starting_tiles[i] = new Tile(i - 8, 1, getLocation(i - 8, 6), new Piece("Pawn", "♟", 'B'));
            starting_tiles[i + 8] = new Tile(i - 8, 6, getLocation(i - 8, 1), new Piece("Pawn", "♙", 'W')); 
        }

        starting_tiles[24] = new Tile(0, 7, "A1", new Piece("Rook", "♖", 'W'));
        starting_tiles[25] = new Tile(1, 7, "B1", new Piece("Knight", "♘", 'W'));
        starting_tiles[26] = new Tile(2, 7, "C1", new Piece("Bishop", "♗", 'W'));
        starting_tiles[27] = new Tile(3, 7, "D1", new Piece("Queen", "♕", 'W'));
        starting_tiles[28] = new Tile(4, 7, "E1", new Piece("King", "♔", 'W'));
        starting_tiles[29] = new Tile(5, 7, "F1", new Piece("Bishop","♗", 'W'));
        starting_tiles[30] = new Tile(6, 7, "G1", new Piece("Knight", "♘", 'W'));
        starting_tiles[31] = new Tile(7, 7, "H1", new Piece("Rook", "♖", 'W'));
    }

    public Tile tileFromArray(int col, int row) {
        for (Tile starting_tile : starting_tiles) {
            if (starting_tile.col == col && starting_tile.row == row) {
                return starting_tile;
            }
        }

        return null;
    }

    public Tile[][] setupBoard(Tile[][] board) {
        tileSetUp();

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (tileFromArray(c, r) != null) {
                    board[r][c] = tileFromArray(c, r);
                } else {
                    board[r][c] = new Tile(c, r, getLocation(c, r), new Piece(null, "    ", 'N'));
                }
            }
        }
        
        return board;
    }


}