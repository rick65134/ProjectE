package moze_intel.projecte.emc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonHolder {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NormalizedSimpleStack.class, new NSSTypeAdapter())
            .registerTypeAdapter(CustomEMCEntry.class, new CustomEMCEntry.Serializer())
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .create();

    private JsonHolder() {}
}
