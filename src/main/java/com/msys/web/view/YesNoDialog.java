package com.msys.web.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class YesNoDialog extends Window implements Button.ClickListener {

	Callback callback;
	Button yes = new Button("Yes", this);
	Button no = new Button("No", this);

	public YesNoDialog(String caption, String question, Callback callback) {
		super(caption);

		setModal(true);

		this.callback = callback;
		VerticalLayout vl = new VerticalLayout();
		if (question != null) {
			vl.addComponent(new Label(question));
		}

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(yes);
		hl.addComponent(no);
		vl.addComponent(hl);
		setContent(vl);
	}

	public void buttonClick(ClickEvent event) {
		if (getParent() != null) {
			// ((Window) getParent()).removeWindow(this);
			close();
		}
		callback.onDialogResult(event.getSource() == yes);
	}

	public interface Callback {

		public void onDialogResult(boolean resultIsYes);
	}

}
