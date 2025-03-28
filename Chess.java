import java.util.List;

class Chess {
    public static void main(String[] args) { 
        Board board_manager = new Board();

        board_manager.printBoard();

        List<Tile> possibleMoves = board_manager.getLine(Board.board[6][0], 0, -1);
        
        for(int i=0;i<possibleMoves.size();i++){
            System.out.println(possibleMoves.get(i).location);
        } 
    

        // board_manager.pawnMovement("A2");
        // board_manager.pawnMovement("A7");

        //board_manager.move("A8", "C5");

        //board_manager.rookMovement("C5");
    }
}