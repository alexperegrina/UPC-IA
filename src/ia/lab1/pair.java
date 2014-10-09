package ia.lab1;

public class pair
{
    private final Double first;
    private final Double second;

    public pair(Double aKey, Double aValue)
    {
        first   = aKey;
        second = aValue;
    }

    public Double getFirst()   { return first; }
    public Double getSecond() { return second; }
}