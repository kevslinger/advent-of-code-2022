package advent.of.code.day11;

class MonkeyOp {
    private String[] operation;

    MonkeyOp(String operation) {
        this.operation = operation.strip().split(" ");
    }

    long performOp(long old) {
        // first 3 are op: new = 
        long out;
        // Assume there is only one operation
        long op1, op2;
        if (operation[3].equals("old")) {
            op1 = old;
        } else {
            op1 = Integer.parseInt(operation[3]);
        }
        if  (operation[5].equals("old")) {
            op2 = old;
        } else {
            op2 = Integer.parseInt(operation[5]);
        }
        String op = operation[4];
        switch (op) {
            case "+" -> {
                return op1 + op2;
            }
            case "*" -> {
                return op1 * op2;
            }
            case "/" -> {
                return op1 / op2;
            }
            case "-" -> {
                return op1 - op2;
            }
            default -> {
                out = 0;
                System.out.println("ERROR: No operation detected");
                System.exit(9);
            }
        }
        return out;
    }

    @Override
    public String toString() {
        return this.operation[1] + " " + this.operation[2] + " " + this.operation[3] + " " + this.operation[4] + " " + this.operation[5];
    }
}
