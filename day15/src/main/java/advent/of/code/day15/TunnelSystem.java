package advent.of.code.day15;

import java.util.ArrayList;

public class TunnelSystem {
    private ArrayList<Sensor> sensors;
    private int[] deadSpotGrid;
    private int[] adjustment;

    public TunnelSystem(ArrayList<String> sensorStrings, int row) {
        sensors = Sensor.parseSensors(sensorStrings);
        // TODO: This loops over range 3 times, which is excessive.
        int[][] range = getSignalRangeArray(sensors);
        deadSpotGrid = createdeadSpotGrid(range);
        adjustment = getAdjustment(range);
        deadSpotGrid = filldeadSpotGrid(deadSpotGrid, sensors, range, adjustment, row);
    }

    // Get the range for where a beacon can't be for each sensor.
    private int[][] getSignalRangeArray(ArrayList<Sensor> sensors) {
        int[][] range = new int[sensors.size()][4];
        for (int i = 0; i < range.length; i++) {
            range[i] = sensors.get(i).getSignalRange();
        }
        return range;
    }

    // Create the deadSpotGrid of 0s to the size we need to fit all sensors and beacons.
    private int[] createdeadSpotGrid(int[][] range) {
        int minX = range[0][0], maxX = range[0][1], minY = range[0][2], maxY = range[0][3];
        for (int i = 1; i < range.length; i++) {
            minX = Math.min(range[i][0], minX);
            maxX = Math.max(range[i][1], maxX);
            minY = Math.min(range[i][2], minY);
            maxY  = Math.max(range[i][3], maxY);
        }
        return new int[maxY - minY + 1]; // + 1 to cover 0
    }

    // The beacons and/or range can be negative, so we need to adjust to match java indexing
    private int[] getAdjustment(int[][] range) {
        int minX = range[0][0], minY = range[0][2];
        for (int i = 1; i < range.length; i++) {
            minX = Math.min(range[i][0], minX);
            minY = Math.min(range[i][2], minY);
        }
        return new int[] {- minX, - minY};
    }

    // Fill in the deadSpotGrid with either a 1 (sensor), 2 (beacon), or 9 (cannot be beacon)
    private int[] filldeadSpotGrid(int[] deadSpotGrid, ArrayList<Sensor> sensors, int[][] range, int[] adjustment, int row) {
        for (int i = 0; i < range.length; i++) {
            Sensor sensor = sensors.get(i);
            // Fill in sensor if we care about that row.
            if (sensor.getSensorCoordinate().x() == row) {
                deadSpotGrid[sensor.getSensorCoordinate().y() + adjustment[1]] = 1;
            }
            // Fill in beacon if we care about that row.
            if (sensor.getBeaconCoordinate().x() == row) {
                deadSpotGrid[sensor.getBeaconCoordinate().y() + adjustment[1]] = 2;
            }
            int newY;
            for (int y = range[i][2]; y <= range[i][3]; y++) {
                newY = y + adjustment[1];
                if (deadSpotGrid[newY] == 0 && Sensor.getDistance(sensor.getSensorCoordinate(), new Coordinate(row, y)) <= sensor.getDistance()) {
                    deadSpotGrid[newY] = 9;
                }
            }
        }
        return deadSpotGrid;
    }

    public int countDeadSpots() {
        int deadSpots = 0;
        for (int i = 0; i < deadSpotGrid.length; i++) {
            if (deadSpotGrid[i] == 9) {
                deadSpots++;
            }
        }
        return deadSpots;
    }

    public int findDistressBeacon(int minVal, int maxVal) {
        for (int i = minVal; i <= maxVal; i++) {
            if (deadSpotGrid[i + adjustment[1]] == 0) {
                return i;
            }
        }
        return -1;
    }
}