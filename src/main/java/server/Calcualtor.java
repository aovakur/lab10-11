package server;

import java.util.Scanner;

public class Calcualtor {

    public double calc(String input) throws Exception {
        Scanner scanner = new Scanner(input);
        double x = scanner.nextDouble();
        String op = scanner.next();
        double y = scanner.nextDouble();

        double result;

        if (op.equals("+")) result = x + y;
        else if (op.equals("-")) result = x - y;
        else if (op.equals("*")) result = x * y;
        else if (op.equals("/")) result = x / y;
        else throw new Exception("Operator not recognized");

        return result;
    }

}
