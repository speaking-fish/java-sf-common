package com.speakingfish.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Files {

    public static byte[] readAll(File file) {
        int size = (int) file.length();
        final ByteBuffer buffer = ByteBuffer.allocate(size);
        final FileInputStream stream;
        try {
            stream = new FileInputStream(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        try {
            final FileChannel channel = stream.getChannel();
            @SuppressWarnings("unused")
            final int readCount;
            try {
                readCount = channel.read(buffer);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                stream.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.array();
    }

    public static void writeAll(File file, byte[] data) {
        final ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.position(0);
        final FileOutputStream stream;
        try {
            stream = new FileOutputStream(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        try {
            final FileChannel channel = stream.getChannel();
            @SuppressWarnings("unused")
            final int writeCount;
            try {
                writeCount = channel.write(buffer);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                stream.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void appendAll(File file, byte[] data) {
        final ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.position(0);
        final FileOutputStream stream;
        try {
            stream = new FileOutputStream(file, true);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        try {
            final FileChannel channel = stream.getChannel();
            @SuppressWarnings("unused")
            final int writeCount;
            try {
                writeCount = channel.write(buffer);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                stream.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyAll(OutputStream dest, InputStream src, int bufferSize) {
        final byte[] buffer = new byte[bufferSize];
        try {
            do {
                final int readCount = src.read(buffer);
                if(readCount < 0) {
                    break;
                }
                dest.write(buffer, 0, readCount);
            } while(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void copyAll(OutputStream dest, InputStream src) {
        copyAll(dest, src, 1024 * 1024);
    }
    
}
