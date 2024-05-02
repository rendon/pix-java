public class Pixel {
    
    private int px, py;
    private boolean status; //ON or OFF
    
    public Pixel(int x, int y)
    {
        px = x;
        py = y;
        status = false; // By default the pixel is off.
    }
    
    public void setX(int x) { px = x; }
    public void setY(int y) { py = y; }
    public int x() { return px; }
    public int y() { return py; }
    public void fill(boolean newStatus) { status = newStatus; }
    public boolean isFilled() { return status; }
}

