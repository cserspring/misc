package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RegisterForm extends FormBean {
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String confirm ;
	private String email;
	
	public String getFirstname() { return firstname; }
	public String getLastname()  { return lastname;  }
	public String getUsername()  { return username;  }
	public String getPassword()  { return password;  }
	public String getConfirm()   { return confirm;   }
	public String getEmail()     { return email;     }
	public void setFirstname(String s) { firstname = trimAndConvert(s,"<>\"");  }
	public void setLastname(String s)  { lastname  = trimAndConvert(s,"<>\"");  }
	public void setUsername(String s)  { username  = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirm(String s)   { confirm   = s.trim();                  }
	public void setEmail(String s)     { email     = trimAndConvert(s,"<>\"");  }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0)
			errors.add("User Name is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (confirm == null || confirm.length() == 0)
			errors.add("Confrim password is required");
		if (email == null || email.length() == 0)
			errors.add("Email is required");
		if (firstname == null || firstname.length() == 0)
			errors.add("First Name is required");
		if (lastname == null || lastname.length() == 0)
			errors.add("Last Name is required");
		if (username != null && !username.matches("\\S+"))
			errors.add("User Name doesn't permit blank characters");
		if (password != null && !password.matches("\\S+"))
			errors.add("Password doesn't permit blank characters");
		if (firstname != null && !firstname.matches("\\S+"))
			errors.add("First Name doesn't permit blank characters");
		if (lastname != null && !lastname.matches("\\S+"))
			errors.add("Last Name doesn't permit blank characters");
		if (confirm != null && !confirm.matches("\\S+"))
			errors.add("Confirm Password doesn't permit blank characters");
		if (confirm != null && confirm.length() > 0 && !(confirm.equals(password)))
			errors.add("Password and confirm password are not matched");
		if (email != null && email.length() > 0 && !(email.matches("(\\S)+@(\\S)+")))
			errors.add("Invalid Format for E-mail Address");
		return errors;
	}
}
