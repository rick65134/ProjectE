package moze_intel.projecte.emc.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

public class CustomEMCEntry
{
    public final NormalizedSimpleStack nss;
    public final int emc;

    public CustomEMCEntry(NormalizedSimpleStack nss, int emc)
    {
        this.nss = nss;
        this.emc = emc;
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this ||
                o instanceof CustomEMCEntry
                        && nss.equals(((CustomEMCEntry) o).nss)
                        && emc == ((CustomEMCEntry) o).emc;
    }

    @Override
    public int hashCode()
    {
        return nss.hashCode() ^ 31 * emc;
    }

    static class Serializer implements JsonSerializer<CustomEMCEntry>, JsonDeserializer<CustomEMCEntry>
    {
        @Override
        public CustomEMCEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = JsonUtils.getJsonObject(json, "custom emc entry");
            if (!JsonUtils.hasField(obj, "item") || !JsonUtils.hasField(obj, "emc"))
            {
                throw new JsonParseException("Missing fields from Custom EMC entry");
            }
            NormalizedSimpleStack nss = context.deserialize(obj.get("item"), NormalizedSimpleStack.class);
            int emc = JsonUtils.getInt(obj.get("emc"), "emc");
            if (emc < 0)
            {
                throw new JsonParseException("Invalid EMC amount: " + emc);
            }
            return new CustomEMCEntry(nss, emc);
        }

        @Override
        public JsonElement serialize(CustomEMCEntry src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.add("item", context.serialize(src.nss));
            obj.add("emc", context.serialize(src.emc));
            return obj;
        }
    }
}
