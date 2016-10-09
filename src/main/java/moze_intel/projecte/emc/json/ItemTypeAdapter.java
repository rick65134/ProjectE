package moze_intel.projecte.emc.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ItemTypeAdapter extends TypeAdapter<Item> {
    @Override
    public void write(JsonWriter out, Item value) throws IOException {
        out.value(value.getRegistryName().toString());
    }

    @Override
    public Item read(JsonReader in) throws IOException {
        String name = in.nextString();
        Item item = Item.REGISTRY.getObject(new ResourceLocation(name));

        if (item == null)
        {
            PELogger.logWarn("Read item %s from json that doesn't exist", name);
        }

        return item;
    }
}
