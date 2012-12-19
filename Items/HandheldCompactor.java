package compactMobs.Items;

import compactMobs.CompactMobsCore;
import compactMobs.DefaultProps;
import cpw.mods.fml.common.registry.TickRegistry;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAuraFX;
import net.minecraft.src.EntityBreakingFX;
import net.minecraft.src.EntityBubbleFX;
import net.minecraft.src.EntityCloudFX;
import net.minecraft.src.EntityCritFX;
import net.minecraft.src.EntityDiggingFX;
import net.minecraft.src.EntityDropParticleFX;
import net.minecraft.src.EntityEnchantmentTableParticleFX;
import net.minecraft.src.EntityExplodeFX;
import net.minecraft.src.EntityFX;
import net.minecraft.src.EntityFlameFX;
import net.minecraft.src.EntityFootStepFX;
import net.minecraft.src.EntityHeartFX;
import net.minecraft.src.EntityLavaFX;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityNoteFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.EntityReddustFX;
import net.minecraft.src.EntitySmokeFX;
import net.minecraft.src.EntitySnowShovelFX;
import net.minecraft.src.EntitySpellParticleFX;
import net.minecraft.src.EntitySplashFX;
import net.minecraft.src.EntitySuspendFX;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.IEventListener;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class HandheldCompactor extends Item{
	public HandheldCompactor(int par1) {
        super(par1);
        this.setMaxDamage(250);
        maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return "Handheld Compactor";
    }

    public String getTextureFile() {
        return DefaultProps.ITEM_TEXTURES + "/items.png";
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {

        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

        return par1ItemStack;
    }
    
    /*@Override
    public boolean isDamageable() 
    {
    	return true;
    }
    
    @Override
    public boolean isRepairable()
    {
    	return false;
    }*/
    
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 50;
    }
    
    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }
    
    
    @Override
    public void onUpdate(ItemStack stack, World par2World, Entity eplayer, int par4, boolean par5) 
    {
    	EntityPlayer player = (EntityPlayer) eplayer;
    	/*ItemStack stack2 = player.getItemInUse();
    	if (stack2 != null)
    	{
    		if (stack2.equals(stack))
    		{
    			CompactMobsCore.instance.cmLog.info(String.valueOf(player.getCurrentEquippedItem().getItem().getMaxDamage()));
    			if (stack.getItemDamage() == 0)
    	    	{
    	    		stack.setItemDamage(stack.getItem().getMaxDamage()-10);
    	    	}
    	    	else if (stack.getItemDamage() > 1)
    	    	{
    	    		if (stack.getItemDamage() - 10 <= 1)
    	    		{
    	    			stack.setItemDamage(1);
    	    		}
    	    		else
    	    		{
    	    			//stack.setItemDamage(stack.getItemDamage() - 10);
    	    			stack.damageItem(-10, player);
    	    		}
    	    	}
    		}
    	}
    	if (stack2 != null && stack2.equals(stack))
    	{
    		
    	}*/
    	
    	//else 
    	//CompactMobsCore.instance.cmLog.info(String.valueOf(stack.getItemDamage() == stack.getItem().getMaxDamage()-1));
    	/*if (stack.getItemDamage() < stack.getItem().getMaxDamage()-1 && stack.getItemDamage() != 0)
    	{
    		stack.setItemDamage(stack.getItemDamage()+1);
    	}
    	else if (stack.getItemDamage() == stack.getItem().getMaxDamage()-1)
    	{
    		stack.setItemDamage(0);
    	}*/
    	ItemStack stack2 = player.getCurrentEquippedItem();//player.getItemInUse();
    	NBTTagCompound nbttag;
    	int charge = 0;
    	
    	/*if (player.isUsingItem() && stack2.equals(stack))
    	{
    		double yaw = player.rotationYawHead;
        	double y = player.posY;
        	double xChange = 1, zChange=0, xVel = 0, zVel = 0;
        	World world = player.worldObj;
        	
        	xChange = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
        	zChange = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
        	
        	
        	xVel = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.1F);
        	zVel = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.1F);
        	
        	
        	//CompactMobsCore.instance.cmLog.info(String.valueOf(types[stack.stackSize+1]));
        	CompactMobsCore.instance.proxy.spawnParticle("smoke", player.posX+xChange+world.rand.nextDouble()/2-.25D, y+world.rand.nextDouble()/4-.125D, player.posZ + zChange+world.rand.nextDouble()/2-.25D, xVel, 0F, zVel, 5);
    		
    	}*/
    	//CompactMobsCore.instance.cmLog.info(String.valueOf(player.isUsingItem()));
    	
    	if (!player.isUsingItem())
    	{
    		
	    	if (stack.hasTagCompound())
	    	{
	    		nbttag = stack.stackTagCompound;
	    	}
	    	else
	    	{
	    		nbttag = new NBTTagCompound();
	    	}
	    	if (nbttag.hasKey("charge"))
	    	{
	    		charge = nbttag.getInteger("charge");
	    		if (charge > 0)
	    		{
		    		if (charge >= stack.getItem().getMaxDamage()-1)
			    	{
			    		charge = 0;
			    	}
			    	else
			    	{
			    		charge++;
			    	}
			    	
			    	nbttag.setInteger("charge", charge);
			    	stack.setTagCompound(nbttag);
	    		}
	    	}
	    	
	    	
    	}
    	else 
    		{
    		if (!stack2.isItemEqual(stack))
	    	{
	    		
		    	if (stack.hasTagCompound())
		    	{
		    		nbttag = stack.stackTagCompound;
		    	}
		    	else
		    	{
		    		nbttag = new NBTTagCompound();
		    	}
		    	if (nbttag.hasKey("charge"))
		    	{
		    		charge = nbttag.getInteger("charge");
		    		if (charge > 0)
		    		{
			    		if (charge >= stack.getItem().getMaxDamage()-1)
				    	{
				    		charge = 0;
				    	}
				    	else
				    	{
				    		charge++;
				    	}
				    	
				    	nbttag.setInteger("charge", charge);
				    	stack.setTagCompound(nbttag);
		    		}
		    	}
	    	}
		}
    	
    	if (stack.hasTagCompound())
    	{
    		NBTTagCompound nbttag2 = stack.stackTagCompound;
    		if (nbttag2.hasKey("charge"))
    		{
				//CompactMobsCore.instance.cmLog.info(String.valueOf(nbttag.getInteger("charge")));
				stack.setItemDamage(nbttag2.getInteger("charge"));
    			
    		}
    		
    	}
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
   
    
    @Override
    //public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
    //public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9,float par10) {
    public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count){
    	/*NBTTagCompound nbttag;
    	int charge = 0;
    	if (stack.hasTagCompound())
    	{
    		nbttag = stack.stackTagCompound;
    	}
    	else
    	{
    		nbttag = new NBTTagCompound();
    	}
    	if (nbttag.hasKey("charge"))
    	{
    		charge = nbttag.getInteger("charge");
    	}
    	
    	if (!player.getCurrentEquippedItem().isItemDamaged())
    	{
    		player.getCurrentEquippedItem().setItemDamage(/*player.getCurrentEquippedItem().getItem().getMaxDamage()-1* /990);
    		//player	.getCurrentEquippedItem().damageItem(-10, player);
    	}
    	else if (player.getCurrentEquippedItem().getItemDamage() < 1)
    	{
    		if (player.getCurrentEquippedItem().getItemDamage() < 10)
    		{
    			player.getCurrentEquippedItem().setItemDamage(1);
    		}
    		else
    		{
    			player.getCurrentEquippedItem().setItemDamage(player.getCurrentEquippedItem().getItemDamage()-10);
    			//player.getCurrentEquippedItem().damageItem(-10, player);
    		}
    	}
    	CompactMobsCore.instance.cmLog.info("***");
    	CompactMobsCore.instance.cmLog.info("1: "+String.valueOf(charge));
    	CompactMobsCore.instance.cmLog.info("2: "+String.valueOf(charge == 0));
    	CompactMobsCore.instance.cmLog.info("3: "+String.valueOf(charge > 1));
    	if (charge == 0)
    	{
    		CompactMobsCore.instance.cmLog.info("2a: "+String.valueOf(player.getCurrentEquippedItem().getItem().getMaxDamage()));
    		charge = player.getCurrentEquippedItem().getItem().getMaxDamage()-10;
    		CompactMobsCore.instance.cmLog.info("2b: "+String.valueOf(charge));
    	}
    	else if (charge > 1)
    	{
    		CompactMobsCore.instance.cmLog.info("3a: "+String.valueOf(charge - 10 <1));
    		if (charge - 10 <= 1)
    		{
    			CompactMobsCore.instance.cmLog.info("3aa1: "+String.valueOf(charge));
    			charge = 1;
    			CompactMobsCore.instance.cmLog.info("3aa2: "+String.valueOf(charge));
    		}
    		else
    		{
    			CompactMobsCore.instance.cmLog.info("3ab1: "+String.valueOf(charge));
    			charge -= 10;
    			CompactMobsCore.instance.cmLog.info("3ab2: "+String.valueOf(charge));
    		}
    	}
    	CompactMobsCore.instance.cmLog.info("4: "+String.valueOf(charge));
    	CompactMobsCore.instance.cmLog.info("***");
    	nbttag.setInteger("charge", charge);
    	stack.setTagCompound(nbttag);*/
    	
    	double yaw = player.rotationYawHead;
    	double y = player.posY;
    	double xChange = 1, zChange=0, xVel = 0, zVel = 0;
    	World world = player.worldObj;
    	//CompactMobsCore.instance.cmLog.info(String.valueOf(player.getCurrentEquippedItem().getItemDamage()));
    	
    	String types[] = {
    			"bubble",
                "suspended","depthsuspend","townaura","crit","magicCrit","smoke","mobSpell","mobSpellAmbient","spell","instantSpell","witchMagic","note","portal","enchantmenttable","explode",
                "flame","lava","footstep","splash","largesmoke","cloud","reddust","snowballpoof","dripWater","dripLava","snowshovel","slime","heart","angryVillager","happyVillager","iconcrack_","tilecrack_"
    	};
    	
    	//world.s
    	
    	/*if(yaw>=90)
    	{
    		yaw -= 90;
    		xChange = -2*Math.cos(yaw);
    		zChange = -2*Math.sin(yaw);
    	}else if(yaw<=-90)
    	{
    		yaw = Math.abs(yaw)-90;
    		xChange = 2*Math.cos(yaw);
    		zChange = -2*Math.sin(yaw);
    	}else if(yaw<90 && yaw>=0)
    	{
    		xChange = -2*Math.sin(yaw);
    		zChange = 2*Math.cos(yaw);
    	}else if(yaw<=0 && yaw>-90)
    	{
    		yaw = Math.abs(yaw);
    		xChange = 2*Math.sin(yaw);
    		zChange = 2*Math.cos(yaw);
    	}*/
    	xChange = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
    	zChange = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * 1F);
    	
    	
    	xVel = (double)(MathHelper.cos((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.1F);
    	zVel = (double)(MathHelper.sin((player.rotationYaw+90) / 180.0F * (float)Math.PI) * -.1F);
    	
    	
    	//CompactMobsCore.instance.cmLog.info(String.valueOf(types[stack.stackSize+1]));
    	CompactMobsCore.instance.proxy.spawnParticle("smoke", player.posX+xChange+world.rand.nextDouble()/2-.25D, y+world.rand.nextDouble()/4-.125D, player.posZ + zChange+world.rand.nextDouble()/2-.25D, xVel, 0F, zVel, 5);
		//return true;
    	
    }
    
    
    /*@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityLiving entity)
    {
    	
    	return true;
    }*/
    
    
    /*@ForgeSubscribe
    public void invoke(EntityInteractEvent event)
    {
    	CompactMobsCore.instance.cmLog.info("EVENT");
    	
    		//EntityInteractEvent event = (EntityInteractEvent)eevent;
    		EntityPlayer player = event.entityPlayer;
    		if (event.target instanceof EntityLiving)
    		{
    			EntityLiving entity = (EntityLiving) event.target;
    			if (player.getCurrentEquippedItem().getItem() == CompactMobsItems.handCompactor)
    			{
    				spawnParticles(player.worldObj, entity, player);
    			}
    		}
    
    }
    
    
    public void spawnParticles(World world, EntityLiving entity, EntityPlayer player) {
        //CompactMobsCore.instance.cmLog.info("Got");
        double xv, yv, zv;
        double py = player.posY, px = player.posX, pz = player.posZ;
        double x = entity.posX, y = entity.posY, z = entity.posZ;
        if (py - y > 0) {
            yv = (py - y) / 10;
        } else {
            yv = 0;
        }
        if (px - x > 0) {
            xv = (px - x) / 10;
        } else if (px - x < 0) {
            xv = (px - x) / 10;
        } else {
            xv = 0;
        }
        if (pz - z > 0) {
            zv = (pz - z) / 10;
        } else if (pz - z < 0) {
            zv = (pz - z) / 10;
        } else {
            zv = 0;
        }
        CompactMobsCore.instance.proxy.spawnParticle("explode", x + .5D, y + .5D, z + .5D, xv, yv, zv, 10);
        //this.worldObj.spawnParticle("smoke", this.xCoord+1.5D, this.yCoord+.5D, this.zCoord+.5, -.1, 0, 0);
        //world.spawnParticle("smoke", x+.5D, y+.5D, z+.5D, xv, yv, zv);
    }*/
    

}
