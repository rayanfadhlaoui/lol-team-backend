package com.lolteam.jsonSerialiser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1927528068391137902L;

	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		if (value != null) {
			generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
	}
}
