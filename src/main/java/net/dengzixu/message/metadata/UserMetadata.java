package net.dengzixu.message.metadata;

public record UserMetadata(Long uid, String username) implements MessageMetadata {

    /**
     * 用户元数据
     *
     * @param uid      UID
     * @param username 用户名
     */
    public UserMetadata(long uid, String username) {
        this(Long.valueOf(uid), username);
    }

    @Override
    public String convertToString() {
        return String.format("[%s(%s)]", username, uid);
    }
}
