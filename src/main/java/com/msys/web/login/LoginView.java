package com.msys.web.login;

import com.msys.repository.UserRepository;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SpringComponent
@UIScope
public class LoginView extends CustomComponent implements View, Button.ClickListener {

	private UserRepository userRepo;
	private static final long serialVersionUID = 1L;
	public static final String NAME = "login";
	private final TextField user;
	private final PasswordField password;
	private final Button loginButton;
	public static String userName;
	public static String pass;

	public void setUserRepository(UserRepository user) {
		this.userRepo = user;
	}

	public LoginView() {
		setSizeFull();

		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. joe@email.com)");
		user.addValidator(new EmailValidator("Username must be an email address"));
		user.setInvalidAllowed(false);

		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		loginButton = new Button("Login", this);

		VerticalLayout fields = new VerticalLayout(user, password, loginButton);
		fields.setCaption("Please login to access the application. (test@test.com/passw0rd)");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		user.focus();
	}

	private static final class PasswordValidator extends AbstractValidator<String> {
		private static final long serialVersionUID = 1L;

		public PasswordValidator() {
			super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
			if (value != null && (value.length() < 8 || !value.matches(".*\\d.*"))) {
				return false;
			}
			return true;
		}

		@Override
		public Class<String> getType() {
			return String.class;
		}
	}

	@Override
	public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {

		if (!user.isValid() || !password.isValid()) {
			return;
		}

		String username = user.getValue();
		String password = this.password.getValue();

		boolean isValid = username.equals(userRepo.findByEmail(username).getEmail())
				&& password.equals(userRepo.findByEmail(username).getPassword());

		if (isValid) {
			getSession().setAttribute("user", username);
			getUI().getNavigator().navigateTo(LoginMainView.NAME);
		} else {
			this.password.setValue(null);
			this.password.focus();
		}
	}
}
