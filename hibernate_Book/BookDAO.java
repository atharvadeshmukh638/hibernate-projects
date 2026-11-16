package com.hibernate_Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class BookDAO {	    
	    public void createBook(Book obj) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.persist(obj);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public List<Book> fetchAll() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("from Book", Book.class).list();
	        }
	    }
	    
	    public Book fetchById(int id) {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.get(Book.class, id);
	        }
	    }
	    
	    public void updatePrice(int id, double newValue) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            Book obj = session.get(Book.class, id);
	            if (obj != null) obj.setPrice(newValue);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public void delete(int id) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            Book obj = session.get(Book.class, id);
	            if (obj != null) session.remove(obj);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public void insertTenRecords() {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            for (int i = 1; i <= 10; i++) {
	                session.persist(new Book("Name" + i, (i % 2 == 0 ? "Tech" : "Fiction"), 40000 + (i * 1000)));
	            }
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public void updateCategory(Book obj) {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.merge(obj); 
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public void deleteFictionRecords() {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.createQuery("delete from Book where category = 'Fiction'", null).executeUpdate();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	    
	    public List<Book> fetchNativeGreaterThan50k() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createNativeQuery("SELECT * FROM book WHERE price > 50000", Book.class).list();
	        }
	    }
	    
	    public List<Book> fetchNativeDescOrder() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createNativeQuery("SELECT * FROM book ORDER BY price DESC", Book.class).list();
	        }
	    }
	    
	    public Double fetchNativeHighestValue() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return (Double) session.createNativeQuery("SELECT MAX(price) FROM book", Double.class).getSingleResult();
	        }
	    }
	    
	    public Double fetchNativeTotalSum() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return (Double) session.createNativeQuery("SELECT SUM(price) FROM book", Double.class).getSingleResult();
	        }
	    }
	    
	    public List<Object[]> fetchNativeGroupWiseCount() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createNativeQuery("SELECT category, COUNT(*) FROM book GROUP BY category", Object[].class).list();
	        }
	    }
	    
	    public List<Book> fetchNativeValueBetween() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createNativeQuery("SELECT * FROM book WHERE price BETWEEN 40000 AND 80000", Book.class).list();
	        }
	    }
	    
	    public List<Book> fetchHqlNamesStartingWithA() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("from Book where title like 'A%'", Book.class).list();
	        }
	    }
	    
	    public List<Book> fetchHqlGreaterThanAvg() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("from Book where price > (select avg(price) from Book)", Book.class).list();
	        }
	    }
	    
	    public Double fetchHqlSecondHighest() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("select max(price) from Book where price < (select max(price) from Book)", Double.class).getSingleResult();
	        }
	    }
	    
	    public List<Book> fetchHqlInGroups() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("from Book where category IN ('Tech', 'Fiction', 'History')", Book.class).list();
	        }
	    }
	    
	    public List<Object[]> fetchHqlGroupWiseAvg() {
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            return session.createQuery("select category, avg(price) from Book group by category", Object[].class).list();
	        }
	    }
	    
	    public void bulkUpdateSpecificGroup() {
	        Transaction transaction = null;
	        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            session.createQuery("update Book set price = price * 1.10 where category = 'Tech'", null).executeUpdate();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) transaction.rollback();
	            e.printStackTrace();
	        }
	    }
	}


