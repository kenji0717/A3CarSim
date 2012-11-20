package com.github.kenji0717.a3cs;
//2012,11/03コピペ
//2012,11/20修正
//http://d.hatena.ne.jp/quasistatic/20111208/p1
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

class PropertiesControl extends ResourceBundle.Control {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset;

    public PropertiesControl() {
        this(UTF8);
    }

    public PropertiesControl(String charsetName) {
        this(Charset.forName(charsetName));
    }

    public PropertiesControl(Charset charset) {
        this.charset = charset;
    }

    private static final String FORMAT = "properties";
    private static final List<String> FORMATS = Arrays.asList(FORMAT);

    @Override
    public List<String> getFormats(String baseName) {
        if (baseName == null) throw new NullPointerException();
        return FORMATS;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        if (baseName == null || locale == null || format == null || loader == null) throw new NullPointerException();
        ResourceBundle bundle = null;
        if (format.equals(FORMAT)) {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, format);
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(stream, charset));
                    bundle = new PropertyResourceBundle(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    reader.close();
                }
            }
        }
        return bundle;
    }
}
