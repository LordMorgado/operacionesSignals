package operaciones;

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
