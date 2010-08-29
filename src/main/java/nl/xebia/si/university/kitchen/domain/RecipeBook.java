package nl.xebia.si.university.kitchen.domain;

import org.springframework.beans.factory.FactoryBean;

import java.io.File;
import java.io.IOException;

/**
 */
public class RecipeBook implements FactoryBean {
	private File tempFolder;

	public RecipeBook() throws IOException {
		tempFolder = File.createTempFile("recipes", "");
		tempFolder.delete();
		tempFolder.mkdir();
	}

	@Override
	public Object getObject() throws Exception {
		return tempFolder;
	}

	@Override
	public Class<?> getObjectType() {
		return File.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void cleanUp() {
		recursiveDelete(tempFolder);
	}

	private void recursiveDelete(File file) {
		File[] files= file.listFiles();
		if (files != null)
			for (File each : files)
				recursiveDelete(each);
		file.delete();
	}

}
