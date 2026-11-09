package study;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StudyEnvironmentTest {
    @Test
    void testsRunOnJava17() {
        assertEquals(17, Runtime.version().feature());
    }
}
