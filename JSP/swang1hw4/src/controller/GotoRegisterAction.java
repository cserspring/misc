package controller;

import javax.servlet.http.HttpServletRequest;

import model.Model;

public class GotoRegisterAction extends Action {
	public GotoRegisterAction(Model model){
		
	}
	@Override
	public String getName() {
		return "gotoRegister.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		return "register.jsp";
	}

}