package packMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import packEntities.EntityDescriptor;
import packMap.Terrain.Plains;

public class HexagonalMap extends JPanel {
    private int columns = 2; // Number of columns
    private int rows = 2; // Number of rows
    private int hexSide = 50; // Side of the hexagon
    private int hexOffset; // Distance from left horizontal vertex to vertical axis
    private int hexApotheme; // Apotheme of the hexagon = radius of inscribed circumference
    private int hexRectWidth; // Width of the circumscribed rectangle
    private int hexRectHeight; // Height of the circumscribed rectangle
    private int hexGridWidth;  // hexOffset + hexSide (b + s)
    
    private Hex[][] map;
    private BufferedImage savedImage;

    public HexagonalMap()
    {
        CalculateHexValues();
        initiateMap();
        drawMap();
    }
    
    private void CalculateHexValues()
    {
        if(columns > 0 && rows > 0 && hexSide > 0)
        {
            hexApotheme = (int) (hexSide * Math.cos(Math.PI / 6));
            hexOffset = (int) (hexSide * Math.sin(Math.PI / 6));
            hexGridWidth = hexOffset + hexSide;
            hexRectWidth = 2 * hexOffset + hexSide;
            hexRectHeight = 2 * hexApotheme; 
            
        }
    }

    public final void initiateMap() 
    {
        map = new Hex[columns][rows];
        for (int column = 0; column < map.length; column++)
            for (int row = 0; row < map[0].length; row++)
                map[column][row] = new Plains(map, column,row);
    }
    private void drawEntities(Graphics g, Polygon hex, Hex hexObj)
    {
        int centerX = (int) hex.getBounds().getCenterX();
        int centerY = (int) hex.getBounds().getCenterY();
        
        if (hexObj.InitSlots() - hexObj.getFreeSlots() > 0) // if hex got any units
        {
            int iconOffset = 2; // distance between icons
            int slotLines = 2; // how many lines of slots
            int slotsPerLine = hexObj.InitSlots() / slotLines; // how many slots per line
            int unitWidth = (hexSide / slotsPerLine) - iconOffset * slotsPerLine;
            int unitHeight = (hexSide >> 1) - iconOffset * slotsPerLine;
            int unitSides = Math.min(unitWidth, unitHeight); // Since all images are by base 100 x 100 pixels

            int slot = 0;
            int line = 0;
            int iconLocX, iconLocY;
            for (EntityDescriptor i : hexObj.getEntities()) 
            {
                line = slot / slotsPerLine;
                BufferedImage icon = i.getIcon();
                //start of the drawing area + number of slot in line * how big pictures are + offset
                iconLocX = (centerX - hexOffset) + (slot % slotsPerLine) * unitSides + iconOffset;
                //start of the drawing area + numer of line * how big pictures are + offset
                iconLocY = centerY - (hexOffset) + line * unitSides + iconOffset;
                g.drawImage(icon, iconLocX, iconLocY, unitSides, unitSides, this);
                ++slot;
            }
        }
    }
    private void drawHex(Graphics g,int column, int row)
    {
        if(column < 0) return;
        if(row < 0) return;
        Polygon hex;
        hex = buildHexagon(column, row);
        Hex hexObj = map[column][row];
                
        //Draw hex itself
        g.setColor(hexObj.getBaseColor());
        //g.setColor(colorDispenser.CheckIfNeighbour(2, 2) ? Color.GREEN : Color.red); //test for neighbourhood
        g.fillPolygon(hex);
        //Draw borders between hexes
        Graphics2D tmp = (Graphics2D) g;
        tmp.setColor(hexObj.getBorderColor());
        tmp.setStroke(new BasicStroke(2));
        tmp.drawPolygon(hex);
        
        drawEntities(g, hex, hexObj);
        //Testing drawing area for units
        //g.setColor(Color.red);
        //g.fillRect(centerX - (hexOffset), centerY - (hexOffset), hexSide , hexSide);
        
    }
    private void drawHex(int column, int row)
    {
        Graphics savedImageInterface = savedImage.getGraphics();
        drawHex(savedImageInterface, column, row);
    }
    private void drawMap()
    {
        Graphics g = this.getGraphics();
        int tmpWidth = getWidth() <= 0 ? this.getPreferredSize().width : this.getWidth(); 
        int tmpHeight = getHeight() <= 0 ? this.getPreferredSize().height : this.getHeight(); 
        savedImage = new BufferedImage(tmpWidth, tmpHeight, BufferedImage.TYPE_INT_ARGB );
        Graphics savedImageInterface = savedImage.getGraphics();
        Polygon hex;
        for (int column = 0; column < map.length; column++)
            for (int row = 0; row < map[0].length; row++)
                drawHex(savedImageInterface, column, row);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        drawMap();
        g.drawImage(savedImage, 0, 0, this);
    }
    

    @Override
    public Dimension getPreferredSize() {
        int panelWidth = columns * hexGridWidth + hexOffset;
        int panelHeight = rows * hexRectHeight + hexApotheme + 1;
        return new Dimension(panelWidth, panelHeight);
    }

