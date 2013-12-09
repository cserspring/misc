package controller;

import javax.servlet.http.HttpServletRequest;

import model.Model;

public class GotoChangePasswordAction extends Action{
	public GotoChangePasswordAction(Model model){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "gotoChangePassword.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "changePassword.jsp";
	}
}
