package compactMobs.GUI;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import compactMobs.DefaultProps;
import compactMobs.Containers.ContainerEquiper;
import compactMobs.Containers.ContainerExaminer;
import compactMobs.TileEntity.TileEntityEquiper;
import compactMobs.TileEntity.TileEntityExaminer;

public class GuiEquiper extends GuiContainer {

    private TileEntityEquiper te;
    private EntityPlayer player;

    public GuiEquiper(InventoryPlayer player, TileEntityEquiper tileEntity) {
        super(new ContainerEquiper(player, tileEntity));
        this.te = tileEntity;
        this.player = player.player;
        ySize = 228;
        xSize = 176;
    }
    
    @Override
    public void initGui()
    {
    	super.initGui();
    	Keyboard.enableRepeatEvents(true);
    	int i = (this.width - this.xSize)/2;
    	int j = (this.height - this.ySize)/2;
    }
    
    @Override
    public void onGuiClosed()
    {
    	super.onGuiClosed();
    	Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	fontRenderer.drawString("Mob Equiper", 10, 10, 0x404040);
    	fontRenderer.drawString("Upgrade", 116, 57, 0x404040);
    	fontRenderer.drawString("Held", 62, 57, 0x404040);
    	
    	if(te.ItemStacks[2]!=null)
			fontRenderer.drawString("Held", 62, 57, 0x404040);
    }
		
	


    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(DefaultProps.GUI_TEXTURES + "/equiper_gui.png");
        //int j = (width - xSize) / 2;
        int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		
		
		
        //int k = (height - ySize) / 2;
        //drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        /*
         * if(true) { float l = getBurnTimeRemainingScaled(17);
         * drawTexturedModalRect(85+j, 32+k, xSize, 0, 6, (int)l); l =
         * getBurnTimeRemainingScaled(15); drawTexturedModalRect(63+j, 56+k,
         * xSize, 16, (int)l, 6); l++; drawTexturedModalRect(j+114 - (int)l,
         * k+56, xSize+15-(int)l, 22, 16, 6);
        }*/
         
    }
    
}



