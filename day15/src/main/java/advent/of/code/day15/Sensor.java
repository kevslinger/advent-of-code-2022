package advent.of.code.day15;

import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Sensor {
    private Coordinate sensorCoordinate;
    private Coordinate beaconCoordinate;

    Sensor(Coordinate sensorCoordinate, Coordinate beaconCoordinate) {
        this.sensorCoordinate = sensorCoordinate;
        this.beaconCoordinate = beaconCoordinate;
    }

    Coordinate getSensorCoordinate() {
        return sensorCoordinate;
    }

    Coordinate getBeaconCoordinate() {
        return beaconCoordinate;
    }

    static int getDistance(Coordinate a, Coordinate b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    int getDistance() {
        return Sensor.getDistance(sensorCoordinate, beaconCoordinate);
    }

    int[] getSignalRange() {
        int[] range = new int[4];
        int distance = Sensor.getDistance(sensorCoordinate, beaconCoordinate);
        range[0] = sensorCoordinate.x() - distance;
        range[1] = sensorCoordinate.x() + distance;
        range[2] = sensorCoordinate.y() - distance;
        range[3] = sensorCoordinate.y() + distance;
        return range;
    }

    static ArrayList<Sensor> parseSensors(ArrayList<String> sensorStrings) {
        var sensors = new ArrayList<Sensor>();
        String regex = "x=(-?[0-9]+), y=(-?[0-9]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        int x, y;
        Coordinate sensorCoordinate, beaconCoordinate;
        for (String sensorString : sensorStrings) {
            matcher = pattern.matcher(sensorString);
            matcher.find();
            sensorCoordinate = new Coordinate(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
            matcher.find();
            beaconCoordinate = new Coordinate(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)));
            sensors.add(new Sensor(sensorCoordinate, beaconCoordinate));
        }
        return sensors;
    }
}