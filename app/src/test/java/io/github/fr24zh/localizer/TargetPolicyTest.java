package io.github.fr24zh.localizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class TargetPolicyTest {
    @Test
    public void acceptsMainAndRemoteProcessesForTargetPackage() {
        assertTrue(TargetPolicy.shouldLoad(
                "com.flightradar24free",
                "com.flightradar24free"));
        assertTrue(TargetPolicy.shouldLoad(
                "com.flightradar24free",
                "com.flightradar24free:remote"));
    }

    @Test
    public void rejectsOtherPackages() {
        assertFalse(TargetPolicy.shouldLoad(
                "com.example.other",
                "com.example.other"));
        assertFalse(TargetPolicy.shouldLoad(
                "com.flightradar24free.fake",
                "com.flightradar24free.fake"));
    }

    @Test
    public void rejectsMissingPackageOrProcessNames() {
        assertFalse(TargetPolicy.shouldLoad(null, null));
        assertFalse(TargetPolicy.shouldLoad("com.flightradar24free", null));
    }
}
