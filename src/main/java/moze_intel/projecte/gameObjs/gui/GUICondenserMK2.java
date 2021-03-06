package moze_intel.projecte.gameObjs.gui;

import moze_intel.projecte.PECore;
import moze_intel.projecte.gameObjs.container.CondenserMK2Container;
import moze_intel.projecte.gameObjs.tiles.CondenserMK2Tile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICondenserMK2 extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation(PECore.MODID.toLowerCase(), "textures/gui/condenser_mk2.png");
	private final CondenserMK2Container container;

	public GUICondenserMK2(InventoryPlayer invPlayer, CondenserMK2Tile tile)
	{
		super(new CondenserMK2Container(invPlayer, tile));
		this.container = ((CondenserMK2Container) inventorySlots);
		this.xSize = 255;
		this.ySize = 233;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = container.getProgressScaled();
		this.drawTexturedModalRect(x + 33, y + 10, 0, 235, progress, 10);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2)
	{
		int toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;
		this.fontRendererObj.drawString(Integer.toString(toDisplay), 140, 10, 4210752);
	}
}
