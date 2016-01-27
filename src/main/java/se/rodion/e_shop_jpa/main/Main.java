//package se.rodion.e_shop_jpa.main;
//
//import java.util.HashSet;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import se.rodion.e_shop_jpa.model.Order;
//import se.rodion.e_shop_jpa.model.OrderRow;
//import se.rodion.e_shop_jpa.model.Product;
//import se.rodion.e_shop_jpa.model.User;
//import se.rodion.e_shop_jpa.model.status.OrderStatus;
//import se.rodion.e_shop_jpa.model.status.ProductStatus;
//import se.rodion.e_shop_jpa.model.status.UserStatus;
//import se.rodion.e_shop_jpa.repository.IDGenerator;
//import se.rodion.e_shop_jpa.repository.OrderIdGenerator;
//import se.rodion.e_shop_jpa.repository.OrderRepository;
//import se.rodion.e_shop_jpa.repository.ProductRepository;
//import se.rodion.e_shop_jpa.repository.JPAOrderRepository;
//import se.rodion.e_shop_jpa.repository.JPAProductRepository;
//import se.rodion.e_shop_jpa.repository.JPAUserRepository;
//import se.rodion.e_shop_jpa.repository.UserRepository;
//import se.rodion.e_shop_sql.service.ECommerceService;
//
//public class Main
//{	
//	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");
//
//	public static void main(String[] args)
//	{
//		
//		UserRepository userRepository = new JPAUserRepository(factory);
//		ProductRepository productRepository = new JPAProductRepository(factory);
//		OrderRepository orderRepository = new JPAOrderRepository(factory);
//		IDGenerator orderIdGenerator = new OrderIdGenerator(10000);
//		
//		ECommerceService ecom = new ECommerceService(userRepository, 
//														productRepository, 
//														orderRepository,
//														orderIdGenerator);
//		
//		User user1 = new User ("username1", "Karl", "Karlsson", UserStatus.ACTIVE);
//		User user2 = new User ("username2", "Nils", "Nilsson", UserStatus.ACTIVE);
//		User user3 = new User ("username3", "Nils", "Nilsson", UserStatus.NON_ACTIVE);
//		ecom.saveOrUpdateUser(user1);
//		ecom.saveOrUpdateUser(user2);
//		ecom.saveOrUpdateUser(user3);
//		User user4 = new User (1L, "username1", "Olof", "Olofsson", UserStatus.NON_ACTIVE);
//		ecom.saveOrUpdateUser(user4);
//		ecom.removeUser(user2);
//		ecom.saveUser(new User (1L, "username1", "Olof", "Olofsson", "active"));
//		
//		
//		ecom.getAllUsers().forEach(e -> System.out.println(e));
//		
//		
//		User user5 = ecom.getUserByUserId(2L);
//		System.out.println(user5);
//		System.out.println(ecom.getUserByUserId(1L));
//		
//		
//		User user6 = ecom.getUserByUsername("username1");
//		System.out.println(user6);
//		System.out.println(ecom.getUserByUsername("username1"));
//		
//		User user7 = ecom.updateUserStatus(user3, "non-active");
//		System.out.println(user7.getUserStatus());
//		System.out.println(ecom.getUserByUserId(3L));
//		System.out.println(ecom.updateUserStatus(user3, "non-active"));
//		System.out.println(ecom.getUserByUserId(3L));
//		
//		
//		Product product1 = new Product("Table red", 1000.00, ProductStatus.IN_STOCK);
//		Product product2 = new Product("Table green", 1500.00, ProductStatus.OUT_OF_STOCK);
//		Product product3 = new Product("Chair yellow", 500.00, ProductStatus.IN_STOCK);		
//		Product product4 = new Product("Chair blue", 3500.00, ProductStatus.IN_STOCK);		
//		ecom.saveOrUpdateProduct(product1);
//		ecom.saveOrUpdateProduct(product2);
//		ecom.saveOrUpdateProduct(product3);
//		ecom.saveOrUpdateProduct(product4);
//		
//		ecom.saveOrUpdateProduct(new Product(7L, "Table", 1000.00, ProductStatus.OUT_OF_STOCK));
//		
//		
//		ecom.getAllProducts().forEach(e -> System.out.println(e));
//		
//		System.out.println(ecom.getProductByProductId(6L));
//		
//		ecom.searchProductByProductName("Table").forEach(e -> System.out.println(e));
//		
//		System.out.println(ecom.updateProductStatus(product3, "valid"));
//		
//		OrderRow orderRow1 = new OrderRow(product1, 10);
//		OrderRow orderRow2 = new OrderRow(product1, 20);
//		OrderRow orderRow3 = new OrderRow(product2, 30);
//		OrderRow orderRow4 = new OrderRow(product1, 40);
//		OrderRow orderRow5 = new OrderRow(product3, 50);
//		OrderRow orderRow6 = new OrderRow(product2, 60);
//
//		HashSet<OrderRow> orderRows = new HashSet<>();
//		orderRows.add(orderRow1);
//		orderRows.add(orderRow3);		
//		ecom.createOrder(user1, orderRows);
//		
//		
//		HashSet<OrderRow> orderRows2 = new HashSet<>();
//		orderRows2.add(orderRow2);
//		orderRows2.add(orderRow4);		
//		ecom.createOrder(user2, orderRows2);
//		
//		HashSet<OrderRow> orderRows3 = new HashSet<>();
//		orderRows3.add(orderRow5);
//		orderRows3.add(orderRow6);	
//		
//		Order order1 = new Order(8L, 10001, user3, orderRows3, OrderStatus.PLACED);
//		ecom.updateOrder(order1);
//		
//		OrderRow orderRow5 = new OrderRow(11L, order1, user2, product1, 40);
//		ecom.updateOrderRaw(orderRow5);
//		System.out.println(ecom.getOrderById(10L));
//		
//		Order order1 = new Order(10L, user1, orderRows, "Placed");
//		System.out.println(order1);
//		order1.getOrderRows().forEach(System.out::println);
//		orderRows.forEach(System.out::println);
//		ecom.updateOrder(order1);
//		
//		
//		ecom.getAllOrders().forEach(e -> System.out.println(e));
//		
//		System.out.println(ecom.getOrderById(10L));
//		
//		Order order1 = new Order(12L, user1, orderRaws, "Placed");
//		ecom.updateOrder(order1);
//		
//		System.out.println(ecom.getOrderById(11L));
//		System.out.println(ecom.getOrdersByUser("username2"));
//		System.out.println(ecom.getOrdersByStatus(OrderStatus.CANCELED));
//		
//		ecom.updateOrderStatus(ecom.getOrderById(10L), "Payed");
//		
//		List<Order> specOrders = ecom.getOrderWithValueOverAmount(48000);
//		specOrders.forEach(e -> System.out.println(e));
//		
//		
//	}
//
//}
