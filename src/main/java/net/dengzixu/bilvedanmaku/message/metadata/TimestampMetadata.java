package net.dengzixu.bilvedanmaku.message.metadata;

import java.text.SimpleDateFormat;
import java.util.Date;

public record TimestampMetadata(Long timestamp, Boolean generated) implements MessageMetadata {

    /**
     * @param timestamp 秒级 时间戳
     * @param generated 是否为生成时间戳
     *                  默认值为 false
     */
    public TimestampMetadata(long timestamp, Boolean generated) {
        this(Long.valueOf(timestamp), generated);
    }

    /**
     * @param timestamp 秒级 时间戳
     */
    public TimestampMetadata(Long timestamp) {
        this(timestamp, Boolean.FALSE);
    }

    /**
     * @param timestamp 秒级 时间戳
     */
    public TimestampMetadata(long timestamp) {
        this(Long.valueOf(timestamp));
    }

    @Override
    public String convertToString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return String.format("[%s]", simpleDateFormat.format(new Date(timestamp * 1000)));
    }
}
