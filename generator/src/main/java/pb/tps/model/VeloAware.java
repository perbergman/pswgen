package pb.tps.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.google.common.base.Strings;

public class VeloAware {
	protected static final String ENC = "UTF-8";
	private Writer out = null;

	protected VeloAware(String fileName) {
		Properties props = new Properties();
		// props.setProperty("resource.loader", "classpath, file");
		props.setProperty("resource.loader", "classpath");
		props.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		props.setProperty("classpath.resource.loader.cache", "true");

		// props.setProperty("file.resource.loader.class",
		// FileResourceLoader.class.getName());
		// props.setProperty("file.resource.loader.path", propPath);
		// props.setProperty("file.resource.loader.cache", "true");

		Velocity.init(props);

		try {
			if (Strings.isNullOrEmpty(fileName)) {
				out = new PrintWriter(System.out);
			} else {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(fileName), ENC));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Writer getWriter() {
		return out;
	}

	protected String runVelo(String tpl, VelocityContext context) {
		StringWriter w = new StringWriter();
		Velocity.mergeTemplate(tpl, ENC, context, w);
		return w.toString();
	}

	/**
	 * Calc padding
	 *
	 * @param width
	 *            - columns
	 * @param got
	 *            - actual number of cells in a row
	 * @return - how many padding cells needed
	 */
	protected int addCells(int width, int got) {
		int add = 0;
		if ((got % width) > 0) {
			if (got < width) {
				add = width - got;
			} else {
				add = width - (got % width);
			}
		}
		return add;
	}
}
