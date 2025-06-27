package calculator;
import java.util.*;

public class main
{
    public static void main(String[] args)
    {
       Scanner scan=new Scanner(System.in);

        System.out.println("===== Simple Calculator =====");
        System.out.print("Enter first number: ");
        int a = scan.nextInt();

        System.out.print("Enter second number: ");
        int b = scan.nextInt();

        System.out.println("Choose operation: +  -  *  /");
        System.out.print("Enter operation: ");
        char op = scan.next().charAt(0);

        try {
            switch (op) {
                case '+':
                    System.out.println("Result: " + add.add(a, b));
                    break;
                case '-':
                    System.out.println("Result: " + sub.sub(a, b));
                    break;
                case '*':
                    System.out.println("Result: " + multi.multi(a, b));
                    break;
                case '/':
                    System.out.println("Result: " + div.div(a, b));
                    break;
                default:
                    System.out.println("Invalid operation!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scan.close();
    }
}
