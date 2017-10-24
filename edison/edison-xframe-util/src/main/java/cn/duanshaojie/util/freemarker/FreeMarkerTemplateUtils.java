package cn.duanshaojie.util.freemarker;


import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;
@Component
public class FreeMarkerTemplateUtils {

    private static Logger logger = LoggerFactory.getLogger(FreeMarkerTemplateUtils.class);
    private Map<String, Template> templates = Maps.newHashMap();

    public String renderTemplate(Object model, String folder, String fileName, String encoding) {
        String key = folder + "/" + fileName;
        try {
            Template template;
            if (templates.containsKey(key)) {
                template = templates.get(key);
            } else {
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
                cfg.setDefaultEncoding(encoding);
                cfg.setClassLoaderForTemplateLoading(FreeMarkerTemplateUtils.class.getClassLoader(), folder);
                template = cfg.getTemplate(fileName);
                templates.put(key, template);
            }
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            logger.error("FreeMarkerTemplateUtils",e);
        }

        return null;
    }

}
