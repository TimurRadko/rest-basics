package com.epam.esm.dao.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDateTime> {

  public LocalDateSerializer() {
    super(LocalDateTime.class);
  }

  @Override
  public void serialize(
      LocalDateTime localDateTime, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    String serialized = localDateTime.format(dateTimeFormatter);
    generator.writeString(serialized);
  }
}
