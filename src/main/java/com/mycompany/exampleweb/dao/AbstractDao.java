package com.mycompany.exampleweb.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDao<T> {

    private final Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T t) {
        getEntityManager().persist(t);
    }

    public void createList(List<T> list) throws Exception {
        try {
            for (T t : list) {
                getEntityManager().merge(t);
            }
        } catch (Exception e) {
            getEntityManager().getTransaction().setRollbackOnly();
            throw e;
        }
    }

    public void edit(T t) {
        getEntityManager().merge(t);
    }

    public void remove(T t) {
        getEntityManager().remove(getEntityManager().merge(t));
    }

    public T find(Object id) {
        return getEntityManager().find(clazz, id);
    }

    public List<T> findAll() {

        CriteriaQuery cq
                = getEntityManager()
                .getCriteriaBuilder()
                .createQuery();

        cq.select(cq.from(clazz));

        try {
            return getEntityManager().createQuery(cq).getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            getEntityManager().close();
        }
    }

    public T findByFilter(String field, String value) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> clazzRoot = cq.from(clazz);
        cq.select(clazzRoot);
        cq.where(cb.equal(clazzRoot.get(field), value));
        try {
            return (T) getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
