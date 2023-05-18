package advent.of.code.day13;


class Node {
    // Int value
    private Integer value;
    // Next node in the sequence
    private Node next;
    // Flags to denote start/end of links
    private boolean start;
    private boolean end;

    public Node() {

    }

    public Node(Integer value) {
        this.value = value;
        start = false;
        end = false;
        next = null;
    }

    public Node(boolean start, boolean end) {
        this.start = start;
        this.end = end;
        value = null;
        next = null;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isStart() {
        return start;
    }
    
    public boolean isEnd() {
        return end;
    }
}
