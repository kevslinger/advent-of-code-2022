package advent.of.code.day13;


class Node {
    // Int value
    private Integer value;
    // Next node in the sequence
    private Node next;
    // Flags to denote start/end of links
    private boolean start;
    private boolean end;

    Node(Integer value) {
        this.value = value;
        start = false;
        end = false;
        next = null;
    }

    Node(boolean start, boolean end) {
        this.start = start;
        this.end = end;
        value = null;
        next = null;
    }

    void setNext(Node next) {
        this.next = next;
    }

    Node getNext() {
        return next;
    }

    Integer getValue() {
        return value;
    }

    boolean isStart() {
        return start;
    }
    
    boolean isEnd() {
        return end;
    }
}
