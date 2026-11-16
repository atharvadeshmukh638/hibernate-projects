package com.hibernate_GYM;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MemberDAO {

	public void createMember(Member obj) {
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

	public List<Member> fetchAll() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Member", Member.class).list();
		}
	}

	public Member fetchById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Member.class, id);
		}
	}

	public void updateFee(int id, double newValue) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Member obj = session.get(Member.class, id);
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
			Member obj = session.get(Member.class, id);
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
				session.persist(new Member("Name" + i, (i % 2 == 0 ? "Premium" : "Basic"), 40000 + (i * 1000)));
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public void updatePlan(Member obj) {
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

	public void deleteBasicRecords() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("delete from Member where plan = 'Basic'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	public List<Member> fetchNativeGreaterThan50k() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM member WHERE fee > 50000", Member.class).list();
		}
	}

	public List<Member> fetchNativeDescOrder() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM member ORDER BY fee DESC", Member.class).list();
		}
	}

	public Double fetchNativeHighestValue() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT MAX(fee) FROM member", Double.class).getSingleResult();
		}
	}

	public Double fetchNativeTotalSum() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (Double) session.createNativeQuery("SELECT SUM(fee) FROM member", Double.class).getSingleResult();
		}
	}

	public List<Object[]> fetchNativeGroupWiseCount() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT plan, COUNT(*) FROM member GROUP BY plan", Object[].class).list();
		}
	}

	public List<Member> fetchNativeValueBetween() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createNativeQuery("SELECT * FROM member WHERE fee BETWEEN 40000 AND 80000", Member.class)
					.list();
		}
	}

	public List<Member> fetchHqlNamesStartingWithA() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Member where name like 'A%'", Member.class).list();
		}
	}

	public List<Member> fetchHqlGreaterThanAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Member where fee > (select avg(fee) from Member)", Member.class).list();
		}
	}

	public Double fetchHqlSecondHighest() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createQuery("select max(fee) from Member where fee < (select max(fee) from Member)", Double.class)
					.getSingleResult();
		}
	}

	public List<Member> fetchHqlInGroups() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Member where plan IN ('Premium', 'Basic', 'Pro')", Member.class).list();
		}
	}

	public List<Object[]> fetchHqlGroupWiseAvg() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("select plan, avg(fee) from Member group by plan", Object[].class).list();
		}
	}

	public void bulkUpdateSpecificGroup() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.createQuery("update Member set fee = fee * 1.10 where plan = 'Premium'", null).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}
}
