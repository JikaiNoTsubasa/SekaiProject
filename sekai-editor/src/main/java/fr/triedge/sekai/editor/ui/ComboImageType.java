package fr.triedge.sekai.editor.ui;

public class ComboImageType {

	public String name;
	public int value;
	public ComboImageType(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.name+" ["+this.value+"]";
	}
}
