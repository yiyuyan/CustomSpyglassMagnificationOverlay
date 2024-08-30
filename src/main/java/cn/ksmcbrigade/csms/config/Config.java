package cn.ksmcbrigade.csms.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Config {

    public File file;
    public Mode mode;

    public Config(File file,Mode mode){
        this.mode = mode;
        this.file = file;
    }

    public Config(File file) throws IOException {
        this.file = file;
        if(!file.exists()) FileUtils.writeStringToFile(file,"{\"mode\":0}");
        this.mode = Mode.valueOf(JsonParser.parseString(FileUtils.readFileToString(file)).getAsJsonObject().get("mode").getAsInt());
    }

    public void save() throws IOException {
        JsonObject object = new JsonObject();
        object.addProperty("mode",this.mode.index);
        FileUtils.writeStringToFile(file,object.toString());
    }
}
