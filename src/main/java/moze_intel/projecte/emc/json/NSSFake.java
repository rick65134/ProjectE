package moze_intel.projecte.emc.json;

class NSSFake extends NormalizedSimpleStack {

    public final String name;
    public final int counter;
    private static int fakeItemCounter = 0;

    NSSFake(String name)
    {
        this.counter = fakeItemCounter++;
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }

    @Override
    public String json()
    {
        return "FAKE|" + this.counter + " " + this.name;
    }

    @Override
    public String toString() {
        return "NSSFAKE" + counter + ": " + name;
    }
}
