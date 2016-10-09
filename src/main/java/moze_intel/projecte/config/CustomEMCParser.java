package moze_intel.projecte.config;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import moze_intel.projecte.PECore;
import moze_intel.projecte.emc.json.CustomEMCEntry;
import moze_intel.projecte.emc.json.CustomEMCFile;
import moze_intel.projecte.emc.json.JsonHolder;
import moze_intel.projecte.emc.json.NormalizedSimpleStack;
import moze_intel.projecte.emc.mappers.customConversions.CustomConversionMapper;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.util.JsonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class CustomEMCParser
{
	private static final File CONFIG = new File(PECore.CONFIG_DIR, "custom_emc.json");

	public static CustomEMCFile currentEntries;
	private static boolean dirty = false;

	public static void init()
	{
		flush();

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
			}
		}

		try {
			currentEntries = JsonHolder.GSON.fromJson(new BufferedReader(new FileReader(CONFIG)), CustomEMCFile.class);
		} catch (FileNotFoundException e) {
			PELogger.logFatal("Couldn't read custom emc file");
			currentEntries = new CustomEMCFile(new ArrayList<>());
		}
	}

	private static NormalizedSimpleStack getNss(String str, int meta)
	{
		if (str.contains(":"))
		{
			return NormalizedSimpleStack.getFor(str, meta);
		}
		else
		{
			return NormalizedSimpleStack.forOreDictionary(str);
		}
	}

	public static boolean addToFile(String toAdd, int meta, int emc)
	{
		NormalizedSimpleStack nss = getNss(toAdd, meta);

		for (CustomEMCEntry entry : currentEntries.entries)
		{
			if (entry.nss.equals(nss))
			{
				return false;
			}
		}

		currentEntries.entries.add(new CustomEMCEntry(nss, emc));
		dirty = true;
		return true;
	}

	public static boolean removeFromFile(String toRemove, int meta)
	{
		NormalizedSimpleStack nss = getNss(toRemove, meta);
		Iterator<CustomEMCEntry> iter = currentEntries.entries.iterator();

		boolean removed = false;
		while (iter.hasNext())
		{
			if (iter.next().nss.equals(nss))
			{
				iter.remove();
				dirty = true;
				removed = true;
			}
		}

		return removed;
	}

	private static void flush()
	{
		if (dirty)
		{
			try
			{
				Files.write(JsonHolder.GSON.toJson(currentEntries), CONFIG, Charsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}

			dirty = false;
		}
	}

	private static void writeDefaultFile()
	{
		JsonObject elem = (JsonObject) JsonHolder.GSON.toJsonTree(new CustomEMCFile(new ArrayList<>()));
		elem.add("__comment", new JsonPrimitive("Use the in-game commands to edit this file"));
		try
		{
			Files.write(JsonHolder.GSON.toJson(elem), CONFIG, Charsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
