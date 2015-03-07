/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton;

import net.objecthunter.exp4j.*;

class ZeroCalculator {

    
     /**
     * Use exp4j library engine to evaluate functions
     * @author Alex
     * @returns value of function at given x
     */
    private double evaluate(String function, double x) {
        Expression e = new ExpressionBuilder(function)
        .variables("x","e","pi","π")
        .build()
        .setVariable("x", x)
        .setVariable("π", Math.PI)
        .setVariable("e", Math.E)
        .setVariable("pi", Math.PI);
        double result = e.evaluate();
        return result;
    }

    /**
     * Gets the slope at a point x arithmetically
     *
     * @author Alex
     */
    private double derivativeOf (String function, double x)
    {
        double left = evaluate (function, x-(1e-5));
        double right= evaluate (function, x+(1e-5));
        double slope = (right-left)/(2*1e-5);
        return slope;
    }
    
    /**
     * This is where the algorithm happens
     *
     * @author Alex
     * @returns zeroes of given string function, as an array of doubles
     */
    public double zero(String function, double guess) {
        // Maximum accepted change in x for an answer
        double tolerance = 1e-5;
        
        // How horizontal our slope can get before we break
        double almostZero = 1e-20;
        
        // The users inputted guess
        double x = guess;
        
        // This just needs some value
        double newX=-999.0;
        
        // If we have found a result after 100 iterations
        boolean found=false;
        
        
        //This is the majority of the actual algorithm
        for (int iter = 0; (!found)&&(iter < 100); iter++) {
            
            // f(x)
            double y= evaluate (function,x);

            // f'(x)
            double slope =derivativeOf (function, x); 
            
            //If slope is horizontal, massive error will be induced, we stop here.
            if (almostZero> Math.abs(slope)) 
                throw new IllegalArgumentException ("Bad guess, function becomes horizontal");
            
            // This is important
            newX = x-(y/slope); 
            
            // If x has changed sufficiently little
            if (Math.abs (newX-x)<tolerance){
                found = true;
                break;
            }
            x=newX;
        }
        if (found)
            return newX;
        throw new IllegalArgumentException("Could not find a zero");
    }
}
