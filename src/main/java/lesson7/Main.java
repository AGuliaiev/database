package lesson7;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        try (ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build()) {
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(Product.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(User.class)
                    .getMetadataBuilder()
                    .build();

            try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
                 Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                Product product1 = new Product();
                product1.setName("Pencil");
                product1.setPrice(1.99);
                product1.setTotalCount(100);

                Product product2 = new Product();
                product2.setName("Notebook");
                product2.setPrice(0.99);
                product2.setTotalCount(50);

                Product product3 = new Product();
                product3.setName("Phone");
                product3.setPrice(1999.99);
                product3.setTotalCount(10);

                session.save(product1);
                session.save(product2);
                session.save(product3);

                Address address = new Address();
                address.setCity("Lviv");
                address.setStreet("Sadova");

                Order order1 = new Order();
                order1.setStatus(Status.IN_PROGRESS);
                order1.setAddress(address);

                order1.setProducts(new ArrayList<>(Arrays.asList(product1, product3)));

                Order order2 = new Order();
                order2.setStatus(Status.DELIVERED);
                order2.setAddress(address);

                order2.setProducts(new ArrayList<>(Arrays.asList(product2, product3)));

                User user = new User();
                user.setName("John");
                user.setOrders(Arrays.asList(order1, order2));

                session.save(user);

                transaction.commit();
            }
        }
    }
}