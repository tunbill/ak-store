package com.ak.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.ak.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.ak.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.ak.domain.User.class.getName());
            createCache(cm, com.ak.domain.Authority.class.getName());
            createCache(cm, com.ak.domain.User.class.getName() + ".authorities");
            createCache(cm, com.ak.domain.Company.class.getName());
            createCache(cm, com.ak.domain.Company.class.getName() + ".customerTypes");
            createCache(cm, com.ak.domain.Company.class.getName() + ".customers");
            createCache(cm, com.ak.domain.Company.class.getName() + ".stores");
            createCache(cm, com.ak.domain.Company.class.getName() + ".departments");
            createCache(cm, com.ak.domain.Company.class.getName() + ".jobs");
            createCache(cm, com.ak.domain.Company.class.getName() + ".jobTypes");
            createCache(cm, com.ak.domain.Company.class.getName() + ".employees");
            createCache(cm, com.ak.domain.Company.class.getName() + ".items");
            createCache(cm, com.ak.domain.Company.class.getName() + ".itemGroups");
            createCache(cm, com.ak.domain.Company.class.getName() + ".units");
            createCache(cm, com.ak.domain.Company.class.getName() + ".invoices");
            createCache(cm, com.ak.domain.Company.class.getName() + ".invoiceLines");
            createCache(cm, com.ak.domain.Industry.class.getName());
            createCache(cm, com.ak.domain.Industry.class.getName() + ".companies");
            createCache(cm, com.ak.domain.Province.class.getName());
            createCache(cm, com.ak.domain.Province.class.getName() + ".companies");
            createCache(cm, com.ak.domain.Customer.class.getName());
            createCache(cm, com.ak.domain.Customer.class.getName() + ".invoices");
            createCache(cm, com.ak.domain.CustomerType.class.getName());
            createCache(cm, com.ak.domain.CustomerType.class.getName() + ".customers");
            createCache(cm, com.ak.domain.Terms.class.getName());
            createCache(cm, com.ak.domain.Terms.class.getName() + ".customers");
            createCache(cm, com.ak.domain.Terms.class.getName() + ".invoices");
            createCache(cm, com.ak.domain.Store.class.getName());
            createCache(cm, com.ak.domain.Store.class.getName() + ".items");
            createCache(cm, com.ak.domain.Department.class.getName());
            createCache(cm, com.ak.domain.Department.class.getName() + ".employees");
            createCache(cm, com.ak.domain.Jobs.class.getName());
            createCache(cm, com.ak.domain.JobType.class.getName());
            createCache(cm, com.ak.domain.JobType.class.getName() + ".jobs");
            createCache(cm, com.ak.domain.Employee.class.getName());
            createCache(cm, com.ak.domain.Employee.class.getName() + ".invoices");
            createCache(cm, com.ak.domain.Item.class.getName());
            createCache(cm, com.ak.domain.Item.class.getName() + ".invoiceLines");
            createCache(cm, com.ak.domain.Unit.class.getName());
            createCache(cm, com.ak.domain.Unit.class.getName() + ".items");
            createCache(cm, com.ak.domain.ItemGroup.class.getName());
            createCache(cm, com.ak.domain.ItemGroup.class.getName() + ".items");
            createCache(cm, com.ak.domain.Invoice.class.getName());
            createCache(cm, com.ak.domain.Invoice.class.getName() + ".invoiceLines");
            createCache(cm, com.ak.domain.InvoiceLine.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
