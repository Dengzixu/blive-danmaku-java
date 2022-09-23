package net.dengzixu.message.metadata;

public record UserFaceMetadata(String faceUrl) implements MessageMetadata{
    @Override
    public String convertToString() {
        return "";
    }
}
