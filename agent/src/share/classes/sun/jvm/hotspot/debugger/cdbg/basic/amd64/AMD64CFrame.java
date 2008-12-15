/*
 * Copyright 2003 Sun Microsystems, Inc.  All Rights Reserved.
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
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 *
 */

package sun.jvm.hotspot.debugger.cdbg.basic.amd64;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.cdbg.*;
import sun.jvm.hotspot.debugger.cdbg.basic.*;

/** Basic AMD64 frame functionality providing sender() functionality. */

public class AMD64CFrame extends BasicCFrame {
  private Address rbp;
  private Address pc;

  private static final int ADDRESS_SIZE = 8;

  /** Constructor for topmost frame */
  public AMD64CFrame(CDebugger dbg, Address rbp, Address pc) {
    super(dbg);
    this.rbp = rbp;
    this.pc  = pc;
  }

  public CFrame sender() {
    if (rbp == null) {
      return null;
    }

    Address nextRBP = rbp.getAddressAt( 0 * ADDRESS_SIZE);
    if (nextRBP == null) {
      return null;
    }
    Address nextPC  = rbp.getAddressAt( 1 * ADDRESS_SIZE);
    if (nextPC == null) {
      return null;
    }
    return new AMD64CFrame(dbg(), nextRBP, nextPC);
  }

  public Address pc() {
    return pc;
  }

  public Address localVariableBase() {
    return rbp;
  }
}
