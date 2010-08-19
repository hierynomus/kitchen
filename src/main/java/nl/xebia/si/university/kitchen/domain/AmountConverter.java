package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import nl.xebia.si.university.kitchen.domain.Amount;

import static nl.xebia.si.university.kitchen.domain.Amount.Unit;

/**
 */
public class AmountConverter implements Converter {
	public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
		Amount amount = (Amount) source;
		StringBuilder builder = new StringBuilder();
		builder.append(amount.getAmount());
		if (amount.getUnit() == Unit.GRAMS) {
			builder.append(" gr");
		}
		writer.setValue(builder.toString());
	}

	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
		final String value = reader.getValue();
		final String[] strings = value.split(" ");
		if (strings.length == 1) {
			return new Amount(Integer.parseInt(strings[0]), Unit.PIECES);
		} else if (strings[1].trim().equalsIgnoreCase("gr")) {
			return new Amount(Integer.parseInt(strings[0]), Unit.GRAMS);
		} else {
			throw new IllegalArgumentException("Could not parse Amount: " + value);
		}
	}

	public boolean canConvert(final Class type) {
		return type.equals(Amount.class);
	}
}
