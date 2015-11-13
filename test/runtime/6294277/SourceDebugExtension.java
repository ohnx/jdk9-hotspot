/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 6294277
 * @summary java -Xdebug crashes on SourceDebugExtension attribute larger than 64K
 * @run main/othervm -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n SourceDebugExtension
 */
import java.io.*;

public class SourceDebugExtension extends ClassLoader
{
    static final int attrSize = 68000;
    static byte[] header = {
(byte)0xca, (byte)0xfe, (byte)0xba, (byte)0xbe, (byte)0x00, (byte)0x00, (byte)0x00,
(byte)0x32, (byte)0x00, (byte)0x1e, (byte)0x0a, (byte)0x00, (byte)0x06, (byte)0x00,
(byte)0x0f, (byte)0x09, (byte)0x00, (byte)0x10, (byte)0x00, (byte)0x11, (byte)0x08,
(byte)0x00, (byte)0x12, (byte)0x0a, (byte)0x00, (byte)0x13, (byte)0x00, (byte)0x14,
(byte)0x07, (byte)0x00, (byte)0x15, (byte)0x07, (byte)0x00, (byte)0x16, (byte)0x01,
(byte)0x00, (byte)0x06, (byte)0x3c, (byte)0x69, (byte)0x6e, (byte)0x69, (byte)0x74,
(byte)0x3e, (byte)0x01, (byte)0x00, (byte)0x03, (byte)0x28, (byte)0x29, (byte)0x56,
(byte)0x01, (byte)0x00, (byte)0x04, (byte)0x43, (byte)0x6f, (byte)0x64, (byte)0x65,
(byte)0x01, (byte)0x00, (byte)0x0f, (byte)0x4c, (byte)0x69, (byte)0x6e, (byte)0x65,
(byte)0x4e, (byte)0x75, (byte)0x6d, (byte)0x62, (byte)0x65, (byte)0x72, (byte)0x54,
(byte)0x61, (byte)0x62, (byte)0x6c, (byte)0x65, (byte)0x01, (byte)0x00, (byte)0x04,
(byte)0x6d, (byte)0x61, (byte)0x69, (byte)0x6e, (byte)0x01, (byte)0x00, (byte)0x16,
(byte)0x28, (byte)0x5b, (byte)0x4c, (byte)0x6a, (byte)0x61, (byte)0x76, (byte)0x61,
(byte)0x2f, (byte)0x6c, (byte)0x61, (byte)0x6e, (byte)0x67, (byte)0x2f, (byte)0x53,
(byte)0x74, (byte)0x72, (byte)0x69, (byte)0x6e, (byte)0x67, (byte)0x3b, (byte)0x29,
(byte)0x56, (byte)0x01, (byte)0x00, (byte)0x0a, (byte)0x53, (byte)0x6f, (byte)0x75,
(byte)0x72, (byte)0x63, (byte)0x65, (byte)0x46, (byte)0x69, (byte)0x6c, (byte)0x65,
(byte)0x01, (byte)0x00, (byte)0x0d, (byte)0x54, (byte)0x65, (byte)0x73, (byte)0x74,
(byte)0x50, (byte)0x72, (byte)0x6f, (byte)0x67, (byte)0x2e, (byte)0x6a, (byte)0x61,
(byte)0x76, (byte)0x61, (byte)0x0c, (byte)0x00, (byte)0x07, (byte)0x00, (byte)0x08,
(byte)0x07, (byte)0x00, (byte)0x17, (byte)0x0c, (byte)0x00, (byte)0x18, (byte)0x00,
(byte)0x19, (byte)0x01, (byte)0x00, (byte)0x34, (byte)0x54, (byte)0x65, (byte)0x73,
(byte)0x74, (byte)0x20, (byte)0x70, (byte)0x72, (byte)0x6f, (byte)0x67, (byte)0x72,
(byte)0x61, (byte)0x6d, (byte)0x20, (byte)0x66, (byte)0x6f, (byte)0x72, (byte)0x20,
(byte)0x62, (byte)0x69, (byte)0x67, (byte)0x20, (byte)0x53, (byte)0x6f, (byte)0x75,
(byte)0x72, (byte)0x63, (byte)0x65, (byte)0x44, (byte)0x65, (byte)0x62, (byte)0x75,
(byte)0x67, (byte)0x45, (byte)0x78, (byte)0x74, (byte)0x65, (byte)0x6e, (byte)0x73,
(byte)0x69, (byte)0x6f, (byte)0x6e, (byte)0x20, (byte)0x61, (byte)0x74, (byte)0x74,
(byte)0x72, (byte)0x69, (byte)0x62, (byte)0x75, (byte)0x74, (byte)0x65, (byte)0x73,
(byte)0x07, (byte)0x00, (byte)0x1a, (byte)0x0c, (byte)0x00, (byte)0x1b, (byte)0x00,
(byte)0x1c, (byte)0x01, (byte)0x00, (byte)0x08, (byte)0x54, (byte)0x65, (byte)0x73,
(byte)0x74, (byte)0x50, (byte)0x72, (byte)0x6f, (byte)0x67, (byte)0x01, (byte)0x00,
(byte)0x10, (byte)0x6a, (byte)0x61, (byte)0x76, (byte)0x61, (byte)0x2f, (byte)0x6c,
(byte)0x61, (byte)0x6e, (byte)0x67, (byte)0x2f, (byte)0x4f, (byte)0x62, (byte)0x6a,
(byte)0x65, (byte)0x63, (byte)0x74, (byte)0x01, (byte)0x00, (byte)0x10, (byte)0x6a,
(byte)0x61, (byte)0x76, (byte)0x61, (byte)0x2f, (byte)0x6c, (byte)0x61, (byte)0x6e,
(byte)0x67, (byte)0x2f, (byte)0x53, (byte)0x79, (byte)0x73, (byte)0x74, (byte)0x65,
(byte)0x6d, (byte)0x01, (byte)0x00, (byte)0x03, (byte)0x6f, (byte)0x75, (byte)0x74,
(byte)0x01, (byte)0x00, (byte)0x15, (byte)0x4c, (byte)0x6a, (byte)0x61, (byte)0x76,
(byte)0x61, (byte)0x2f, (byte)0x69, (byte)0x6f, (byte)0x2f, (byte)0x50, (byte)0x72,
(byte)0x69, (byte)0x6e, (byte)0x74, (byte)0x53, (byte)0x74, (byte)0x72, (byte)0x65,
(byte)0x61, (byte)0x6d, (byte)0x3b, (byte)0x01, (byte)0x00, (byte)0x13, (byte)0x6a,
(byte)0x61, (byte)0x76, (byte)0x61, (byte)0x2f, (byte)0x69, (byte)0x6f, (byte)0x2f,
(byte)0x50, (byte)0x72, (byte)0x69, (byte)0x6e, (byte)0x74, (byte)0x53, (byte)0x74,
(byte)0x72, (byte)0x65, (byte)0x61, (byte)0x6d, (byte)0x01, (byte)0x00, (byte)0x07,
(byte)0x70, (byte)0x72, (byte)0x69, (byte)0x6e, (byte)0x74, (byte)0x6c, (byte)0x6e,
(byte)0x01, (byte)0x00, (byte)0x15, (byte)0x28, (byte)0x4c, (byte)0x6a, (byte)0x61,
(byte)0x76, (byte)0x61, (byte)0x2f, (byte)0x6c, (byte)0x61, (byte)0x6e, (byte)0x67,
(byte)0x2f, (byte)0x53, (byte)0x74, (byte)0x72, (byte)0x69, (byte)0x6e, (byte)0x67,
(byte)0x3b, (byte)0x29, (byte)0x56, (byte)0x01, (byte)0x00, (byte)0x14, (byte)0x53,
(byte)0x6f, (byte)0x75, (byte)0x72, (byte)0x63, (byte)0x65, (byte)0x44, (byte)0x65,
(byte)0x62, (byte)0x75, (byte)0x67, (byte)0x45, (byte)0x78, (byte)0x74, (byte)0x65,
(byte)0x6e, (byte)0x73, (byte)0x69, (byte)0x6f, (byte)0x6e, (byte)0x00, (byte)0x21,
(byte)0x00, (byte)0x05, (byte)0x00, (byte)0x06, (byte)0x00, (byte)0x00, (byte)0x00,
(byte)0x00, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x07,
(byte)0x00, (byte)0x08, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09, (byte)0x00,
(byte)0x00, (byte)0x00, (byte)0x1d, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01,
(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x05, (byte)0x2a, (byte)0xb7, (byte)0x00,
(byte)0x01, (byte)0xb1, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00,
(byte)0x0a, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x06, (byte)0x00, (byte)0x01,
(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09, (byte)0x00,
(byte)0x0b, (byte)0x00, (byte)0x0c, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x09,
(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x25, (byte)0x00, (byte)0x02, (byte)0x00,
(byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0xb2, (byte)0x00,
(byte)0x02, (byte)0x12, (byte)0x03, (byte)0xb6, (byte)0x00, (byte)0x04, (byte)0xb1,
(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x0a, (byte)0x00,
(byte)0x00, (byte)0x00, (byte)0x0a, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00,
(byte)0x00, (byte)0x03, (byte)0x00, (byte)0x08, (byte)0x00, (byte)0x04, (byte)0x00,
(byte)0x02, (byte)0x00, (byte)0x0d, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x02,
(byte)0x00, (byte)0x0e, (byte)0x00, (byte)0x1d, (byte)0x00, (byte)0x01, (byte)0x09,
(byte)0xa0
  };

    public static void main(String[] args) throws Exception
    {
        try {
            SourceDebugExtension loader = new SourceDebugExtension();
            /* The test program creates a class file from the header
             * stored above and adding the content of a SourceDebugExtension
             * attribute made of the character 0x02 repeated 68000 times.
             * This attribute doesn't follow the syntax specified in JSR 45
             * but it's fine because this test just checks that the JVM is
             * able to load a class file with a SourceDebugExtension
             * attribute bigger than 64KB. The JVM doesn't try to
             * parse the content of the attribute, this work is performed
             * by the SA or external tools.
             */
            byte[] buf = new byte[header.length + attrSize];
            for(int i=0; i<header.length; i++) {
                buf[i] = header[i];
            }
            for(int i=0; i<attrSize; i++) {
                buf[header.length+i] = (byte)0x02;
            }
            Class c = loader.defineClass("TestProg", buf, 0, buf.length);
            System.out.println("Test PASSES");
        } catch(Exception e) {
            System.out.println("Test FAILS");
        }
    }
}
