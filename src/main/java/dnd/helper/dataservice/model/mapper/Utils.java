package dnd.helper.dataservice.model.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Utils {
    public static LocalDateTime toLocalDateTime(OffsetDateTime time) {
        return time.toLocalDateTime();
    }

    public static OffsetDateTime toOffsetDateTime(LocalDateTime time) {
        return time.atOffset(ZoneOffset.of("+04:00"));
    }
}
