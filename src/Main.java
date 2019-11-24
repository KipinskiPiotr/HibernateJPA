import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void generateData(){
        final Session session = getSession();
        try {
            Product kalafior = new Product("Kalafior", 10);
            Product marchew = new Product("Marchew", 15);
            Product woda = new Product("Woda", 20);
            Product sok = new Product("Sok jabłkowy", 8);
            Supplier supp = new Supplier("Warzywniak", 158, new Address("Kraków", "Lea 58", "30-058"));
            Supplier supp2 = new Supplier("Carrefour", 141, new Address("Kraków", "Lea 41", "30-041"));
            Customer cust = new Customer("KustomerCompany", 10, new Address("Warszawa", "Główna 5", "0-005"));
            supp.addProduct(kalafior);
            supp.addProduct(marchew);
            supp2.addProduct(woda);
            supp2.addProduct(sok);
            Category warzywa = new Category("Warzywa");
            Category picie = new Category("Picie");
            warzywa.addProduct(kalafior);
            warzywa.addProduct(marchew);
            woda.setCategory(picie);
            sok.setCategory(picie);

            Invoice inv1 = new Invoice(cust);
            inv1.addProduct(kalafior, 3);
            inv1.addProduct(woda, 2);
            Invoice inv2 = new Invoice(cust);
            inv2.addProduct(kalafior, 3);
            inv2.addProduct(marchew, 1);
            inv2.addProduct(sok, 5);

            Transaction tx = session.beginTransaction();
            session.save(kalafior);
            session.save(marchew);
            session.save(woda);
            session.save(sok);
            session.save(supp);
            session.save(supp2);
            session.save(warzywa);
            session.save(picie);
            session.save(cust);
            session.save(inv1);
            session.save(inv2);
            tx.commit();
        } finally {
            session.close();
        }
    }

    public static void displayEntityNames(Session session){
        final Metamodel metamodel = session.getSessionFactory().getMetamodel();
        for (EntityType<?> entityType : metamodel.getEntities()) {
            final String entityName = entityType.getName();
            final Query query = session.createQuery("from " + entityName);
            System.out.println("executing: " + query.getQueryString());
            for (Object o : query.list()) {
                System.out.println("  " + o);
            }
        }
    }

    public static void jpaDemo(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
        EntityManager em = emf.createEntityManager();
        EntityTransaction etx = em.getTransaction();

        Product kalafior = new Product("Kalafior", 10);
        Product marchew = new Product("Marchew", 15);
        Product woda = new Product("Woda", 20);
        Product sok = new Product("Sok jabłkowy", 8);
        Category warzywa = new Category("Warzywa");
        Category picie = new Category("Picie");
        warzywa.addProduct(kalafior);
        warzywa.addProduct(marchew);
        woda.setCategory(picie);
        sok.setCategory(picie);

        etx.begin();
        em.persist(picie);
        em.persist(warzywa);
        etx.commit();

        Product p2 = em.find(Product.class, 2);
        System.out.println(p2.getProductName() + " należy do kategorii: " + p2.getCategory().getName());

        Category cat = em.find(Category.class, 4);
        System.out.println("Produkty z kategorii " + cat.getName() + ":");
        for(Product p: cat.getProducts()){
            System.out.println(p.getProductName());
        }
        em.close();
    }

    public static void main(final String[] args) throws Exception {
        generateData();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
        EntityManager em = emf.createEntityManager();

        List<Invoice> invoices = em.createNamedQuery("searchByCustomerName")
                .setParameter("name","KustomerCompany").getResultList();

        for(Invoice i: invoices){
            for(ProductInvoice pi: i.getProductInvoices()){
                System.out.println(pi.getQuantity() + " x " + pi.getProduct().getProductName());
            }
        }

        em.close();
    }
}