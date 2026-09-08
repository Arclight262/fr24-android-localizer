package io.github.fr24zh.localizer;

final class ResourceDiagnosticSink {
    interface Writer {
        void write(String record);
    }

    private static final String PREFIX = "FR24ZH: ";

    private final Writer writer;

    ResourceDiagnosticSink(Writer writer) {
        this.writer = writer;
    }

    void emit(String message) {
        if (message != null) {
            writer.write(PREFIX + message);
        }
    }
}
