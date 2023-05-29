package advent.of.code.day11;

import java.math.BigInteger;

class MonkeyOp {
    private String[] operation;

    MonkeyOp(String operation) {
        this.operation = operation.strip().split(" ");
    }

    BigInteger performOp(BigInteger old) {
        // first 3 are op: new = 
        BigInteger out;
        // Assume there is only one operation
        BigInteger op1, op2;
        if (operation[3].equals("old")) {
            op1 = old;
        } else {
            op1 = new BigInteger(operation[3]);
        }
        if  (operation[5].equals("old")) {
            op2 = old;
        } else {
            op2 = new BigInteger(operation[5]);
        }
        String op = operation[4];
        switch (op) {
            case "+": 
                out = op1.add(op2);
                break;
            case "*":
                out = op1.multiply(op2);
                break;
            case "/":
                out = op1.divide(op2);
                break;
            case "-":
                out = op1.subtract(op2);
                break;
            default:
                out = BigInteger.valueOf(0);
                System.out.println("ERROR: No operation detected");
                System.exit(9);
                break;
        }
        return out;
    }

    public String toString() {
        return this.operation[1] + " " + this.operation[2] + " " + this.operation[3] + " " + this.operation[4] + " " + this.operation[5];
    }
}
