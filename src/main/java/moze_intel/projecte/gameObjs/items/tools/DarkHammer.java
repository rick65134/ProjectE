package moze_intel.projecte.gameObjs.items.tools;

import com.google.common.collect.Multimap;
import moze_intel.projecte.api.state.PEStateProps;
import moze_intel.projecte.api.state.enums.EnumMatterType;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DarkHammer extends PEToolBase
{
	public DarkHammer() 
	{
		super("dm_hammer", (byte)2, new String[] {});
		this.setNoRepair();
		this.peToolMaterial = "dm_tools";
		this.harvestMaterials.add(Material.IRON);
		this.harvestMaterials.add(Material.ANVIL);
		this.harvestMaterials.add(Material.ROCK);

		this.toolClasses.add("hammer");
		this.toolClasses.add("pickaxe");
		this.toolClasses.add("chisel");
	}

	// Only for RedHammer
	protected DarkHammer(String name, byte numCharges, String[] modeDesc)
	{
		super(name, numCharges, modeDesc);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase damaged, EntityLivingBase damager)
	{
		boolean flag = ProjectEConfig.useOldDamage;
		attackWithCharge(stack, damaged, damager, flag ? HAMMER_BASE_ATTACK : 1.0F);
		return true;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		digAOE(stack, world, player, true, 0, hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();
		if ((block == ObjHandler.matterBlock && state.getValue(PEStateProps.TIER_PROP) == EnumMatterType.DARK_MATTER)
				|| block == ObjHandler.dmFurnaceOff
				|| block == ObjHandler.dmFurnaceOn)
		{
			return 1200000.0F;
		}
		
		return super.getStrVsBlock(stack, state);
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack)
	{
		if (slot != EntityEquipmentSlot.MAINHAND)
		{
			return super.getAttributeModifiers(slot, stack);
		}

		byte charge = getCharge(stack);
		float damage = HAMMER_BASE_ATTACK + charge;

		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		if (!ProjectEConfig.useOldDamage)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
		}
		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
		return multimap;
	}
}
