package com.hibernate_OnlineStore;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductDAO {

	public void createProduct(Product obj) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(obj);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Product> fetchAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Product", Product.class).list();
		}
	}

	public Product fetchById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Product.class, id);
		}
	}

	public void updatePrice(int id, double newValue) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Product obj = session.get(Product.class, id);
			if (obj != null)
				obj.setPrice(newValue);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Product obj = session.get(Product.class, id);
			if (obj != null)
				session.remove(obj);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void insertTenRecords() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			for (int i = 1; i <= 10; i++) {
				session.persist(new Product("Name" + i, (i % 2 == 0 ? "Electronics" : "Clothing"), 40000 + (i * 1000)));
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void updateCategory(Product obj) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(obj);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void deleteClothingRecords() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("delete from Product where category = 'Clothing'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Product> fetchNativeGreaterThan50k() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM product WHERE price > 50000", Product.class).list();
		}
	}

	public List<Product> fetchNativeDescOrder() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM product ORDER BY price DESC", Product.class).list();
		}
	}

	public Double fetchNativeHighestValue() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT MAX(price) FROM product", Double.class).getSingleResult();
		}
	}

	public Double fetchNativeTotalSum() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT SUM(price) FROM product", Double.class).getSingleResult();
		}
	}

	public List<Object[]> fetchNativeGroupWiseCount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT category, COUNT(*) FROM product GROUP BY category", Object[].class)
					.list();
		}
	}

	public List<Product> fetchNativeValueBetween() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM product WHERE price BETWEEN 40000 AND 80000", Product.class)
					.list();
		}
	}

	public List<Product> fetchHqlNamesStartingWithA() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Product where name like 'A%'", Product.class).list();
		}
	}

	public List<Product> fetchHqlGreaterThanAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Product where price > (select avg(price) from Product)", Product.class)
					.list();
		}
	}

	public Double fetchHqlSecondHighest() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select max(price) from Product where price < (select max(price) from Product)",
					Double.class).getSingleResult();
		}
	}

	public List<Product> fetchHqlInGroups() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createQuery("from Product where category IN ('Electronics', 'Clothing', 'Home')", Product.class)
					.list();
		}
	}

	public List<Object[]> fetchHqlGroupWiseAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select category, avg(price) from Product group by category", Object[].class)
					.list();
		}
	}

	public void bulkUpdateSpecificGroup() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("update Product set price = price * 1.10 where category = 'Electronics'", null)
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}
}
