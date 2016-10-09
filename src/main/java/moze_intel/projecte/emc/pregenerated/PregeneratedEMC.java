package moze_intel.projecte.emc.pregenerated;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moze_intel.projecte.emc.json.JsonHolder;
import moze_intel.projecte.emc.json.NormalizedSimpleStack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class PregeneratedEMC
{
	public static boolean tryRead(File f, Map<NormalizedSimpleStack, Integer> map)
	{
		try {
			Map<NormalizedSimpleStack, Integer> m = read(f);
			map.clear();
			map.putAll(m);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<NormalizedSimpleStack, Integer> read(File file) throws IOException
	{
		Type type = new TypeToken<Map<NormalizedSimpleStack, Integer>>() {}.getType();
		try (BufferedReader r = new BufferedReader(new FileReader(file)))
		{
			Map<NormalizedSimpleStack, Integer> map = JsonHolder.GSON.fromJson(r, type);
			map.remove(null);
			return map;
		}
	}

	public static void write(File file, Map<NormalizedSimpleStack, Integer> map) throws IOException
	{
		Type type = new TypeToken<Map<NormalizedSimpleStack, Integer>>() {}.getType();
		try (BufferedWriter w = new BufferedWriter(new FileWriter(file)))
		{
			JsonHolder.GSON.toJson(map, type, w);
		}
	}
}
