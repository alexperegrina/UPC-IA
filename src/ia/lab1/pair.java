package ia.lab1;

public class pair
{
    private Double first;
    private Double second;
    
    public pair() {
    	first   = 0.0;
        second = 0.0;
    }

    public pair(Double aKey, Double aValue)
    {
        first   = aKey;
        second = aValue;
    }
    
    public void setFirst(Double value) {
    	first = value;
    }
    public void setSecond(Double value) {
    	second = value;
    }

    public Double getFirst()   { return first; }
    public Double getSecond() { return second; }
}