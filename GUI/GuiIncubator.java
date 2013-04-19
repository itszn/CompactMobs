
package compactMobs.GUI;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import compactMobs.DefaultProps;
import compactMobs.Containers.ContainerIncubator;
import compactMobs.TileEntity.TileEntityIncubator;

public class GuiIncubator extends GuiContainer {

    private TileEntityIncubator te;
    private EntityPlayer player;

    public GuiIncubator(InventoryPlayer player, TileEntityIncubator tileEntity) {
        super(new ContainerIncubator(player, tileEntity));
        this.te = tileEntity;
        this.player = player.player;
        ySize = 240;
        xSize = 175;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString("Incubator", 10, 10, 0x404040);
        super.drawGuiContainerForegroundLayer(par1, par2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(DefaultProps.GUI_TEXTURES + "/incubator_gui.png");
        //int j = (width - xSize) / 2;
        //int k = (height - ySize) / 2;
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
    }
    
}

