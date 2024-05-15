package org.demo.batch.config;

import org.demo.batch.entity.BusinessEmploymentEntity;
import org.springframework.batch.item.ItemProcessor;

public class BusinessEmploymentProcessor implements ItemProcessor<BusinessEmploymentEntity, BusinessEmploymentEntity> {

    @Override
    public BusinessEmploymentEntity process(BusinessEmploymentEntity item) {
        return item;
    }
}
