package group_project;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sounds/backgroundSound.wav");
		soundURL[1] = getClass().getResource("/sounds/attackSound.wav");
		soundURL[2] = getClass().getResource("/sounds/deathSound.wav");
		soundURL[3] = getClass().getResource("/sounds/jumpSound.wav");
		soundURL[4] = getClass().getResource("/sounds/keySoundmp3.wav");
		
	}
	
	//
	public void setFile(int i) {
		try {
			if(soundURL[i] == null) {
				System.out.println("Sound file not found for index: " +i);
				return;
			}
			
			AudioInputStream audioIS = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(audioIS);
		}catch(Exception e) {
			System.out.println("Error loading sound at index " + i + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Starts the clip to play 
	public void play() {
		clip.start();
	}
	
	
	//Loops the clip continuously
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	
	//Stops the clip
	public void stop() {
		clip.stop();
	}

}
