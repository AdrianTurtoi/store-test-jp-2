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
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.MultiSelectionModel;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

@SpringComponent
@UIScope
public class ImportView extends CustomComponent implements View, Upload.Receiver {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "import";
	private final Grid grid = new Grid();
	private final Grid grid1 = new Grid();
	private OrderRepository orderRepo;
	private OrderItemRepository orderItemRepo;
	Button deleteButton = new Button("Delete");
	Button insertButton = new Button("Insert");
	Button fetchButton = new Button("Fetch");
	Button editButton = new Button("Edit");
	Button saveButton = new Button("Save");
	final UI ui = UI.getCurrent();
	int i = 1;

	HeaderRow filterRow = grid1.appendHeaderRow();
	TextField filterFieldArticleName = new TextField();
	TextField filterFieldSupplierName = new TextField();

	public void setOrderRepository(OrderRepository order) {
		this.orderRepo = order;
	}

	public void setOrderItemRepository(OrderItemRepository orderItem) {
		this.orderItemRepo = orderItem;
	}

	final Upload upload = new Upload("Upload the file here", this);

	@PostConstruct
	public void init() {
	}

	public ImportView() {

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(fetchButton);
		buttonLayout.addComponent(deleteButton);
		buttonLayout.addComponent(insertButton);
		buttonLayout.addComponent(editButton);
		// buttonLayout.addComponent(saveButton);
		VerticalLayout mainLayout = new VerticalLayout(upload, grid, grid1, buttonLayout);
		mainLayout.setSizeFull();
		mainLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(grid1, Alignment.MIDDLE_CENTER);

		CssLayout csLay = new CssLayout(mainLayout);
		csLay.setSizeFull();
		setCompositionRoot(csLay);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(70, Unit.PERCENTAGE);
		grid.setColumns("deliveryDate", "validFrom", "validTo");
		grid.setSelectionMode(SelectionMode.SINGLE);

		grid1.setHeight(300, Unit.PIXELS);
		grid1.setWidth(100, Unit.PERCENTAGE);
		grid1.setSelectionMode(SelectionMode.MULTI);

		MultiSelectionModel selection = (MultiSelectionModel) grid1.getSelectionModel();

		editButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				grid1.setEditorEnabled(true);
				grid.setEditorSaveCaption("Save");
				grid.setEditorCancelCaption("Cancel");

				grid1.getEditorFieldGroup().addCommitHandler(new CommitHandler() {
					@Override
					public void preCommit(CommitEvent commitEvent) throws CommitException {
					}

					@Override
					public void postCommit(CommitEvent commitEvent) throws CommitException {
						Object editedItemId = grid1.getEditedItemId();
						orderItemRepo.save((OrderItem) editedItemId);
						// grid1.saveEditor();
					}
				});

			}
		});

		fetchButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				grid.setContainerDataSource(new BeanItemContainer<Order>(Order.class, orderRepo.findAll()));

				setGridItems();
			}
		});

		deleteButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			@Transactional
			public void buttonClick(ClickEvent event) {
				for (Object itemId : selection.getSelectedRows()) {
					OrderItem orderItem = (OrderItem) itemId;
					orderItemRepo.deleteByOrderItemId(orderItem.getId());
					grid1.getContainerDataSource().removeItem(itemId);
				}
				grid1.getSelectionModel().reset();
				// event.getButton().setEnabled(false);
			}
		});
		// deleteButton.setEnabled(grid1.getSelectedRows().size() > 0);

		insertButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ImportViewAddItem subWin = new ImportViewAddItem(orderItemRepo, orderRepo);
				subWin.setHeight("400px");
				subWin.setWidth("600px");

				subWin.setPositionX(350);
				subWin.setPositionY(150);

				UI.getCurrent().addWindow(subWin);

				subWin.addCloseListener(new Window.CloseListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(CloseEvent e) {
						grid.setContainerDataSource(new BeanItemContainer<Order>(Order.class, orderRepo.findAll()));

						setGridItems();
					}
				});

			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	@SuppressWarnings("resource")
	@Override
	@Transactional
	public OutputStream receiveUpload(String filename, String mimeType) {
		PipedOutputStream ps = null;
		String[] orderItems;
		String[] orderDetails;
		Set<Order> setOrder = new HashSet<Order>();

		try {
			File f = new File(filename);
			InputStream inputStream = new FileInputStream(f.getAbsolutePath());
			Reader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader dis = new BufferedReader(inputStreamReader);

			String input;
			ps = new PipedOutputStream();
			new PipedInputStream(ps);
			PrintStream os = new PrintStream(ps);

			while ((input = dis.readLine()) != null) {

				if (input.contains("oc;")) {

					orderDetails = input.substring(3, input.indexOf("[")).split(";");

					SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
					Order order = new Order();
					order.setDeliveryDate(formatter.parse(orderDetails[0]));
					order.setValidFrom(formatter.parse(orderDetails[1]));
					order.setValidTo(formatter.parse(orderDetails[2]));
					order.setAvalabileAt(2);

					Matcher m = Pattern.compile("(?<=\\[)(.+?)(?=\\])").matcher(input);

					Set<OrderItem> setOrderItem = new HashSet<OrderItem>();

					while (m.find()) {

						orderItems = m.group().split(";");

						OrderItem orderItem1 = new OrderItem();
						Article article = new Article(Integer.parseInt(orderItems[0]));
						orderItem1.setArticles(article);
						orderItem1.setQuantity(Integer.parseInt(orderItems[1]));
						Supplier supplier = new Supplier(Integer.parseInt(orderItems[2]));
						orderItem1.setSuppliers(supplier);
						orderItem1.setOrders(order);

						setOrderItem.add(orderItem1);
					}

					order.setOrderItems(setOrderItem);
					setOrder.add(order);

					orderRepo.save(order);

				} else if (input.contains("on;")) {
				} else if (input.contains("os;")) {
				} else if (input.contains("ol;")) {
				}
			}

			grid.setContainerDataSource(new BeanItemContainer<Order>(Order.class, orderRepo.findAll()));

			setGridItems();

			os.close();
		} catch (Exception e) {
			System.out.println("StringUtils reverse: " + e.getMessage());
		}
		return ps;
	}

	public void setGridItems() {
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				// grid1.setVisible(false);
			} else {
				Order orderSelected = (Order) e.getSelected().iterator().next();
				putData(ui, orderSelected);

				Set<OrderItem> orderItemsList = orderItemRepo.findByOrders(orderSelected);
				if (orderItemsList != null) {
					final BeanItemContainer<OrderItem> ds = new BeanItemContainer<OrderItem>(OrderItem.class,
							orderItemsList);
					// ds.addAll(orderItemsList);
					ds.addNestedContainerBean("articles");
					ds.addNestedContainerBean("suppliers");
					grid1.setColumns("articles.articleNo", "articles.articleName", "quantity", "suppliers.supplierNo",
							"suppliers.supplierName");
					grid1.setContainerDataSource(ds);

					HeaderCell articleNameFilter = filterRow.getCell("articles.articleName");
					filterFieldArticleName.setColumns(8);

					filterFieldArticleName.addTextChangeListener(change -> {
						ds.removeContainerFilters("articles.articleName");

						if (!change.getText().isEmpty())
							ds.addContainerFilter(
									new SimpleStringFilter("articles.articleName", change.getText(), true, false));
					});

					articleNameFilter.setComponent(filterFieldArticleName);

					HeaderCell supplierNameFilter = filterRow.getCell("suppliers.supplierName");
					filterFieldSupplierName.setColumns(8);

					filterFieldSupplierName.addTextChangeListener(change -> {
						ds.removeContainerFilters("suppliers.supplierName");

						if (!change.getText().isEmpty())
							ds.addContainerFilter(
									new SimpleStringFilter("suppliers.supplierName", change.getText(), true, false));
					});

					supplierNameFilter.setComponent(filterFieldSupplierName);

					/*
					 * for (Object pid : grid1.getContainerDataSource().
					 * getContainerPropertyIds()) { HeaderCell cell =
					 * filterRow.getCell(pid); TextField filterField = new
					 * TextField(); filterField.setColumns(8);
					 * filterField.addTextChangeListener(change -> {
					 * ds.removeContainerFilters(pid); if
					 * (!change.getText().isEmpty()) ds.addContainerFilter(new
					 * SimpleStringFilter(pid, change.getText(), true, false));
					 * }); cell.setComponent(filterField); }
					 */
				}
			}
		});
	}

	public static void putData(UI ui, Order order) {
		ui.getSession().setAttribute("order", order);
	}

}
