package advent.of.code.day24;

class SimulationState {
    private int minutes;
    private Coordinate loc;

    SimulationState(int minutes, Coordinate loc) {
        this.minutes = minutes;
        this.loc = loc;
    }

    SimulationState(Coordinate loc) {
        this.loc = loc;
    }

    int getMinutes() {
        return minutes;
    }

    Coordinate getLoc() {
        return loc;
    }

    public String toString() {
        return "" + minutes + " minutes, Location: " + loc.x() + ", " + loc.y();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SimulationState)) { 
            return false; 
        }
        SimulationState ss = (SimulationState) o;
        return loc.equals(ss.getLoc()) && minutes == ss.getMinutes();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + prime * minutes
                + ((loc == null) ? 0 : loc.hashCode());
        return result;
    }
}
