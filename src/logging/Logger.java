package logging;

import utils.Const;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Logger implements LoggerCommon {
    private static final Logger INSTANCE = new Logger();
    private String FileName;

    private Logger() {
        this.FileName = Const.DATA_DIRECTORY + "default.log";
    }

    public static Logger getLogger() {
        return INSTANCE;
    }

    @Override
    public void setFileName(String fileName) {
        this.FileName = Const.DATA_DIRECTORY + fileName + ".log";
    }

    @Override
    public void log(String message) {
//todo уровни логирования ?
        File file = new File(FileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла", e);
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            writer.write(message + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка открытия файла", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
