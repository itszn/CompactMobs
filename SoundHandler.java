package compactMobs;

import java.util.logging.Level;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
	@ForgeSubscribe
	public void onSoundLoad(SoundLoadEvent event) {
		String soundLocation = "mods/compactMobs/sound/";
		String[] screams = {soundLocation+"scream1.ogg",soundLocation+"scream2.ogg",soundLocation+"scream3.ogg",soundLocation+"scream4.ogg",soundLocation+"scream5.ogg"};
		
			for (int i = 0; i<5; i++)
				try{
					event.manager.soundPoolSounds.addSound(screams[i], this.getClass().getResource("/"+screams[i]));
					CompactMobsCore.cmLog.log(Level.INFO, "Loaded sound file: " + screams[i]);
					CompactMobsCore.sounds[i] = "mods.compactMobs.sound.scream";
				}
				catch (Exception e) {
					CompactMobsCore.cmLog.log(Level.WARNING, "Failed loading sound file: " + screams[i]);
				}
	}
}
