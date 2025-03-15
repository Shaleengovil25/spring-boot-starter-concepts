package com.shaleen.helper.generator;

import java.util.Properties;
import java.util.stream.Stream;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class CustomIdGenerator implements IdentifierGenerator{

	String prefix;
	
	@Override
	public Object generate(SharedSessionContractImplementor session, Object obj) {
		// TODO Auto-generated method stub
		System.out.println("prefix generate---> "+prefix);
        String query = String.format("select %s from %s", 
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName());
        
        System.out.println("query---> "+query);
        Stream<String> ids = session.createQuery(query).stream();

        Long max = ids.map(o -> o.replace(prefix, ""))
            .mapToLong(Long::parseLong)
            .max()
            .orElse(0L);
        
        System.out.println("max---> "+max);

        return prefix + String.format("%04d",(max + 1));
	}
	
    @Override
    public void configure(Type type, Properties properties, 
      ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
        System.out.println("prefix--->"+prefix);
    }

}
