package moze_intel.projecte.emc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moze_intel.projecte.emc.mappers.customConversions.json.CustomConversion;
import moze_intel.projecte.emc.mappers.customConversions.json.CustomConversionDeserializer;
import moze_intel.projecte.emc.mappers.customConversions.json.FixedValues;
import moze_intel.projecte.emc.mappers.customConversions.json.FixedValuesDeserializer;
import net.minecraft.item.Item;

public final class JsonHolder {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NormalizedSimpleStack.class, new NSSTypeAdapter().nullSafe())
            .registerTypeAdapter(CustomEMCEntry.class, new CustomEMCEntry.Serializer())
            .registerTypeAdapter(Item.class, new ItemTypeAdapter().nullSafe())
            .registerTypeAdapter(CustomConversion.class, new CustomConversionDeserializer())
		    .registerTypeAdapter(FixedValues.class, new FixedValuesDeserializer())
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .create();

    private JsonHolder() {}
}
