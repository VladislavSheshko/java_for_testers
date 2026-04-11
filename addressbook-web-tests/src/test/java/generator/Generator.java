package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Generator {
    @Parameter(names = {"--type", "-t"})
    String type;
    @Parameter(names = {"--output", "-o"})
    String output;
    @Parameter(names = {"--format", "-f"})
    String format;
    @Parameter(names = {"--count", "-n"})
    int count;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder().addObject(generator).build().parse(args);
        generator.run();
    }

    private void run() throws IOException {
        //генерируем данные в зависимости от type
        List<?> data = generate();
        //сохраняем, не зная конкретного типа
        save(data);
    }

    private List<?> generate() {
        if ("groups".equals(type)) {
            return generateGroups();
        } else if ("contacts".equals(type)) {
            return generateContacts();
        } else {
            throw new IllegalArgumentException("Неизвестный тип данных " + type);
        }
    }
    private ArrayList<GroupData> generateGroups() {
        var result = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            result.add(new GroupData().
                    withName(CommonFunctions.randomString(i * 10))
                    .withHeader(CommonFunctions.randomString(i * 10))
                    .withFooter(CommonFunctions.randomString(i * 10)));
        }
        return result;
    }

    private ArrayList<ContactData> generateContacts() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            result.add(new ContactData()
                    .withFirstname(CommonFunctions.randomString(i * 10))
                    .withLastname(CommonFunctions.randomString(i * 10)));
        }
        return result;
    }

    private <T> void save(List<T> data) throws IOException {
        if ("yaml".equals(format)) {
            var mapper = new YAMLMapper();
            mapper.writeValue(new File(output), data);
        } else if ("xml".equals(format)) {
            var mapper = new XmlMapper();
            mapper.writeValue(new File(output), data);
        }else if ("json".equals(format)) {
            JsonMapper mapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
            var json = mapper.writeValueAsString(data);
            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
        } else {
            throw new IllegalArgumentException("Неизвестный формат данных " + format);
        }
    }
}