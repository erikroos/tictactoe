package tictactoe;

public class Helper {
    public static int coordsToMove(int x, int y, int rowLength) {
        return y * rowLength + x;
    }

    public static int[] moveToCoords(int move, int rowLength) {
        int[] coords = new int[2];
        coords[0] = move % rowLength; // x
        coords[1] = move / rowLength; // y
        return coords;
    }
}
