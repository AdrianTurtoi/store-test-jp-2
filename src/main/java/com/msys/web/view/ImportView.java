package com.msys.web.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import com.vaadin.ui.Upload;
import com.msys.entity.Article;
import com.msys.entity.Order;
import com.msys.entity.OrderItem;
import com.msys.entity.Supplier;
import com.msys.repository.OrderItemRepository;
import com.msys.repository.OrderRepository;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class ImportView extends CustomComponent implements View, Upload.Receiver {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "import";
	private final Grid grid = new Grid();
	private final Grid grid1 = new Grid();
	private OrderRepository orderRepo;
	private OrderItemRepository orderItemRepo;

	public void setOrderRepository(OrderRepository order) {
		this.orderRepo = order;
	}

	public void setOrderItemRepository(OrderItemRepository order) {
		this.orderItemRepo = order;
	}

	final Upload upload = new Upload("Upload the file here", this);

	@PostConstruct
	public void init() {
	}

	public ImportView() {
		VerticalLayout mainLayout = new VerticalLayout(upload, grid, grid1);
		mainLayout.setSizeFull();
		mainLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(grid1, Alignment.MIDDLE_CENTER);

		CssLayout csLay = new CssLayout(mainLayout);
		csLay.setSizeFull();
		setCompositionRoot(csLay);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@Override
	@Transactional
	public OutputStream receiveUpload(String filename, String mimeType) {
		PipedOutputStream ps = null;
		PipedInputStream is = null;

		String[] orderItems;
		String[] orderDetails;
		Set<OrderItem> setOrderItem = new HashSet<OrderItem>();

		try {
			File f = new File(filename);
			//DataInputStream dis = new DataInputStream(new FileInputStream(f.getAbsolutePath()));
			InputStream inputStream       = new FileInputStream(f.getAbsolutePath());
			Reader      inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader dis = new BufferedReader(inputStreamReader);
	
			String input;
			ps = new PipedOutputStream();
			is = new PipedInputStream(ps);
			PrintStream os = new PrintStream(ps);
			
			while ((input = dis.readLine()) != null) {
				
				if (input.contains("oc;")) {
					
					orderDetails = input.substring(3, input.indexOf("[")).split(";");

					SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
					Order order1 = new Order();
					order1.setDeliveryDate(formatter.parse(orderDetails[0]));
					order1.setValidFrom(formatter.parse(orderDetails[1]));
					order1.setValidTo(formatter.parse(orderDetails[2]));
					order1.setAvalabileAt(2);

					Matcher m = Pattern.compile("(?<=\\[)(.+?)(?=\\])").matcher(input);

					while (m.find()) {

						orderItems = m.group().split(";");

						OrderItem orderItem1 = new OrderItem();
						orderItem1.setArticles(new Article(Integer.parseInt(orderItems[0])));
						orderItem1.setQuantity(Integer.parseInt(orderItems[1]));
						orderItem1.setSuppliers(new Supplier(Integer.parseInt(orderItems[2])));
						orderItem1.setOrders(order1);

						setOrderItem.add(orderItem1);						
					}

					order1.setOrderItems(setOrderItem);
					orderRepo.save(order1);

					grid.setHeight(300, Unit.PIXELS);
					grid.setWidth(70, Unit.PERCENTAGE);
					grid.setColumns("id", "deliveryDate", "validFrom", "validTo");
					grid.setContainerDataSource(new BeanItemContainer<Order>(Order.class, orderRepo.findAll()));

					grid.addSelectionListener(e -> {
						if (e.getSelected().isEmpty()) {
							grid1.setVisible(false);
						} else {
							Order orderSelected = (Order) e.getSelected().iterator().next();
							grid1.setHeight(30, Unit.PERCENTAGE);
							grid1.setWidth(100, Unit.PERCENTAGE);
							List<OrderItem> orderItemsList = orderItemRepo.findByOrders(orderSelected);
							if (orderItemsList != null) {
								final BeanItemContainer<OrderItem> ds = new BeanItemContainer<OrderItem>(
										OrderItem.class, orderItemsList);

								ds.addNestedContainerBean("articles");
								ds.addNestedContainerBean("suppliers");
								grid1.setColumns("articles.articleNo", "articles.articleName", "quantity",
										"suppliers.supplierNo", "suppliers.supplierName");
								grid1.setContainerDataSource(ds);
							}

						}
					});

				} else if (input.contains("on;")) {

				} else if (input.contains("os;")) {

				} else if (input.contains("ol;")) {

				}
			}
			os.close();
		} catch (Exception e) {
			System.out.println("StringUtils reverse: " + e);
		}
		return ps;
	}

}
