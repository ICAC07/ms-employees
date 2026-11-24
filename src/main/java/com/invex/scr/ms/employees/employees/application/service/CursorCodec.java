package com.invex.scr.ms.employees.employees.application.service;

import com.invex.scr.ms.employees.employees.domain.exception.InvalidQueryException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CursorCodec {

  private static final String PREFIX = "v1:";

  public String encode(long lastId) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString((PREFIX + lastId).getBytes(StandardCharsets.UTF_8));
  }

  public long decode(String cursor) {
    if (cursor == null) {
      return 0L;
    }
    try {
      String decoded = new String(Base64.getUrlDecoder().decode(cursor), StandardCharsets.UTF_8);
      if (!decoded.startsWith(PREFIX)) {
        throw new IllegalArgumentException("prefijo desconocido");
      }
      long id = Long.parseLong(decoded.substring(PREFIX.length()));
      if (id < 0) {
        throw new IllegalArgumentException("id negativo");
      }
      return id;
    } catch (IllegalArgumentException e) {
      throw new InvalidQueryException("El cursor no es válido", e);
    }
  }
}
