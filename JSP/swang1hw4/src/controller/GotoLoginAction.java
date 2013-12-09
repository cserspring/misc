package controller;

import javax.servlet.http.HttpServletRequest;
import model.*;
public class GotoLoginAction extends Action {

	public GotoLoginAction(Model model){
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "gotoLogin.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		return "login.jsp";
	}

}
