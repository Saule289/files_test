
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.saule289.Donuts;

import java.io.InputStream;
import java.io.InputStreamReader;
;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    ClassLoader cl = JsonTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();
    @DisplayName("Отображение данных json")
    @Test


    void jsonTest() throws Exception {


        try (
                InputStream resource = cl.getResourceAsStream("donuts.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Donuts donuts = objectMapper.readValue(reader, Donuts.class);
            assertThat(donuts.price).isEqualTo(1.55);
            assertThat(donuts.batters).contains("chocolate");
            assertThat(donuts.in_stock).isTrue();
        }
    }
}
