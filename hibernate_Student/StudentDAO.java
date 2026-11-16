package com.hibernate_Student;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class StudentDAO {
	public void createStudent(Student obj) {
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

	public List<Student> fetchAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Student", Student.class).list();
		}
	}

	public Student fetchById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Student.class, id);
		}
	}

	public void updateFee(int id, double newValue) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Student obj = session.get(Student.class, id);
			if (obj != null)
				obj.setFee(newValue);
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
			Student obj = session.get(Student.class, id);
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
				session.persist(new Student("Name" + i, (i % 2 == 0 ? "Science" : "Arts"), 40000 + (i * 1000)));
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void updateCourse(Student obj) {
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

	public void deleteArtsRecords() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("delete from Student where course = 'Arts'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Student> fetchNativeGreaterThan50k() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM student WHERE fee > 50000", Student.class).list();
		}
	}

	public List<Student> fetchNativeDescOrder() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM student ORDER BY fee DESC", Student.class).list();
		}
	}

	public Double fetchNativeHighestValue() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT MAX(fee) FROM student", Double.class).getSingleResult();
		}
	}

	public Double fetchNativeTotalSum() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT SUM(fee) FROM student", Double.class).getSingleResult();
		}
	}

	public List<Object[]> fetchNativeGroupWiseCount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT course, COUNT(*) FROM student GROUP BY course", Object[].class)
					.list();
		}
	}

	public List<Student> fetchNativeValueBetween() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM student WHERE fee BETWEEN 40000 AND 80000", Student.class)
					.list();
		}
	}

	public List<Student> fetchHqlNamesStartingWithA() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Student where name like 'A%'", Student.class).list();
		}
	}

	public List<Student> fetchHqlGreaterThanAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Student where fee > (select avg(fee) from Student)", Student.class).list();
		}
	}

	public Double fetchHqlSecondHighest() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select max(fee) from Student where fee < (select max(fee) from Student)",
					Double.class).getSingleResult();
		}
	}

	public List<Student> fetchHqlInGroups() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Student where course IN ('Science', 'Arts', 'Commerce')", Student.class)
					.list();
		}
	}

	public List<Object[]> fetchHqlGroupWiseAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select course, avg(fee) from Student group by course", Object[].class).list();
		}
	}

	public void bulkUpdateSpecificGroup() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("update Student set fee = fee * 1.10 where course = 'Science'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}
}
