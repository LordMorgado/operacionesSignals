/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operaciones;

/**
 *
 * @author alfa
 */
public class discreteFunction {
    private final double[] values;
    private final int zeroPosition;
    
    public discreteFunction(double [] values,int zero)
    {
        this.values=values;
        this.zeroPosition=zero;
    }
    
    public double[] getValues()
    {
        return values;
    }
    
    public int getZeroPosition()
    {
        return zeroPosition;
    }
}
