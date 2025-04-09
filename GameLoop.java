
import java.util.List;
import java.util.Scanner;

public class GameLoop {
    static Board board_manager = new Board();

    Scanner in = new Scanner(System.in);
    String scan;           


    public void gameLoop() {
        while (true) {
            board_manager.printBoard();

            Tile start = moveStart();
            List<Tile> possibleMoves = board_manager.moveManager(start);
    
            while (possibleMoves.isEmpty()) {
                System.out.println("There are no moves you can take from this tile");
                start = moveStart();
                possibleMoves = board_manager.moveManager(start);
            }
    
            System.out.println("You may move to: ");
            for (Tile move : possibleMoves) {
                System.out.print(move.getLocation() + " ");
            }
    
            System.out.println("\nWhere would you like to move (press Q to reselect starting piece)");
            String end = getLocationInput();

            if (end.equals("Q")) {
                continue;
            }
            
            while (!inList(possibleMoves, end)) {
                System.out.println("\nInvalid move");
                end = getLocationInput();
            }
    
            board_manager.move(start, board_manager.getTile(end));
        }
    }

    public boolean inList(List<Tile> possibleMoves, String location) {
        for (Tile moves : possibleMoves) {
            if (moves.getLocation().equals(location)) {
                return true;
            }
        }

        return false;
    }

    public Tile moveStart() {
        Tile start = null;

        while (start == null || start.getPiece().getType() == null) {
            start = getPieceToMove();

            if (start == null) {
                System.err.println("Invalid tile\n");
            } else if (start.getPiece().getType() == null) {
                System.err.println("There is no piece at that tile\n");
            }
        }

        System.out.println(start.getPiece().getType());

       return start;
    }

    public Tile getPieceToMove() {
        System.out.println("What piece would you like to move? (Enter its location)");
        return board_manager.getTile(getLocationInput());
    }

    public String getLocationInput() {
        scan = in.nextLine();
        return scan.trim().toUpperCase();
    }
}
