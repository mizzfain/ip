package kevin.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kevin.task.Task.parseSavedDateTimeString;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void parseSavedDateTimeString_withMinutesPM() {
        assertEquals(LocalDateTime.of(2026, 11, 12, 13, 30),
                parseSavedDateTimeString("12 Nov 2026 130PM"));
    }

    @Test
    public void parseSavedDateTimeString_withoutMinutes() {
        assertEquals(LocalDateTime.of(2026, 11, 12, 13, 0),
                parseSavedDateTimeString("12 Nov 2026 1PM"));
    }

    @Test
    public void parseSavedDateTimeString_twoDigitHourAMSep() {
        assertEquals(LocalDateTime.of(2026, 9, 17, 11, 45),
                parseSavedDateTimeString("17 Sep 2026 1145AM"));
    }
}
