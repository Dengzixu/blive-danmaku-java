package net.dengzixu.bilvedanmaku.uitls;

import org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class UncompressUtils {
    public static byte[] uncompress(byte[] data, Compressor compressor) {
        if (null == data || data.length == 0) {
            return null;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        InputStream inputStream = null;

        try {
            switch (compressor) {
                case ZLIB -> inputStream = new InflaterInputStream(byteArrayInputStream);
                case BROTLI -> inputStream = new BrotliCompressorInputStream(byteArrayInputStream);
            }
            byte[] buffer = new byte[1024];

            int n;
            while ((n = inputStream.read(buffer)) >= 0) {
                byteArrayOutputStream.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public enum Compressor {
        ZLIB,
        BROTLI;
    }
}
