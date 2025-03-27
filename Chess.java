class Chess {
    public static void main(String[] args) { 
        Board board_manager = new Board();

        board_manager.printBoard();

        board_manager.move("D7", "H5");
        board_manager.move("C8", "D4");
        board_manager.move("C7", "H6");
        board_manager.move("E2", "E4");

        board_manager.move("B8", "D5");

        board_manager.pawnMovement("H2");

        // board_manager.pawnMovement("A2");
        // board_manager.pawnMovement("A7");

        //board_manager.move("A8", "C5");

        //board_manager.rookMovement("C5");
    }
}