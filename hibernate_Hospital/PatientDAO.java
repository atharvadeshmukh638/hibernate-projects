package com.hibernate_Hospital;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class PatientDAO {
	public void createPatient(Patient obj) {
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

	public List<Patient> fetchAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Patient", Patient.class).list();
		}
	}

	public Patient fetchById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Patient.class, id);
		}
	}

	public void updateBillAmount(int id, double newValue) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Patient obj = session.get(Patient.class, id);
			if (obj != null)
				obj.setBillamount(newValue);
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
			Patient obj = session.get(Patient.class, id);
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
				session.persist(new Patient("Name" + i, (i % 2 == 0 ? "ICU" : "General"), 40000 + (i * 1000)));
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void updateWard(Patient obj) {
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

	public void deleteGeneralRecords() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("delete from Patient where ward = 'General'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Patient> fetchNativeGreaterThan50k() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM patient WHERE billAmount > 50000", Patient.class).list();
		}
	}

	public List<Patient> fetchNativeDescOrder() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM patient ORDER BY billAmount DESC", Patient.class).list();
		}
	}

	public Double fetchNativeHighestValue() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT MAX(billAmount) FROM patient", Double.class)
					.getSingleResult();
		}
	}

	public Double fetchNativeTotalSum() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT SUM(billAmount) FROM patient", Double.class)
					.getSingleResult();
		}
	}

	public List<Object[]> fetchNativeGroupWiseCount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT ward, COUNT(*) FROM patient GROUP BY ward", Object[].class).list();
		}
	}

	public List<Patient> fetchNativeValueBetween() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createNativeQuery("SELECT * FROM patient WHERE billAmount BETWEEN 40000 AND 80000", Patient.class)
					.list();
		}
	}

	public List<Patient> fetchHqlNamesStartingWithA() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Patient where name like 'A%'", Patient.class).list();
		}
	}

	public List<Patient> fetchHqlGreaterThanAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createQuery("from Patient where billAmount > (select avg(billAmount) from Patient)", Patient.class)
					.list();
		}
	}

	public Double fetchHqlSecondHighest() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery(
					"select max(billAmount) from Patient where billAmount < (select max(billAmount) from Patient)",
					Double.class).getSingleResult();
		}
	}

	public List<Patient> fetchHqlInGroups() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Patient where ward IN ('ICU', 'General', 'OPD')", Patient.class).list();
		}
	}

	public List<Object[]> fetchHqlGroupWiseAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select ward, avg(billAmount) from Patient group by ward", Object[].class)
					.list();
		}
	}

	public void bulkUpdateSpecificGroup() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("update Patient set billAmount = billAmount * 1.10 where ward = 'ICU'", null)
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}
}
