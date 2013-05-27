package compactMobs.GUI;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import compactMobs.Containers.ContainerBreeder;
import compactMobs.Containers.ContainerNamer;
import compactMobs.TileEntity.TileEntityBreeder;
import compactMobs.TileEntity.TileEntityNamer;

public class GuiNamer extends GuiContainer implements ICrafting {

    private TileEntityNamer te;
    private EntityPlayer player;
    private GuiTextField mobNameField;

    public GuiNamer(InventoryPlayer player, TileEntityNamer tileEntity) {
        super(new ContainerNamer(player, tileEntity));
        this.te = tileEntity;
        this.player = player.player;
        ySize = 166;
        xSize = 176;
    }
    
    @Override
    public void initGui()
    {
    	super.initGui();
    	Keyboard.enableRepeatEvents(true);
    	int i = (this.width - this.xSize)/2;
    	int j = (this.height - this.ySize)/2;
    	this.mobNameField = new GuiTextField(this.fontRenderer, i+26, j+24, 123, 12);
    	this.mobNameField.setTextColor(-1);
    	this.mobNameField.func_82266_h(-1);
    	this.mobNameField.setMaxStringLength(30);
    	this.mobNameField.setText(te.text==null?"":te.text);
    	this.mobNameField.setEnableBackgroundDrawing(false);
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
    	fontRenderer.drawString("Mob Namer", 10, 10, 0x404040);
        
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(DefaultProps.GUI_TEXTURES + "/namer_gui.png");
        //int j = (width - xSize) / 2;
        int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		this.drawTexturedModalRect(j + 23, k + 20, 0, this.ySize + (this.te.ItemStacks[0]!=null ? 0 : 16), 130, 16);
		
		
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
    
    @Override
    protected void keyTyped(char ch, int i)
    {
    	if (this.mobNameField.textboxKeyTyped(ch, i))
    	{
    		CompactMobsCore.instance.proxy.updateNamerText(this.mobNameField.getText(), te);
    		//this.te.updateText(this.mobNameField.getText());
    	}
    	else
    	{
    		super.keyTyped(ch, i);
    	}
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        this.mobNameField.mouseClicked(par1, par2, par3);
        te.runNamer();
    }
    
    @Override
    public void drawScreen(int i, int j, float k)
    {
    	super.drawScreen(i, j, k);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	this.mobNameField.drawTextBox();
    }

	@Override
	public void sendContainerAndContentsToPlayer(Container container, List list) {
		this.sendSlotContents(container, 0, container.getSlot(0).getStack());
		
	}

	@Override
	public void sendSlotContents(Container container, int i, ItemStack itemstack) {
		if (i == 0)
        {
			boolean flag = false;
			if (itemstack.stackTagCompound!=null)
				if (itemstack.stackTagCompound.hasKey("name"))
					flag = true;
			if (flag)		
				this.mobNameField.setText(itemstack == null ? "" : itemstack.stackTagCompound.getString("name"));
			else
				this.mobNameField.setText("");

            if (itemstack != null)
            {
            	CompactMobsCore.instance.proxy.updateNamerText(this.mobNameField.getText(), te);
                //this.mc.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", this.mobNameField.getText().getBytes()));
            }
        }
	}

	@Override
	public void sendProgressBarUpdate(Container container, int i, int j) {
		// TODO Auto-generated method stub
		
	}
}



