class Piece {
    String type;
    String name;
    int side; // 0 for white (top) 1 black (bottom)
    int col;
    int row;

    Piece[] pieces = new Piece[32];

    public Piece() {}

    public Piece(String type, String name, int side, int col, int row) {
        this.type = type;
        this.name = name; // i wanted this to be the symbols but they dont print to my editors terminal- sorry
        this.side = side;
        this.col = col;
        this.row = row;
    }

    public void setrow(int col) {
        this.col = col;
    }

    public void setcol(int row) {
        this.row = row;
    }

    public void createPieces() {
        pieces[0] = new Piece("Rook", "BRo", 1, 0, 7);
        pieces[1] = new Piece("Knight", "BKn", 1, 1, 7);
        pieces[2] = new Piece("Bishop", "BBi", 1, 2, 7);
        pieces[3] = new Piece("Queen", "BQu", 1, 3, 7);
        pieces[4] = new Piece("King", "BKi", 1, 4, 7);
        pieces[5] = new Piece("Bishop", "BBi", 1, 5, 7);
        pieces[6] = new Piece("Knight", "BKn", 1, 6, 7);
        pieces[7] = new Piece("Rook", "BRo", 1, 7, 7);

        for (int i = 8; i < 16; i++) {
            pieces[i] = new Piece("Pawn", "BPa", 1, i - 8, 6);
            pieces[i + 8] = new Piece("Pawn", "WPa", 0, i - 8, 1);
        }

        pieces[24] = new Piece("Rook", "WRo", 0, 0, 0);
        pieces[25] = new Piece("Knight", "WKn", 0, 1, 0);
        pieces[26] = new Piece("Bishop", "WBi", 0, 2, 0);
        pieces[27] = new Piece("Queen", "WQu", 0, 3, 0);
        pieces[28] = new Piece("King", "WKi", 0, 4, 0);
        pieces[29] = new Piece("Bishop","WBi", 0, 5, 0);
        pieces[30] = new Piece("Knight", "WKn", 0, 6, 0);
        pieces[31] = new Piece("Rook", "WRo", 0, 7, 0);
    }

    public Piece getPiece(int col, int row) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].col == col && pieces[i].row == row) {
                return pieces[i];
            }
        }

        return null;
    }

    public void moveManager(Piece piece, Board board) {
        switch (piece.type) {
            case "Rook":
                break;
            case "knight":
                break;
            case "Bishop":
                break;
            case "Queen":
                break;
            case "King":
                break;
            case "Pawn":
                break;
            default:
                break;
        }
    }
}