package advent.of.code.day13;


class Packet {
    private Node packet;
    private String str;

    public Packet(String str) {
        this.str = str;
        packet = initializePacket(str);
    }

    private Node initializePacket(String str) {
        int idx = 1; // Start after the opening bracket.
        Integer[] numbers;
        var root = new Node(false, true); // Initialize a start node
        var dummy = root; 
        while (idx < str.length()) {
            char c = str.charAt(idx);
            Node tmpNode;
            switch(c) {
                case '[':
                    tmpNode = new Node(true, false);
                    dummy.setNext(tmpNode);
                    dummy = tmpNode;
                    break;
                case ']':
                    tmpNode = new Node(false, true);
                    dummy.setNext(tmpNode);
                    dummy = tmpNode;
                    break;
                case ',':
                    break;
                default:
                    Integer val;
                    // Some of the numbers may be 2 characters e.g. 10
                    try {
                        val = Integer.parseInt(str.substring(idx, idx + 2));
                    } catch (NumberFormatException e) {
                        val = (Integer)(c - '0');
                    }
                    tmpNode = new Node(val);
                    // If we're starting a new list but it doesn't have [], then we need to wrap the integer in a start and end
                    if (dummy.isEnd()) {
                        dummy.setNext(new Node(true, false));
                        dummy = dummy.getNext();
                        dummy.setNext(tmpNode);
                        dummy = dummy.getNext();
                        dummy.setNext(new Node(false, true));
                        dummy = dummy.getNext();
                    } else {
                        dummy.setNext(tmpNode);
                        dummy = tmpNode;
                    }
                    break;
            }
            idx++;
        }
        return root;
    }

    public boolean isInOrder(Packet rightPacket) {
        var leftDummy = packet;
        var rightDummy = rightPacket.getPacket();

        // Loop through the packets until we find a difference or finish the list.
        while (leftDummy != null) {
            // If we have exhausted rightDummy, then it is not in order
            if (rightDummy == null) {
                return false;
            }

            if (leftDummy.isEnd() && rightDummy.isEnd()) {
                leftDummy = leftDummy.getNext();
                rightDummy = rightDummy.getNext();
                continue;
            }
            // If right dummy has ended a list but left dummy has not, then it is not in order
            if (rightDummy.isEnd() && !leftDummy.isEnd()) {
                return false;
            }
            // If left dummy has ended but right dummy has not, end right dummy
            if (leftDummy.isEnd() && !rightDummy.isEnd()) {
                return true;
            }
            // If they both have an integer, then left < right is in order, left > right is out of order, and left == right means we continue.
            Integer leftVal = leftDummy.getValue(), rightVal = rightDummy.getValue();
            if (leftVal != null && rightVal != null) {
                if (leftVal < rightVal) {
                    return true;
                } else if (leftVal > rightVal){
                    return false;
                } else {
                    // Advance pointers
                    leftDummy = leftDummy.getNext();
                    rightDummy = rightDummy.getNext();
                }
            } else {
                // If not both of them have an integer value, one might be in a nested list, so we skip over the start values.
                if (leftDummy.isStart()) {
                    leftDummy = leftDummy.getNext();
                }
                if (rightDummy.isStart()) {
                    rightDummy = rightDummy.getNext();
                }
            }
            
        }
        return true;
    }

    public Node getPacket() {
        return packet;
    }

    public String toString() {
        return str;
    }
}
