package com.mathworks.ci;

/**
 * Copyright 2018-2024 The MathWorks, Inc.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import hudson.console.LineTransformationOutputStream;
import hudson.model.TaskListener;

public class ListenerLogDecorator extends LineTransformationOutputStream {
    private final OutputStream listener;
    private final Charset charsetUtf8 = StandardCharsets.UTF_8;

    public ListenerLogDecorator(TaskListener listner) {
        this.listener = listner != null ? listner.getLogger() : null;
    }

    @Override
    protected void eol(byte[] bytes, int length) throws IOException {
        if (this.listener == null) {
            return;
        }

        String line = new String(bytes, 0, length, charsetUtf8);
        this.listener.write(line.getBytes(charsetUtf8));
    }
}
