/*
 * Copyright 2013 Stephen Connolly.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.jszip.gems.sass;

import org.apache.commons.io.IOUtils;
import org.jruby.embed.ScriptingContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SmokeTest {

    public String loadResource(String name) throws IOException {
        InputStream stream = null;
        try {
            stream = getClass().getResourceAsStream(name);
            return IOUtils.toString(stream);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    @Test
    public void processesSass() throws IOException {
        final ScriptingContainer container = new ScriptingContainer();
        container.put("sass", true);
        container.put("template", loadResource("smoke.sass"));
        final String result = (String)container.runScriptlet(loadResource("engine.rb"));
        assertThat(result, notNullValue());
        assertThat(result.replaceAll("\\s+", ""), containsString("border-color:#3bbfce"));
    }

    @Test
    public void processesScss() throws IOException {
        final ScriptingContainer container = new ScriptingContainer();
        container.put("sass", false);
        container.put("template", loadResource("smoke.scss"));
        final String result = (String)container.runScriptlet(loadResource("engine.rb"));
        assertThat(result, notNullValue());
        assertThat(result.replaceAll("\\s+", ""), containsString("border-color:#3bbfce"));
    }
}
