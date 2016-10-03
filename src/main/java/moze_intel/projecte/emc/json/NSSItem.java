package moze_intel.projecte.emc.json;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class NSSItem extends NormalizedSimpleStack {
    public final String itemName;
    public final int damage;

    NSSItem(String itemName, int damage) {
        this.itemName = itemName;
        if (Item.REGISTRY.getObject(new ResourceLocation(itemName)) == null) {
            throw new IllegalArgumentException("Invalid Item with itemName = " + itemName);
        }
        this.damage = damage;
    }

    @Override
    public int hashCode() {
        return itemName.hashCode() ^ damage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NSSItem) {
            NSSItem other = (NSSItem) obj;
            return this.itemName.equals(other.itemName) && this.damage == other.damage;
        }

        return false;
    }

    @Override
    public String json()
    {
        return String.format("%s|%s", itemName,  damage == OreDictionary.WILDCARD_VALUE ? "*" : damage);
    }

    @Override
    public String toString() {
        Item obj = Item.REGISTRY.getObject(new ResourceLocation(itemName));

        if (obj != null) {
            return String.format("%s(%s:%s)", itemName, Item.REGISTRY.getIDForObject(obj), damage == OreDictionary.WILDCARD_VALUE ? "*" : damage);
        }

        return String.format("%s(???:%s)", itemName, damage == OreDictionary.WILDCARD_VALUE ? "*" : damage);
    }
}
