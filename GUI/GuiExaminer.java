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
import compactMobs.Containers.ContainerExaminer;
import compactMobs.TileEntity.TileEntityExaminer;

public class GuiExaminer extends GuiContainer {

    private TileEntityExaminer te;
    private EntityPlayer player;

    public GuiExaminer(InventoryPlayer player, TileEntityExaminer tileEntity) {
        super(new ContainerExaminer(player, tileEntity));
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
    	fontRenderer.drawString("Mob Examiner", 10, 10, 0x404040);
    	ArrayList<String> text = new ArrayList();
    	boolean armorFlag = false;
    	if(this.te.ItemStacks[1]!=null) {
    		ItemStack itemstack = te.ItemStacks[1];
    		if (itemstack.hasTagCompound()) {
	    		if (itemstack.getTagCompound().hasKey("infoVisable")) {
	    			
	    			if (itemstack.getTagCompound().hasKey("entityTags")) {
	    				NBTTagCompound nbttag = itemstack.getTagCompound().getCompoundTag("entityTags");
	    				if (nbttag != null) {
	    					/*
							if (nbttag.hasKey(""))
	    						text.add(": "+nbttag.get(""));
	    					 */
	    					if (nbttag.hasKey("Health"))
	    						text.add("Health: "+nbttag.getShort("Health"));
	    					if (nbttag.hasKey("CustomName"))
	    						if (!nbttag.getString("CustomName").equals(""))
	    							text.add("Custom Name: "+nbttag.getString("CustomName"));
	    					if (nbttag.hasKey("Owner"))
	    						text.add((nbttag.getString("Owner").equals("")?"Wild":"Owner: "+nbttag.getString("Owner")));
	    					if (nbttag.hasKey("powered"))
	    						text.add("Lightning Charged");
	    					
	    					if (nbttag.hasKey("CatType")) {
	    						int c = nbttag.getInteger("CatType");
	    						String[] cats = {"Wild Ocelot","Tuxuedo","Tabby","Siamese"};
	    						text.add("Cat Type: "+cats[c]);
	    					}
	    					if (nbttag.hasKey("Saddle")&&nbttag.getBoolean("Saddle"))
	    						text.add("Saddled");
	    					if (nbttag.hasKey("Sheared")&&nbttag.getBoolean("Sheared"))
	    						text.add("Sheared");
	    					if (nbttag.hasKey("Color")) {
	    						String[] colors = {"White","Orange","Magenta","Light Blue", "Yellow","Lime","Pink","Gray","Light Gray","Cyan","Purple","Blue","Brown","Green","Red","Black"};
	    						text.add("Color: "+colors[nbttag.getByte("Color")]);
	    					}
	    					if (nbttag.hasKey("Size")) {
	    						String[] sizes = {"Small","Medium","Large"};
	    						int s = nbttag.getInteger("Size");
	    						text.add("Size: "+(s<3?sizes[s]:"Huge"));
	    					}
	    					if (nbttag.hasKey("Angry")&&nbttag.getBoolean("Angry"))
	    							text.add("Hostile");
    						if (nbttag.hasKey("Anger")&&nbttag.getShort("Anger")!=0)
    							text.add("Hostile");
	    					
	    					if (nbttag.hasKey("Profession")) {
	    						int p = nbttag.getInteger("Profession");
	    						String[] jobs = {"Farmer","Librarian","Priest","Blacksmith","Butcher","Default"};
	    						text.add("Profession: "+jobs[p]);
	    					}
	    					if (nbttag.hasKey("Offers")) {
	    						NBTTagCompound offers = nbttag.getCompoundTag("Offers");
	    						NBTTagList list = offers.getTagList("Recipes");
	    						
	    						text.add("Trades: "+list.tagCount());
	    					}
	    					if(nbttag.hasKey("Equipment")) { 
	    						NBTTagList list = nbttag.getTagList("Equipment");
	    						armorFlag=true;
	    						for (int i=0; i<5;i++) {
	    							NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
	    							if(!tag.hasNoTags()) {
	    								te.ItemStacks[i+2]=ItemStack.loadItemStackFromNBT(tag).copy();
	    							}
	    							else
	    							{
	    								te.ItemStacks[i+2]=null;
	    							}
	    						}
	    						
	    					}
	    					if (nbttag.hasKey("carried")) {
	    						if (Block.blocksList[nbttag.getShort("carried")]!=null) {
	    							text.add("Block Carried: "+Block.blocksList[nbttag.getShort("carried")].getLocalizedName());
	    							if (nbttag.hasKey("carried")) {
			    						if (Block.blocksList[nbttag.getShort("carried")]!=null)
			    							te.ItemStacks[2] = new ItemStack(Block.blocksList[nbttag.getShort("carried")],1,nbttag.getShort("carriedData"));
			    					}
	    						}
	    						
	    					}
	    				}
	    			}	
	    		}
	    		NBTTagCompound nbttag1 = itemstack.getTagCompound();
	    		
	    		if (nbttag1.hasKey("entityGrowingAge")) {
	                float age = nbttag1.getInteger("entityGrowingAge");
	                int percent = 100;
	            	if (age >= 0) {
	                    percent = (int) Math.floor(((6000 - age) / 6000) * 100);
	                    text.add("Fertility " + String.valueOf(percent) + "%");
	                }
	                if (age < 0) {
	                    percent = (int) Math.floor((((-24000 - age) / -24000) * 100));
	                    text.add("Growth " + String.valueOf(percent) + "%");
	                }
	                
	            }
    		}
    		
    	}
    	if (!armorFlag) {
    		for (int i=0; i<5;i++) {
					te.ItemStacks[i+2]=null;
			}
    	}
    	if(te.ItemStacks[2]!=null)
			fontRenderer.drawString("Held", 10, 111, 0x404040);
    	for (int i=0; i<text.size(); i++)
    		fontRenderer.drawString(text.get(i), 30, 42+i*10, 0x404040);
    }
		
	


    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(DefaultProps.GUI_TEXTURES + "/examiner_gui.png");
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



