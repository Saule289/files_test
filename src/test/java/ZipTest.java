import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static io.netty.util.internal.SystemPropertyUtil.contains;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipTest {
    ClassLoader cl = ZipTest.class.getClassLoader();

    @DisplayName("Проверка содержиого файлов в архиве")
    @Test
    void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("forTest.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("Plan.pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("Yukon Department of Education");
                } else if (entry.getName().contains("Test cases.xlsx")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(0).getCell(3).getStringCellValue()).contains("Expected result");
                } else if (entry.getName().contains("Test results.csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(0)[0]).contains("Test case");
                }

            }
        }
    }


}

