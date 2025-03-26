public class Board {
    Piece[][] board = new Piece[8][8];
    Piece piece_manager = new Piece();

    public Board() { 
        piece_manager.createPieces();
        
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if (piece_manager.getPiece(j, i) != null) {
                    board[i][j] = piece_manager.getPiece(j, i);
                }
            }
        }
    }

    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            System.out.print(8 - i + "|   ");
            for(int j = 0; j < board.length; j++) {
                if ( board[i][j] != null) {
                    System.out.print(board[i][j].name + " ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        System.out.print("  ---------------------------");
        System.out.print("\n     A  B  C  D  E  F  G  H");
    }

    public Piece tileToRowCol(String location) {
        int col = location.charAt(0) - 65;
        int row = 7 - Integer.parseInt(location.substring(1));

        return board[row][col];
    }

}
