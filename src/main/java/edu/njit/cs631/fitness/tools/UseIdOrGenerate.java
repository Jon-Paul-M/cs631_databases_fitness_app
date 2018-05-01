package edu.njit.cs631.fitness.tools;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class UseIdOrGenerate extends IdentityGenerator {
	@SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(UseIdOrGenerate.class.getName());

    @Override
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
        if (obj == null) throw new HibernateException(new NullPointerException());
        Serializable id = session.getEntityPersister(null, obj)
                      .getClassMetadata().getIdentifier(obj, session);
        return id != null ? id : super.generate(session, obj);
    }
}
