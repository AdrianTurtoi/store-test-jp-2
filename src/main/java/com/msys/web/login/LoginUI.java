package com.msys.web.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.msys.repository.OrderItemRepository;
import com.msys.repository.OrderRepository;
import com.msys.repository.UserRepository;
import com.msys.web.login.LoginMainView;
import com.msys.web.view.ImportView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
public class LoginUI extends UI {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Override
	protected void init(VaadinRequest request) {

		Navigator navigator = new Navigator(this, this);

		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(LoginMainView.NAME, LoginMainView.class);
		navigator.addView(ImportView.NAME, ImportView.class);

		navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				View view = event.getNewView();
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = view instanceof LoginView;

				if (view instanceof LoginView) {
					LoginView log = (LoginView) view;
					log.setUserRepository(userRepo);
				}

				if (view instanceof ImportView) {
					ImportView log = (ImportView) view;
					log.setOrderRepository(orderRepo);
				}

				if (view instanceof ImportView) {
					ImportView log = (ImportView) view;
					log.setOrderItemRepository(orderItemRepo);
				}

				if (!isLoggedIn && !isLoginView) {
					getNavigator().navigateTo(LoginView.NAME);
					return false;

				} else if (isLoggedIn && isLoginView) {
					return false;
				}
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});
	}
}