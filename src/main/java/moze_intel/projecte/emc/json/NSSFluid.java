package moze_intel.projecte.emc.json;

import net.minecraftforge.fluids.Fluid;

class NSSFluid extends NormalizedSimpleStack {
    public final String name;

    NSSFluid(Fluid f) {
        this.name = f.getName();
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof NSSFluid && name.equals(((NSSFluid) o).name);
    }

    @Override
    public String json()
    {
        return "FLUID|"+this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Fluid: " + this.name;
    }
}
