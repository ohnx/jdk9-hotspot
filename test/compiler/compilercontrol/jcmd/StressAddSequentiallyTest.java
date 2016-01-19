/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8137167
 * @summary Tests jcmd to be able to add a lot of huge directives
 * @library /testlibrary /test/lib /compiler/testlibrary ../share /
 * @build StressAddSequentiallyTest pool.sub.* pool.subpack.* sun.hotspot.WhiteBox
 *        compiler.testlibrary.CompilerUtils
 *        compiler.compilercontrol.share.actions.*
 * @run main ClassFileInstaller sun.hotspot.WhiteBox
 *                              sun.hotspot.WhiteBox$WhiteBoxPermission
 * @run main/othervm/timeout=300 compiler.compilercontrol.jcmd.StressAddSequentiallyTest
 */

package compiler.compilercontrol.jcmd;

import jdk.test.lib.dcmd.PidJcmdExecutor;

import java.util.List;

public class StressAddSequentiallyTest extends StressAddJcmdBase {
    public static void main(String[] args) {
        new StressAddSequentiallyTest().test();
    }

    @Override
    protected boolean makeConnection(int pid, List<String> commands) {
        commands.forEach(command -> new PidJcmdExecutor(String.valueOf(pid))
                .execute(command));
        return true;
    }
}
