package ru.stqa.mantis.manager;

import java.io.File;

public class JamesCliHelper extends HelperBase {

    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) {
        try {
            new ProcessBuilder("java", "-cp", "james-server-jpa-app.lib/*",
                    "org.apache.james.cli.ServerCmd", "AddUser", email, password)
                    .directory(new File(manager.property("james.workingDir")))
                    .redirectErrorStream(true)
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
// устаревшая конструкция, работала в старых версиях selenium
//CommandLine cmd = new CommandLine(
//        "java", "-cp", "\"james-server-jpa-app.lib/*\"",
//        "org.apache.james.cli.ServerCmd",
//        "AddUser", email, password);
//cmd.setWorkingDirectory(manager.property("james.workingDir"))
//CircularOutputStream out = new CircularOutputStream();
//        cmd.copyOutputTo(out);
//        cmd.execute();
//        cmd.waiFor();
//        System.out.println(out);