package io.github.fr24zh.localizer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public final class ResourceDiagnosticSinkTest {
    @Test
    public void emitsOneActualLogRecordForOneDiagnosticEvent() {
        List<String> records = new ArrayList<>();
        ResourceDiagnosticSink sink = new ResourceDiagnosticSink(records::add);

        sink.emit("resource-hook callback method=getText entryName=redacted");
        sink.emit(null);

        assertEquals(1, records.size());
        assertEquals(
                "FR24ZH: resource-hook callback method=getText entryName=redacted",
                records.get(0));
    }
}
