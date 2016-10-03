package moze_intel.projecte.emc.json;

public class NSSOreDictionary extends NormalizedSimpleStack {

    public final String od;

    NSSOreDictionary(String od) {
        this.od = od;
    }

    @Override
    public int hashCode()
    {
        return od.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof NSSOreDictionary && this.od.equals(((NSSOreDictionary) o).od);
    }

    @Override
    public String json()
    {
        return "OD|" + this.od;
    }

    @Override
    public String toString() {
        return "OD: " + od;
    }
}
