package fr.triedge.sekai.editor.model;

import java.util.ArrayList;


public class CharacterAnimation {

	private ArrayList<CharacterAnimationFrame> frames = new ArrayList<>();
	private AnimationType animationType;

	public ArrayList<CharacterAnimationFrame> getFrames() {
		return frames;
	}

	public void setFrames(ArrayList<CharacterAnimationFrame> frames) {
		this.frames = frames;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}
}
