import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AnalogDigitalWatch extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int CENTER_X = WIDTH / 2;
    private static final int CENTER_Y = HEIGHT / 2;
    private static final int HOUR_HAND_LENGTH = 80;
    private static final int MINUTE_HAND_LENGTH = 110;
    private static final int SECOND_HAND_LENGTH = 130;
    private static final int CLOCK_RADIUS = 150; // Circle size

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Get current time
        LocalTime time = LocalTime.now();
        int hours = time.getHour() % 12; // Convert to 12-hour format
        int minutes = time.getMinute();
        int seconds = time.getSecond();

        // Calculate angles
        double hourAngle = Math.toRadians(90 - (hours * 30 + minutes * 0.5));
        double minuteAngle = Math.toRadians(90 - (minutes * 6));
        double secondAngle = Math.toRadians(90 - (seconds * 6));

        // Draw clock border and hour markings
        drawClockBorder(g2);
        drawHourNumbers(g2);

        // Draw hands using numbers
        drawHand(g2, hourAngle, HOUR_HAND_LENGTH, String.valueOf(hours), new Color(0, 102, 204)); // Dark Blue
        drawHand(g2, minuteAngle, MINUTE_HAND_LENGTH, String.valueOf(minutes), new Color(50, 205, 50)); // Green
        drawHand(g2, secondAngle, SECOND_HAND_LENGTH, String.valueOf(seconds), Color.RED);
    }

    // Draws the clock border
    private void drawClockBorder(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(CENTER_X - CLOCK_RADIUS, CENTER_Y - CLOCK_RADIUS, CLOCK_RADIUS * 2, CLOCK_RADIUS * 2);
    }

    // Draws numbers (1-12) around the clock
    private void drawHourNumbers(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(90 - (i * 30));
            int x = (int) (CENTER_X + (CLOCK_RADIUS - 20) * Math.cos(angle));
            int y = (int) (CENTER_Y - (CLOCK_RADIUS - 20) * Math.sin(angle));
            g2.drawString(String.valueOf(i), x - 5, y + 5);
        }
    }

    // Draws hands using numbers
    private void drawHand(Graphics2D g2, double angle, int length, String digit, Color color) {
        g2.setColor(color);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        List<Point> points = getHandPoints(angle, length, 15);

        for (Point p : points) {
            g2.drawString(digit, p.x, p.y);
        }
    }

    // Calculates evenly spaced points along a hand's direction
    private List<Point> getHandPoints(double angle, int length, int step) {
        List<Point> points = new ArrayList<>();
        for (int i = 1; i <= length / step; i++) {
            int x = (int) (CENTER_X + i * step * Math.cos(angle));
            int y = (int) (CENTER_Y - i * step * Math.sin(angle));
            points.add(new Point(x, y));
        }
        return points;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Digital Watch");
        AnalogDigitalWatch clock = new AnalogDigitalWatch();
        frame.add(clock);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Timer to update clock every second
        Timer timer = new Timer(1000, e -> clock.repaint());
        timer.start();
    }
}
