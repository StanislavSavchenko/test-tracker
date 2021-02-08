package com.test.tracker.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HibernateProperties {
    private String defaultSchema;
    private String dialect;
    private boolean showSql;
    private boolean formatSql;
}