    public Polygon buildHexagon(int column, int row) {
        Polygon hex = new Polygon();
        Point origin = tileToPixel(column, row);
        hex.addPoint(origin.x + hexOffset, origin.y);
        hex.addPoint(origin.x + hexGridWidth, origin.y);
        hex.addPoint(origin.x + hexRectWidth, origin.y + hexApotheme);
        hex.addPoint(origin.x + hexGridWidth, origin.y + hexRectHeight);
        hex.addPoint(origin.x + hexOffset, origin.y + hexRectHeight);
        hex.addPoint(origin.x, origin.y + hexApotheme);
        return hex;
    }
    public static Polygon buildHexagon(int hexSide, Point origin , int column, int row) {
        int hexApotheme = (int) (hexSide * Math.cos(Math.PI / 6));
        int hexOffset = (int) (hexSide * Math.sin(Math.PI / 6));
        int hexGridWidth = hexOffset + hexSide;
        int hexRectWidth = 2 * hexOffset + hexSide;
        int hexRectHeight = 2 * hexApotheme;
        
        Polygon hex = new Polygon();
        hex.addPoint(origin.x + hexOffset, origin.y);
        hex.addPoint(origin.x + hexGridWidth, origin.y);
        hex.addPoint(origin.x + hexRectWidth, origin.y + hexApotheme);
        hex.addPoint(origin.x + hexGridWidth, origin.y + hexRectHeight);
        hex.addPoint(origin.x + hexOffset, origin.y + hexRectHeight);
        hex.addPoint(origin.x, origin.y + hexApotheme);
        return hex;
    }
    public static BufferedImage drawHexagon(int hexSide, Point origin , int column, int row, Hex hexObj) {
        Polygon hex = buildHexagon(hexSide, origin, column, row);
        Color base = hexObj.baseColor, border = hexObj.borderColor;
        BufferedImage img = new BufferedImage(100,100,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = img.createGraphics();
        g.setColor(base);
        g.fillPolygon(hex);
        g.setColor(border);
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(hex);
        return img;
    }

    public Point tileToPixel(int column, int row) {
        Point pixel = new Point();
        pixel.x = hexGridWidth * column;
        if (Util.isOdd(column)) pixel.y = hexRectHeight * row;
        else pixel.y = hexRectHeight * row + hexApotheme;
        return pixel;
    }

    public Point pixelToTile(int x, int y) {
        double hexRise = (double) hexApotheme / (double) hexOffset;
        Point p = new Point(x / hexGridWidth, y / hexRectHeight);
        Point r = new Point(x % hexGridWidth, y % hexRectHeight);
        Direction direction;
        if (Util.isOdd(p.x)) { //odd column
            if (r.y < -hexRise * r.x + hexApotheme) {
                direction = Direction.NW;
            } else if (r.y > hexRise * r.x + hexApotheme) {
                direction = Direction.SW;
            } else {
                direction = Direction.C;
            }
        } else { //even column
            if (r.y > hexRise * r.x && r.y < -hexRise * r.x + hexRectHeight) {
                direction = Direction.NW;
            } else if (r.y < hexApotheme) {
                direction = Direction.N;
            } else direction = Direction.C;
        }
        return new Point(direction.getNeighborCoordinates(p));
    }

    public boolean tileIsWithinBoard(Point coordinates) {
        int column = coordinates.x;
        int row = coordinates.y;
        return (column >= 0 && column < columns) && (row >= 0 && row < rows);
    }
    
    

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getHexSide() {
        return hexSide;
    }

    public int getHexOffset() {
        return hexOffset;
    }

    public int getHexApotheme() {
        return hexApotheme;
    }

    public int getHexRectWidth() {
        return hexRectWidth;
    }

    public int getHexRectHeight() {
        return hexRectHeight;
    }

    public int getHexGridWidth() {
        return hexGridWidth;
    }

    public void setColumns(int columns) {
        this.columns = columns;
        CalculateHexValues();
        resetMap();
    }

    public void setRows(int rows) {
        this.rows = rows;
        CalculateHexValues();
        resetMap();
    }
    
    public void setMapSize(int columns, int rows)
    {
        this.columns = columns;
        this.rows = rows;
        CalculateHexValues();
        resetMap();
    }

    public void setHexSide(int hexSide) {
        this.hexSide = hexSide;
        CalculateHexValues();
    }

    public Hex[][] getMap() {
        return map;
    }
    public Hex getHex(int Column, int Row)
    {
        return map[Column][Row];
    }
    public Hex getHex(Point coordinates)
    {
        return getHex(coordinates.x, coordinates.y);
    }

    public void setMap(Hex[][] map) {
        int mapColumns = map.length, mapRows = map[0].length;
        if(mapColumns > 0 && mapRows > 0 )
        {
             this.map = map;
             columns = mapColumns;
             rows = mapRows;
             redrawMap();
        }
    }
    public void setHex(Hex hex, int Column, int Row)
    {
        if(Column < 0 || Column >= map.length) return;
        if(Row < 0 || Row >= map[0].length) return;
        map[Column][Row] = hex;
        redrawMap();
    }
    public void setHex(Hex hex, Point coordinates)
    {
        setHex(hex, coordinates.x, coordinates.y);
    }
    public void resetMap()
    {
        initiateMap();
        redrawMap();
    }
    public void redrawMap()
    {
        drawMap();
        this.repaint();
    }
    
}