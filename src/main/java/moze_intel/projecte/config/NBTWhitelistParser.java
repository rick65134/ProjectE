package moze_intel.projecte.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import moze_intel.projecte.PECore;
import moze_intel.projecte.emc.json.JsonHolder;
import moze_intel.projecte.utils.NBTWhitelist;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class NBTWhitelistParser
{
	private static final File CONFIG = new File(PECore.CONFIG_DIR, "nbt_whitelist.json");

	private static class NBTWhiteList
	{
		public final List<Item> items;

		private NBTWhiteList(List<Item> items)
		{
			this.items = items;
		}
	}

	public static void init()
	{
		if (!CONFIG.exists())
		{
			try
			{
				if (CONFIG.createNewFile())
				{
					writeDefaultFile();
				}
			}
			catch (IOException e)
			{
				PELogger.logFatal("Exception in file I/O: couldn't create custom configuration files.");
				e.printStackTrace();
			}
		}

		try(BufferedReader r = new BufferedReader(new FileReader(CONFIG)))
		{
			NBTWhiteList whitelist = JsonHolder.GSON.fromJson(r, NBTWhiteList.class);
			for (Item i : whitelist.items)
            {
				PELogger.logInfo("Registering %s to NBT whitelist", i.getRegistryName().toString());
                NBTWhitelist.register(new ItemStack(i));
            }
		} catch (IOException e)
		{
			PELogger.logFatal("Couldn't read nbt whitelist file");
		}

	}

	private static void writeDefaultFile()
	{
		List<Item> defaults = new ArrayList<>();

		if (Loader.isModLoaded("tconstruct"))
		{
			defaults.add(Item.getByNameOrId("tconstruct:pickaxe"));
		}

		if (Loader.isModLoaded("Botania"))
		{
			defaults.add(Item.getByNameOrId("botania:specialFlower"));
		}

		JsonObject obj = (JsonObject) JsonHolder.GSON.toJsonTree(new NBTWhiteList(defaults));
		obj.add("__comment", new JsonPrimitive("To add items to NBT Whitelist, simply add its registry name as a String to the above array"));

		try (BufferedWriter w = new BufferedWriter(new FileWriter(CONFIG)))
		{
			JsonHolder.GSON.toJson(obj, w);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
