package org.example;


import org.example.entites.Cat;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();

        Cat tom = new Cat();
        tom.setAge(1);
        tom.setName("Том Режий");
        tom.setType("Девон-рекс");
        s.save(tom);

        // Commit the transaction
        transaction.commit();
        s.close();
        sf.close();
    }
}