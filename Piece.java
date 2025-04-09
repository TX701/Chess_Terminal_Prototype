public class Piece {
    private String type;
    private String name;
    private final char side;

    public Piece(String type, String name, char side) {
        this.type = type;
        this.name = name; 
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSide() {
        return side;
    }
}