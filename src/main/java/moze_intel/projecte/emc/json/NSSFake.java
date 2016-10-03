package moze_intel.projecte.emc.json;

class NSSFake extends NormalizedSimpleStack {

    public final String description;
    public final int counter;
    private static int fakeItemCounter = 0;

    NSSFake(String description)
    {
        this.counter = fakeItemCounter++;
        this.description = description;
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }

    @Override
    public String json()
    {
        return "FAKE|" + this.counter + " " + this.description;
    }

    @Override
    public String toString() {
        return "NSSFAKE" + counter + ": " + description;
    }
}
