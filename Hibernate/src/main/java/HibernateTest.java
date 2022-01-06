import entity.StudentsEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Hibernate Test Demo.
 * Reference:
 * https://www.youtube.com/watch?v=QJddHc41xrM (Intellij Official Hibernate/JPA Demo)
 * https://www.onlinetutorialspoint.com/hibernate/hibernate-query-language-hql-select.html
 * https://www.onlinetutorialspoint.com/hibernate/hql-update-delete-query-example.html
 * https://www.onlinetutorialspoint.com/hibernate/hibernate-native-sql-query-example.html
 * https://www.onlinetutorialspoint.com/hibernate/hibernate-criteria-api-example.html
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-sub-query.html
 * https://www.tutorialspoint.com/jpa/jpa_criteria_api.htm
 */
public class HibernateTest {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // add a student in the jdbc_demo db
            /*StudentsEntity dalia = new StudentsEntity();
            dalia.setId(6);
            dalia.setFirstName("Dalia");
            dalia.setLastName("Abo Sheasha");
            entityManager.persist(dalia);*/

            // ============================================== Using HQL ==============================================
            // Ref: https://www.onlinetutorialspoint.com/hibernate/hibernate-query-language-hql-select.html
            // 1 - NamedQuery (see detailed HQL in the header of StudentsEntity class)
            // select & print all students
            System.out.println();
            System.out.println("Using HQL");

            TypedQuery<StudentsEntity> studentsQuery = entityManager.createNamedQuery("students.all", StudentsEntity.class);
            for (StudentsEntity students : studentsQuery.getResultList()) {
                System.out.println(students);
            }

            // select & print all students with the specified first name
            TypedQuery<StudentsEntity> studentsByFirstNameQuery = entityManager.createNamedQuery("students.byFirstName", StudentsEntity.class);
            studentsByFirstNameQuery.setParameter(0, "Casey");
            for (StudentsEntity students : studentsByFirstNameQuery.getResultList()) {
                System.out.println(students);
            }

            // select & print all students with the specified first name & age
            TypedQuery<StudentsEntity> studentsByNameAgeQuery = entityManager.createNamedQuery("students.byNameAge", StudentsEntity.class);
            studentsByNameAgeQuery.setParameter(0, "Casey");
            studentsByNameAgeQuery.setParameter(1, 18);
            for (StudentsEntity students : studentsByNameAgeQuery.getResultList()) {
                System.out.println(students);
            }

            // 2 - (Normal) Query
            // Ref: https://www.onlinetutorialspoint.com/hibernate/hql-update-delete-query-example.html
            // update the age of student whose first name is Dalia to 22
            String update = "update StudentsEntity s set s.age=:age where s.firstName=:firstName";
            Query updateQuery = entityManager.createQuery(update);
            updateQuery.setParameter("age", 22);
            updateQuery.setParameter("firstName", "Dalia");

            int countUpdate = updateQuery.executeUpdate();
            System.out.println(countUpdate + " Record(s) Updated.");

            // ============================================== Using Native SQL ==============================================
            // Ref: https://www.onlinetutorialspoint.com/hibernate/hibernate-native-sql-query-example.html
            System.out.println();
            System.out.println("Using Native SQL");

            Query countStudents = entityManager.createNativeQuery("SELECT COUNT(*) FROM students");
            System.out.println("There are " + countStudents.getSingleResult() + " students in the table.");

            Query studentsSelectQuery = entityManager.createNativeQuery("SELECT * FROM students", StudentsEntity.class);
            for (Object o : studentsSelectQuery.getResultList()) {
                System.out.println((StudentsEntity) o);
            }

            // ============================================== Using Criteria API ==============================================
            // Ref: https://www.onlinetutorialspoint.com/hibernate/hibernate-criteria-api-example.html,
            // https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-sub-query.html,
            // https://www.tutorialspoint.com/jpa/jpa_criteria_api.htm
            System.out.println();
            System.out.println("Using Criteria API");

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<StudentsEntity> criteriaQuery = criteriaBuilder.createQuery(StudentsEntity.class);
            Root<StudentsEntity> root = criteriaQuery.from(StudentsEntity.class);

            // select all students, order by their first name ASC
            CriteriaQuery<StudentsEntity> selectAll = criteriaQuery.select(root);
            selectAll.orderBy(criteriaBuilder.asc(root.get("firstName")));
            TypedQuery<StudentsEntity> typedQuery = entityManager.createQuery(selectAll);
            for (StudentsEntity s : typedQuery.getResultList()) {
                System.out.println(s);
            }

            // select students whose first name is Casey & age >= 18
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("firstName"), "Casey"),
                            criteriaBuilder.greaterThanOrEqualTo(root.get("age"), 18) // lessThanOrEqualTo
                    )
            );

            Query studentsCriteriaQuery = entityManager.createQuery(criteriaQuery);
            for (Object o : studentsCriteriaQuery.getResultList()) {
                System.out.println((StudentsEntity) o);
            }

            transaction.commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
