package moze_intel.projecte.emc.mappers.customConversions;

import static org.junit.Assert.*;

import moze_intel.projecte.emc.json.JsonHolder;
import moze_intel.projecte.emc.json.NormalizedSimpleStack;
import moze_intel.projecte.emc.mappers.customConversions.json.ConversionGroup;
import moze_intel.projecte.emc.mappers.customConversions.json.CustomConversion;
import moze_intel.projecte.emc.mappers.customConversions.json.CustomConversionFile;

import net.minecraft.item.Item;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

public class CustomConversionMapperTest
{
	@Test
	public void testCommentOnlyCustomConversionFileJson() {
		String simpleFile = "{'comment':'A very simple Example'}";
		CustomConversionFile f = JsonHolder.GSON.fromJson(new StringReader(simpleFile), CustomConversionFile.class);
		assertNotNull(f);
		assertEquals("A very simple Example", f.comment);
	}

	@Test
	public void testSingleEmptyGroupConversionFileJson() {
		String simpleFile =
				"{'groups': {" +
						"	'groupa': {" +
						"		'comment':'A conversion group for something'," +
						"		'conversions':[" +
						"		]" +
						"	}" +
						"}" +
					"}";

		CustomConversionFile f = JsonHolder.GSON.fromJson(new StringReader(simpleFile), CustomConversionFile.class);
		assertNotNull(f);
		assertEquals(1, f.groups.size());
		assertTrue("Map contains key for group", f.groups.containsKey("groupa"));
		ConversionGroup group = f.groups.get("groupa");
		assertNotNull(group);
		assertEquals("Group contains specific comment", group.comment, "A conversion group for something");
		assertEquals(0, group.conversions.size());
	}

	@Test
	public void testSimpleConversionFileJson() {
		String simpleFile =
				"{'groups': {" +
						"	'groupa': {" +
						"		'conversions':[" +
						"			{'output':'outA|0', 'ingr':{'ing1|0': 1, 'ing2|0': 2, 'ing3|0': 3}}," +
						"			{'output':'outB|0', 'ingr':['ing1|0', 'ing2|0', 'ing3|0']}," +
						"			{'output':'outC|0', 'count':3, 'ingr':['ing1|0', 'ing1|0', 'ing1|0']}" +
						"		]" +
						"	}" +
						"}" +
					"}";

		CustomConversionFile f = JsonHolder.GSON.fromJson(new StringReader(simpleFile), CustomConversionFile.class);
		assertNotNull(f);
		assertEquals(1, f.groups.size());
		assertTrue("Map contains key for group", f.groups.containsKey("groupa"));
		ConversionGroup group = f.groups.get("groupa");
		assertNotNull(group);
		assertEquals(3, group.conversions.size());
		List<CustomConversion> conversions = group.conversions;
		{
			CustomConversion conversion = conversions.get(0);
			assertEquals(NormalizedSimpleStack.getFor("outA", 0), conversion.output);
			assertEquals(1, conversion.count);
			assertEquals(3, conversion.ingredients.size());
			assertEquals(1, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing1", 0)));
			assertEquals(2, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing2", 0)));
			assertEquals(3, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing3", 0)));
		}
		{
			CustomConversion conversion = conversions.get(1);
			assertEquals(NormalizedSimpleStack.getFor("outB", 0), conversion.output);
			assertEquals(1, conversion.count);
			assertEquals(3, conversion.ingredients.size());
			assertEquals(1, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing1", 0)));
			assertEquals(1, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing2", 0)));
			assertEquals(1, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing3", 0)));
		}
		{
			CustomConversion conversion = conversions.get(2);
			assertEquals(NormalizedSimpleStack.getFor("outC", 0), conversion.output);
			assertEquals(3, conversion.count);
			assertEquals(1, conversion.ingredients.size());
			assertEquals(3, (int) conversion.ingredients.get(NormalizedSimpleStack.getFor("ing1", 0)));

		}
	}

	@Test
	public void testSetValueConversionFileJson()
	{
		String simpleFile =
				"{'values': {" +
						"	'before': {" +
						"		'a|0': 1, 'b|0': 2, 'c|0': 'free'" +
						"	}," +
						"	'after': {" +
						"		'd|0': 3" +
						"	}" +
						"}" +
						"}";
		CustomConversionFile f = JsonHolder.GSON.fromJson(new StringReader(simpleFile), CustomConversionFile.class);
		assertNotNull(f.values);
		assertEquals(1, (int) f.values.setValueBefore.get(NormalizedSimpleStack.getFor("a", 0)));
		assertEquals(2, (int) f.values.setValueBefore.get(NormalizedSimpleStack.getFor("b", 0)));
		assertEquals(Integer.MIN_VALUE, (int) f.values.setValueBefore.get(NormalizedSimpleStack.getFor("c", 0)));
		assertEquals(3, (int) f.values.setValueAfter.get(NormalizedSimpleStack.getFor("d", 0)));

	}

	@Test
	public void testSetValueFromConversion()
	{
		String simpleFile =
				"{'values': {" +
						"	'conversion': [" +
						"		{'output':'outA|0', 'ingr':{'ing1|0': 1, 'ing2|0': 2, 'ing3|0': 3}}" +
						"	]" +
						"}" +
						"}";
		CustomConversionFile f = JsonHolder.GSON.fromJson(new StringReader(simpleFile), CustomConversionFile.class);
		assertNotNull(f.values);
		assertNotNull(f.values.conversion);
		assertEquals(1, f.values.conversion.size());
		CustomConversion conversion = f.values.conversion.get(0);
		assertEquals(NormalizedSimpleStack.getFor("outA", 0), conversion.output);
		assertEquals(1, conversion.count);
		assertEquals(3, conversion.ingredients.size());
		assertEquals(1, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing1", 0)));
		assertEquals(2, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing2", 0)));
		assertEquals(3, (int)conversion.ingredients.get(NormalizedSimpleStack.getFor("ing3", 0)));
	}
}