import java.util.LinkedList;
import java.util.Scanner;

/**
 * Write a description of class PolyCalc here.
 *
 * @author Ishi Sood
 * @version 5/3/2022
 */
public class PolyCalc
{
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Ishi's polynomial calculator!");
        while (true)
        {
            Poly p1 = getPoly();
            if (p1 == null)
                return;
            System.out.println("Enter an operation:");
            String operation = sc.nextLine();
            if (operation.compareTo("quit") == 0)
                return;
            while(operation.compareTo("+") != 0 && operation.compareTo("-") != 0 && operation.compareTo("*") != 0 &&
            operation.compareTo("/") != 0){
                System.out.println("Invalid operation. Enter an operation:");
                operation = sc.nextLine();
            }
            Poly p2 = getPoly();
            if (p2 == null)
                return;
                
            System.out.println(executeOper(operation, p1, p2));
        }

    }
    
    /**
     * Executes operation on polynomials based on String input
     * 
     * @param op String representing operation 
     * @param p1 polynomial for operation to be performed on 
     * @param p2 polynomial for operation to be performed with 
     * @return new polynomial representing result of operation with p1 and p2
     */
    public static Poly executeOper(String op, Poly p1, Poly p2){
        if (op.compareTo("+") == 0)
            return p1.add(p2);
        
        else if (op.compareTo("-") == 0)
            return p1.subtract(p2);
        
        else if(op.compareTo("*") == 0)
            return p1.multiply(p2);
        
        else
            return p1.divide(p2);
    }
    
    /**
     * Creates valid polynomial based on user input. 
     * 
     * @returns new polynomial using user input
     */
    public static Poly getPoly(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a polynomial:");
        String input = sc.nextLine();
        if (input.compareTo("quit") == 0){
            return null;
        }
        Poly p1 = buildPoly(input);
        while (p1 == null){
            System.out.println("Invalid polynomial. Please enter a valid polynomial");
            input = sc.nextLine();
            p1 = buildPoly(input);
        }
        return p1;
    }
    
    /**
     * Converts string representation of polynomial into Poly object
     * 
     * @param input string representation of polynomial 
     * @return new polynomial created using String input or null if input is invalid. 
     */
    public static Poly buildPoly(String input){
        Poly retP = new Poly();
        String expStr;
        while(input.length() != 0){
            int space = input.indexOf(" ");
            if (space < 0)
                return null;
            String coefStr = input.substring(0, space);
            input = input.substring(space + 1);
            space = input.indexOf(" ");
            if (space < 0){
                expStr = input;
                input = "";
            } 
            else
                expStr = input.substring(0, space);
                input = input.substring(space + 1);

            double coef = Integer.parseInt(coefStr);
            int exp = Integer.parseInt(expStr);
            Poly temp = new Poly(coef, exp);
            if (exp < 0){
                return null;
            }   
            retP = retP.add(temp);
            
        }
        return retP;
    }
}
