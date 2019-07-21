package com.mycompany.exampleweb.dao;

import com.mycompany.exampleweb.entities.Cidade;
import com.mycompany.exampleweb.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CidadeDao extends AbstractDao<Cidade> {

    private EntityManager em = JPAUtil.getEntityManager();

    public CidadeDao() {
        super(Cidade.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Cidade findByIdIbge(Integer idIbge) {
        Query query = em.createNamedQuery("findByIdIbge");
        query.setParameter("idIbge", idIbge);
        return (Cidade) query.getSingleResult();
    }

    public List<String> findCityNameByUf(String uf) {

        Query query = em.createNamedQuery("findCityNameByUf");
        query.setParameter("uf", uf);
        return (List<String>) query.getResultList();
    }
    
    
}
