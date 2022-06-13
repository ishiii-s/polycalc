
/**
 * Class to represent a monomial l(a term with a coefficient and exponent)
 *
 * @author Ishi Sood
 * @version 5/3/2022
 */
public class Mono
{
    // fields
    double coef; 
    int exp;
    
    /**
     * Constructor for objects of class Mono
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the monomial created.
     */
    public Mono(double coef, int exp)
    {
        this.coef = coef;
        this.exp = exp;
    }
    
    /**
     * "Getter" method returns coefficient value of monomial
     * 
     * @return coef coefficient value of monomial
     */
    public double coef(){
        return coef;
    }
    
    /**
     * "Getter" method returns exponent value of monomial
     * 
     * @return exp exponent value of monomial
     */
    public int exp(){
        return exp;
    }
    
    /**
     * "Setter" method sets new coefficient value of monomial
     * 
     * @param newC new coefficient to set monomial's coefficient to 
     */
    public void setCoef(double newC){
        this.coef = newC;
    }
    
    /**
     * "Setter" method sets new exponent value of monomial
     * 
     * @param newE new exponent to set monomial's exponent to 
     */
    public void setExp(int newE){
        this.exp = newE;
    }
    
    
    

}
