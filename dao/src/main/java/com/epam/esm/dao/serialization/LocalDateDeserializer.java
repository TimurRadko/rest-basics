package com.epam.esm.dao.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateDeserializer extends StdDeserializer<LocalDateTime> {

  protected LocalDateDeserializer() {
    super(LocalDateTime.class);
  }

  @Override
  public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    String serialized = jsonParser.readValueAs(String.class);
    return LocalDateTime.parse(serialized);
  }
}
