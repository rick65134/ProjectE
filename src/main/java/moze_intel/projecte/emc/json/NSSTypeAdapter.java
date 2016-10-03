package moze_intel.projecte.emc.json;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.IOException;

public class NSSTypeAdapter extends TypeAdapter<NormalizedSimpleStack> {
    @Override
    public void write(JsonWriter out, NormalizedSimpleStack value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.json());
        }
    }

    @Override
    public NormalizedSimpleStack read(JsonReader in) throws IOException {
        String s = in.nextString();

        if (s.startsWith("FAKE|")) {
            return NormalizedSimpleStack.createFake(s.substring("FAKE|".length()));
        } else if (s.startsWith("FLUID|")) {
            Fluid f = FluidRegistry.getFluid(s.substring("FLUID|".length()));
            return f == null ? null : NormalizedSimpleStack.getFor(f);
        } else if (s.startsWith("OD|")) {
            return NormalizedSimpleStack.forOreDictionary(s.substring("OD|".length()));
        } else {
            int pipeIndex = s.lastIndexOf('|');
            if (pipeIndex < 0) {
                throw new JsonParseException(String.format("Cannot parse '%s' as itemstack. Missing | to separate metadata.", s));
            }
            String itemName = s.substring(0, pipeIndex);
            String itemDamageString = s.substring(pipeIndex + 1);
            int itemDamage;
            if (itemDamageString.equals("*")) {
                itemDamage = OreDictionary.WILDCARD_VALUE;
            }
            else
            {
                try
                {
                    itemDamage = Integer.parseInt(itemDamageString);
                } catch (NumberFormatException e)
                {
                    throw new JsonParseException(String.format("Could not parse '%s' to metadata-integer", itemDamageString), e);
                }
            }

            return NormalizedSimpleStack.getFor(itemName, itemDamage);
        }
    }
}
