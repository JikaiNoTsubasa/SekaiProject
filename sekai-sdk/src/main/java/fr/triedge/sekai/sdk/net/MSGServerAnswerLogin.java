package fr.triedge.sekai.sdk.net;


import fr.triedge.sekai.sdk.model.User;

public class MSGServerAnswerLogin {

	public MSGCode code;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
