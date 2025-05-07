
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import javax.swing.*;

class Screen extends JPanel {
    public boolean power = false; // robot power state
    public int x = -1, y = -1; // robot position
    private boolean isVisible = false; // robot visibility state
    private boolean heart=false; // heart mode
    private Color robotColor = new Color(100, 200, 100); // eco green

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(!power){
            this.setBackground(Color.GRAY);
        }else{        if (heart){
            this.setBackground(new Color(255, 192, 203)); // Pink background
            g2.setColor(Color.RED);
                if (x == -1 || y == -1) {
                    x = 100;
                    y = 100;
                }       

                // Draw the two top lobes of the heart (ovals)
                g2.fillOval(x, y, 20, 20);       // Left lobe
                g2.fillOval(x + 20, y, 20, 20);  // Right lobe

                // Draw the bottom triangle (point of the heart)
                int[] xPoints = { x, x + 20, x + 40 };
                int[] yPoints = { y + 15, y + 45, y + 15 };
                g2.fillPolygon(xPoints, yPoints, 3);

                // Optional: black eyes as little dots inside the heart
                g2.setColor(Color.BLACK);
                g2.fillOval(x + 6, y + 8, 4, 4);
                g2.fillOval(x + 30, y + 8, 4, 4);

                // Optional: cute antenna on top
                g2.drawLine(x + 20, y, x + 20, y - 10);
                g2.fillOval(x + 17, y - 13, 6, 6);   
                }
                else{
                    this.setBackground(new Color(162,228,184)); // Pink background
                    g2.setColor(robotColor);
                    if (x == -1 || y == -1) {
                         x = 625; // Center the robot if not set (625, 260)
                         y = 260;
                    }
                g2.fillRect(x, y, 40, 40); // robot body
                if (this.isVisible) {
                    drawPlantPackage(g, x, y);
            
                    
                    
                }
  
          // Eyes
          g2.setColor(Color.BLACK);
          g2.fillOval(x + 8, y + 10, 6, 6);
          g2.fillOval(x + 26, y + 10, 6, 6);
  
          // Optional: antenna
          g2.drawLine(x + 20, y, x + 20, y - 10);
          g2.fillOval(x + 17, y - 13, 6, 6);
        }
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Node positions (centered layout)
        Map<String, Point> positions = new HashMap<>();
        positions.put("New York", new Point(centerX - 300, centerY));         // A
        positions.put("Tokyo", new Point(centerX - 150, centerY - 100));      // B
        positions.put("Manilla", new Point(centerX - 150, centerY + 100));    // C
        positions.put("Cairo", new Point(centerX, centerY + 100));            // E
        positions.put("Paris", new Point(centerX + 150, centerY));            // D
        positions.put("Tunis", new Point(centerX + 300, centerY));            // F

        // Draw arrows
        drawArrow(g2, positions.get("New York"), positions.get("Tokyo"), "4.8");
        drawArrow(g2, positions.get("New York"), positions.get("Manilla"), "2");
        drawArrow(g2, positions.get("Tokyo"), positions.get("Paris"), "10.12");
        drawArrow(g2, positions.get("Tokyo"), positions.get("Manilla"), "5");
        drawArrow(g2, positions.get("Manilla"), positions.get("Cairo"), "3.58");
        drawArrow(g2, positions.get("Cairo"), positions.get("Paris"), "4");
        drawArrow(g2, positions.get("Paris"), positions.get("Tunis"), "11");

        // Draw nodes
        for (Map.Entry<String, Point> entry : positions.entrySet()) {
            String label = entry.getKey();
            Point p = entry.getValue();
            g2.setColor(Color.WHITE);
            g2.fillRect(p.x - 30, p.y - 15, 60, 30);
            g2.setColor(Color.BLACK);
            g2.drawRect(p.x - 30, p.y - 15, 60, 30);
            g2.drawString(label, p.x - label.length() * 3, p.y + 5);
        }}

    }

    private void drawArrow(Graphics2D g2, Point from, Point to, String weight) {
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2));

        int dx = to.x - from.x, dy = to.y - from.y;
        double angle = Math.atan2(dy, dx);
        int len = (int) Point.distance(from.x, from.y, to.x, to.y);

        AffineTransform old = g2.getTransform();
        g2.translate(from.x, from.y);
        g2.rotate(angle);

        g2.drawLine(0, 0, len, 0);
        g2.fillPolygon(new int[]{len, len - 10, len - 10}, new int[]{0, -5, 5}, 3);

        g2.setTransform(old);
        g2.drawString(weight, (from.x + to.x) / 2, (from.y + to.y) / 2 - 5);
    }
    // You can expose a method to move the robot later
    public void setPosition(Point p) {
        this.x = p.x;
        this.y = p.y;
        repaint();
    }
    public void deplacer(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        repaint();
    }
    public void setHeart() {
        heart=!heart;
        repaint();
    }
    public void powerUP(){
        power=!power;
        repaint();
    }
    public void setVisible(boolean visible) {
        this.isVisible =visible;
        repaint();
    }
    public void drawPlantPackage(Graphics g, int robotX, int robotY) {
        // Adjusted to be small and centered above robot
        int plantX = robotX + 20;  // Centered horizontally
        int plantY = robotY - 20;  // Slightly above robot
    
        // Stem
        g.setColor(new Color(0, 100, 0));
        g.fillRect(plantX + 5, plantY, 2, 10); // thin stem
    
        // Leaves
        g.setColor(new Color(0, 150, 0));
        g.fillOval(plantX, plantY - 5, 8, 5);
        g.fillOval(plantX + 6, plantY - 3, 8, 5);
    
        // Flower center
        g.setColor(Color.YELLOW);
        g.fillOval(plantX + 3, plantY - 10, 5, 5);
    
        // Petals (very small)
        g.setColor(Color.PINK);
        g.fillOval(plantX + 3, plantY - 14, 5, 5); 
        g.fillOval(plantX + 3, plantY - 6, 5, 5); 
        g.fillOval(plantX - 1, plantY - 10, 5, 5); 
        g.fillOval(plantX + 7, plantY - 10, 5, 5); 
    }
}
