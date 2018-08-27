package com.konex.elektrik.Const;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProjectVersion {

    public Double getProjectVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model getVersionProj = null;
        Double version = null;
        try {
            getVersionProj = reader.read(new FileReader("pom.xml"));
            version = Double.valueOf(getVersionProj.getVersion());
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return version;
    }
}
