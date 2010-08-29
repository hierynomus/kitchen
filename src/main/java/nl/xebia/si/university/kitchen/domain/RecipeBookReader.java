package nl.xebia.si.university.kitchen.domain;

import com.thoughtworks.xstream.XStream;
import org.springframework.integration.annotation.Transformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 */
public class RecipeBookReader {
	private XStream xStream;

	public RecipeBookReader() {
		xStream = new XStream();
		xStream.processAnnotations(new Class[] {Recipe.class, Ingredient.class});
	}

	@Transformer
	public Recipe read(File file) throws FileNotFoundException {
		return (Recipe) xStream.fromXML(new FileInputStream(file));
	}
}
