package com.epam.esm.persistence.mapper;

import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        String description = rs.getString(DESCRIPTION);
        BigDecimal price = rs.getBigDecimal(PRICE);
        int duration = rs.getShort(DURATION);
        LocalDate createDate = rs.getObject(CREATE_DATE, LocalDate.class);
        LocalDate lastUpdateDate = rs.getObject(LAST_UPDATE_DATE, LocalDate.class);
        return new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate);
    }
}
