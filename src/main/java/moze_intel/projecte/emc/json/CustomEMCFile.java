package moze_intel.projecte.emc.json;

import java.util.List;

public class CustomEMCFile
{
    public final List<CustomEMCEntry> entries;

    public CustomEMCFile(List<CustomEMCEntry> entries)
    {
        this.entries = entries;
    }
}
