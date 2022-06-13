import java.util.*;
import java.util.LinkedList;
import java.util.Iterator;
/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 * This class is a skeleton. You need to provide implementations
 * for the methods here defined. Feel free however, to add additional
 * methods as you see fit.
 *
 * @author Ishi Sood
 * @version 5/3/2022
 */
public class Poly{

    // TODO your instance fields here
    //Linked list of Mono type objects (which represent individual monomimals) to represent polynomial
    public LinkedList<Mono> polyList = new LinkedList();  
    
    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * You can create additional constructors if you'd like, but it's 
     * imperative that this one exists.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){
        polyList.add(new Mono(coef, exp));
    }
    
    public Poly(){
        
    }
    
    /**
     * "Getter method" returns the Mono linked list representing the polynomial
     * @return the LinkedList<Mono>
     */
    public LinkedList<Mono> getPoly(){
        return polyList;
    }
    
    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        Poly refList = new Poly();
        Poly retPoly = new Poly();
        LinkedList<Mono> polyList2 = p.getPoly();
        for (Mono m: polyList2){
            Mono temp = this.degreeIndex(m);
            if (temp != null){
                Mono newM = new Mono(m.coef() + temp.coef(), m.exp());
                retPoly.getPoly().add(newM);
                refList.polyList.add(temp);
            }
            else{
                retPoly.getPoly().add(m);
            }
        }
        
        for (Mono m: this.polyList){
            if (refList == null){
                retPoly.getPoly().add(m);
            }
            else{
                Mono temp = refList.degreeIndex(m);
                if (temp == null)
                    retPoly.getPoly().add(m);
            }
        }
        retPoly.polyList = retPoly.sortPoly();
        
        return retPoly;
    }
    
    /**
     * Checks if a monomial with the same exponent degree as the parameter exists in the current polynomial
     * (i.e. if it exists in the polyList)
     * 
     * @param m the monomial whose degree we want to check 
     * @return the Mono (monomial) with the same degree in the polynomial or null if none exists. 
     */
    private Mono degreeIndex(Mono m){
        for (int i = 0; i < polyList.size(); i ++){
            if (this.polyList.get(i).exp() == m.exp())
                if(m.coef() != 0 && this.polyList.get(i).coef() != 0)
                    return polyList.get(i);
        }
        return null; 
    }
    
    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        Poly retPoly = copyPoly(this.polyList);
        LinkedList<Mono> polyList2 = p.getPoly();
        for (Mono m: polyList2){
            retPoly = retPoly.add(new Poly(-1 * m.coef(), m.exp()));
            retPoly.polyList = retPoly.sortPoly();
        }
        retPoly.polyList = retPoly.sortPoly();
        return retPoly;
    }
    
    
 
    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        Poly retPoly = new Poly();
        for (Mono m: this.polyList){
            for(Mono m1: p.getPoly()){
                Poly temp = new Poly((m.coef() * m1.coef()), m.exp() + m1.exp());
                retPoly = retPoly.add(temp);
            }
        }
        retPoly.sortPoly();
        return retPoly; 
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){
        Poly q = new Poly();
        Poly r = copyPoly(this.polyList);
        LinkedList<Mono> tempList = r.getPoly();
        LinkedList<Mono> qList = p.getPoly();
        if (p.equals(new Poly(0, 0))){
            System.out.println("polynomials are not divisible");
            return null;
        }
        if (this.equals(new Poly(0, 0)))
            return new Poly(0, 0);
            
        Iterator<Mono> itr = tempList.iterator();
        Mono lead = itr.next();
        
        while(tempList.size() != 0 && !(r.equals(new Poly(0, 0)) && lead.exp() <= qList.get(0).exp())){
            if(lead.exp() == 0 && qList.get(0).exp() != 0){
                System.out.println("polynomials are not divisible");
                return null;
            }
            double coefficient = lead.coef() / qList.get(0).coef();
            int exponent = lead.exp() - qList.get(0).exp();
            if (exponent < 0){
                System.out.println("polynomials are not divisible");
                return null;
            }
            Poly temp = new Poly(coefficient, exponent);
            q = q.add(temp);
            r = r.subtract(temp.multiply(p));
            tempList = r.getPoly();
            itr = tempList.iterator();
            if (itr.hasNext()){
                lead = itr.next();
                if (lead.coef() == 0 || lead.coef() == -0){
                    r.polyList.remove(lead);
                    tempList = r.getPoly();
                    itr = tempList.iterator();
                    if (itr.hasNext())
                        lead = itr.next();
                }
            }
        }
        
        if (!(r.equals(new Poly(0, 0)))){
            System.out.println("polynomials are not divisible");
            return null;
        }            
        return q;
    }
    
    /**
     * Creates a copy of a polynomial. 
     * 
     * @param p the polynomial's linked list of monomials to be copied
     * @return the new polynomial with the same monomial values. 
     */
    public Poly copyPoly(LinkedList<Mono> p){
        Poly temp = new Poly();
        for (Mono m: p){
            temp.getPoly().add(new Mono(m.coef(), m.exp()));
        }
        return temp;
    }
    
    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){
        Poly Poly2 = Poly.class.cast(o);        
        LinkedList<Mono> pList2 = Poly2.sortPoly();
        this.polyList = this.sortPoly();
        
        int i; 
        for (i = 0; i < polyList.size(); i ++){
            if (pList2.size() > i){
                Mono m1 = this.polyList.get(i);
                Mono m2 = pList2.get(i);
                
                if (m1.coef() != 0 || m2.coef() != 0){
                   if (m1.coef() != m2.coef || m1.exp() != m2.exp())
                       return false;
                }
            }
            else{
                if (polyList.get(i).coef() != 0)
                    return false;
            }
        }
        if (pList2.size() - 1 >= i){
            for (int j = 0; j < pList2.size(); j ++){
                if (pList2.get(j).coef() != 0)
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * Sorts the polynomial based on exponent 
     * 
     * @returns LinkedList<Mono> with monomials sorted by exponent (in descending order)
     */
    private LinkedList<Mono> sortPoly(){
       LinkedList<Mono> retList = new LinkedList<Mono>();
       boolean added = false;

       for (Mono m: this.polyList){
           if (retList.size() == 0){
               if (m.coef() != 0)
                   retList.add(m);
               continue;
           }
           for (int i = 0; i < retList.size(); i ++){
               if(!added && (m.coef() != 0 && m.coef() != -0)){
                   if (retList.get(i).exp() < m.exp() ){
                       retList.add(i, m);
                       added = true;
                   }
                   else{
                       if (i == retList.size() - 1){
                           retList.add(m);
                           added = true;
                       }
                   }
               }
           }
           added = false;
       }
       return retList;
    }
    
    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    
    public String toString(){
        this.polyList = this.sortPoly();
        String retval = "";
        if (polyList.size() == 1)
            if (polyList.get(0).coef() == 0)
                return "0.0";
        if (polyList.size() == 0)
            return "0.0";
        for (int i = 0; i < polyList.size(); i ++){
            Mono curr = polyList.get(i);
            if (i != 0){
                if (curr.coef() < 0)
                    retval += " - ";
                else if (curr.coef() > 0)
                    retval += " + ";
            }
            else{
                if (curr.coef() < 0)
                    retval += "-";
            }
            if (curr.coef() != 0){
                if (Math.abs(curr.coef()) != 1){
                    if (curr.coef() == -1 && curr.exp() != 0 && i != 0)
                        retval += "-";
                    else
                        retval += String.valueOf(Math.abs(curr.coef()));
                }
                else{
                    if (curr.exp() == 0)
                        retval += "1.0";
                }
                if (curr.exp() != 0){
                    retval += "x";
                    if (curr.exp() != 1)
                        retval += "^" + String.valueOf(curr.exp());
                }
            }

        }
        
        return retval;
    }

}
