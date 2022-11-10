package fr.triedge.sekai.common.net;

import fr.triedge.sekai.common.model.User;

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
