package advent.of.code.day15;

import java.util.PriorityQueue;
import java.util.ArrayList;

class TunnelSystem {
    private ArrayList<Sensor> sensors;
    private int[] deadSpotGrid;
    private int[][] range;

    TunnelSystem(ArrayList<String> sensorStrings) {
        sensors = Sensor.parseSensors(sensorStrings);
        range = getSignalRangeArray();
    }

    // Get the range for where a beacon can't be for each sensor.
    private int[][] getSignalRangeArray() {
        int[][] range = new int[sensors.size()][4];
        for (int i = 0; i < range.length; i++) {
            range[i] = sensors.get(i).getSignalRange();
        }
        return range;
    }

    // Create the deadSpotGrid of 0s to the size we need to fit all sensors and beacons.
    private int[] createDeadSpotGrid() {
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
    private int[] getAdjustment() {
        int minX = range[0][0], minY = range[0][2];
        for (int i = 1; i < range.length; i++) {
            minX = Math.min(range[i][0], minX);
            minY = Math.min(range[i][2], minY);
        }
        return new int[] {- minX, - minY};
    }

    // Fill in the deadSpotGrid with either a 1 (sensor), 2 (beacon), or 9 (cannot be beacon)
    void fillDeadSpotGrid(int row) {
        deadSpotGrid = createDeadSpotGrid();
        int[] adjustment = getAdjustment();
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
    }

    long getSpotWhereBeaconCantBe(int maxSize) {
        // Step 1: Create ArrayList<PriorityQueue<Interval?>> and prepopulate it with 4.000.000 PriorityQueue<Interval>s
        // TODO: I'm just going to use Coordinate for now since it's all I need
        var intervals = new ArrayList<PriorityQueue<Coordinate>>(maxSize + 1);
        for (int i = 0; i <= maxSize; i++) {
            intervals.add(new PriorityQueue<Coordinate>());
        }

        // Step 2: For each range, create a diamond and add the intervals to the LinkedList
        //      range[0] is like [0, 10, 5, 15], so you add <5> to 0, <4, 6> to 1, <3, 7> to 2, ..., <0, 15> to 5, ..., <5> to 10
        for (int i = 0; i < range.length; i++) {
            int minX = range[i][0];
            int maxX = range[i][1];
            int minY = range[i][2];
            int maxY = range[i][3];
            Coordinate sensorCoordinate = sensors.get(i).getSensorCoordinate();
            int sensorX = sensorCoordinate.x();
            int sensorY = sensorCoordinate.y();

            // Prevent adding the middle element twice
            if (sensorX == 0) {
                System.out.println("minX " + minX + " maxX " + maxX + " minY " + minY + " maxY " + maxY);
            }
            intervals.get(sensorX).offer(new Coordinate(minY, maxY));
            // TODO: Does it have to be even?
            for (int j = 1; j <= (maxX - minX) / 2; j++) {
                if (sensorX - j >= 0) {
                    intervals.get(sensorX - j).offer(new Coordinate(Math.max(0, minY + j), Math.min(maxSize, maxY - j)));
                }
                if (sensorX + j <= maxSize) {
                    intervals.get(sensorX + j).offer(new Coordinate(Math.max(0, minY + j), Math.min(maxSize, maxY - j)));
                }
            }
        }
        // Step 3: For each interval, merge them down to a single element
        //      This can be done as part of step 2 but I think maybe that's wasteful, also more complicated
        for (int i = 0; i < intervals.size(); i++) {
            PriorityQueue<Coordinate> interval = mergeIntervals(intervals.get(i));
            // If at any point we could not merge the interval, or the interval is lacking at either end, we found the beacon.
            if (interval.size() > 1 || interval.peek().x() > 0 || interval.peek().y() < maxSize) {
                return 4000000L * getMissingSpace(interval, maxSize) + i;
            }
        }
        // Error, could not find beacon
        return -1;
    }

    private PriorityQueue<Coordinate> mergeIntervals(PriorityQueue<Coordinate> intervals) {
        // Merge all the intervals down into one big interval.
        while (intervals.size() > 1) {
            // Pop the first two elements
            Coordinate first = intervals.poll(), second = intervals.poll();
            // Attempt to merge
            if (second.x() <= first.y()) {
                intervals.offer(new Coordinate(first.x(), Math.max(first.y(), second.y())));
            } else {
                // If we cannot merge, then the interval is broken and we will use this information to solve the puzzle
                intervals.offer(second);
                intervals.offer(first);
                return intervals;
            }
        }
        return intervals;
    }

    private long getMissingSpace(PriorityQueue<Coordinate> intervals, int maxSize) {
        // If the interval has size 1, then it is missing either end (0 or maxSize) of the grid
        if (intervals.size() == 1) {
            if (intervals.peek().x() > 0) {
                return 0;
            } else if (intervals.peek().y() < maxSize) {
                return maxSize;
            } else { // Unreachable
                return -1;
            }
        } else {
            // If there are more intervals, then we just need to go 1 past the furthest spot on the first interval
            return intervals.peek().y() + 1;
        }
    }

    int countDeadSpots() {
        int deadSpots = 0;
        for (int i = 0; i < deadSpotGrid.length; i++) {
            if (deadSpotGrid[i] == 9) {
                deadSpots++;
            }
        }
        return deadSpots;
    }
}