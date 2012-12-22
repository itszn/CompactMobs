package compactMobs.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import compactMobs.DefaultProps;
import compactMobs.Containers.ContainerBreeder;
import compactMobs.TileEntity.TileEntityBreeder;

public class GuiBreeder extends GuiContainer {

    private TileEntityBreeder te;
    private EntityPlayer player;

    public GuiBreeder(InventoryPlayer player, TileEntityBreeder tileEntity) {
        super(new ContainerBreeder(player, tileEntity));
        this.te = tileEntity;
        this.player = player.player;
        ySize = 240;
        xSize = 175;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString("Breeder", 10, 10, 0x404040);
        super.drawGuiContainerForegroundLayer(par1, par2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        int i = mc.renderEngine.getTexture(DefaultProps.GUI_TEXTURES + "/breeder_gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(i);
        //int j = (width - xSize) / 2;
        //int k = (height - ySize) / 2;
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        /*
         * if(true) { float l = getBurnTimeRemainingScaled(17);
         * drawTexturedModalRect(85+j, 32+k, xSize, 0, 6, (int)l); l =
         * getBurnTimeRemainingScaled(15); drawTexturedModalRect(63+j, 56+k,
         * xSize, 16, (int)l, 6); l++; drawTexturedModalRect(j+114 - (int)l,
         * k+56, xSize+15-(int)l, 22, 16, 6);
        }
         */
    }
    /*
     * public float getBurnTimeRemainingScaled(int i) {
     * System.out.println((float)(((float)i)/200.0)*InterclassLibrary.getInstance().tileEntityInbuenerTicks);
     * return
     * (float)(((float)i)/200.0)*InterclassLibrary.getInstance().tileEntityInbuenerTicks;
	}
     */
}
