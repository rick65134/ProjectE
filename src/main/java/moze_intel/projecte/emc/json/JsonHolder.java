package moze_intel.projecte.emc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;

public final class JsonHolder {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NormalizedSimpleStack.class, new NSSTypeAdapter().nullSafe())
            .registerTypeAdapter(CustomEMCEntry.class, new CustomEMCEntry.Serializer())
            .registerTypeAdapter(Item.class, new ItemTypeAdapter().nullSafe())
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .create();

    private JsonHolder() {}
}
