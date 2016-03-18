package com.msys.web.view;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.msys.entity.Article;
import com.msys.entity.Order;
import com.msys.entity.OrderItem;
import com.msys.entity.Supplier;
import com.msys.repository.OrderItemRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SpringComponent
@UIScope
public class ImportViewAddItem extends Window {

	private static final long serialVersionUID = 1L;
	final UI ui = UI.getCurrent();
	
	private OrderItemRepository orderItemRepo;
	
	public void setOrderItemRepository (OrderItemRepository orderItemRepo) {
		this.orderItemRepo = orderItemRepo;
	}
	
	public static final String NAME = "importAddItem";

	@Autowired 
	public ImportViewAddItem(OrderItemRepository repository) {
		super("Add Item"); // Set window caption
		this.orderItemRepo = repository;		
		center();

		HorizontalLayout contentH = new HorizontalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		// Some basic content for the window
		VerticalLayout contentV = new VerticalLayout();
		VerticalLayout contentV1 = new VerticalLayout();
		TextField ArticleNo = new TextField();
		Label ArticleNoLabel = new Label("ArticleNo");
		TextField ArticleName = new TextField();
		Label ArticleNameLabel = new Label("ArticleName");
		TextField Quantity = new TextField();
		Label QuantityLabel = new Label("Quantity");
		TextField SupplierNo = new TextField();
		Label SupplierNoLabel = new Label("SupplierNo");
		TextField SupplierName = new TextField();
		Label SupplierNameLabel = new Label("SupplierName");
		Button ok = new Button("Close");
		Button save = new Button("Save");
		contentV.addComponent(ArticleNoLabel);
		contentV.addComponent(ArticleNo);
		contentV.addComponent(ArticleNameLabel);
		contentV.addComponent(ArticleName);
		contentV.addComponent(QuantityLabel);
		contentV.addComponent(Quantity);
		contentV.setMargin(true);
		contentV1.addComponent(SupplierNoLabel);
		contentV1.addComponent(SupplierNo);
		contentV1.addComponent(SupplierNameLabel);
		contentV1.addComponent(SupplierName);
		buttonLayout.addComponent(ok);
		buttonLayout.addComponent(save); 
		contentV1.addComponent(buttonLayout);
		contentV1.setMargin(true);
		contentH.addComponent(contentV);
		contentH.addComponent(contentV1);

		setContent(contentH);

		// Disable the close button
		setClosable(false);

		save.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			@Transactional
			public void buttonClick(ClickEvent event) {
				OrderItem orderItem1 = new OrderItem();
				Article article = new Article(Integer.parseInt(ArticleNo.getValue()), ArticleName.getValue());
				orderItem1.setArticles(article);
				orderItem1.setQuantity(Integer.parseInt(Quantity.getValue()));
				Supplier supplier = new Supplier(Integer.parseInt(SupplierNo.getValue()), SupplierName.getValue());
				orderItem1.setSuppliers(supplier);
				Order order = (Order) readData(ui, "order");
				orderItem1.setOrders(order);

				orderItemRepo.save(orderItem1);

			}
		});

		ok.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				close(); // Close the sub-window
			}
		});

	}

	public static Object readData(UI ui, String attribute) {
		Object object = ui.getSession().getAttribute(attribute);
		ui.getSession().setAttribute(attribute, null);
		return object;
	} 

}