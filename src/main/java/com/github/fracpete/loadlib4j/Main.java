/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Main.java
 * Copyright (C) 2018 FracPete
 */

package com.github.fracpete.loadlib4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * For testing the loading of native libraries in Java, using {@link System#loadLibrary(String)}.
 * Just supply all the names of the libraries that you want to load
 * on the commandline.
 */
public class Main
  implements Serializable {

  /** whether to output progress on stdout. */
  protected boolean m_OutputProgress;

  /**
   * Sets whether to output progress information on stdout.
   *
   * @param value true if to output progress
   */
  public void setOutputProgress(boolean value) {
    m_OutputProgress = value;
  }

  /**
   * Returns whether to output progress information on stdout.
   *
   * @return true if to output progress
   */
  public boolean getOutputProgress() {
    return m_OutputProgress;
  }

  /**
   * Performs the testing.
   *
   * @param libs the names of the libraries (incl extension, but no path)
   * @return the map of the library names and whether they were successfully loaded
   */
  public Map<String,Boolean> testLoading(String[] libs) {
    Map<String,Boolean> result;

    result = new HashMap<>();

    for (String lib: libs) {
      // try as is
      try {
        System.loadLibrary(lib);
        result.put(lib, true);
      }
      catch (Throwable t) {
        result.put(lib, false);
      }
      if (m_OutputProgress)
        System.out.println(lib + ": " + result.get(lib));

      // extension?
      if (lib.contains(".")) {
        lib = lib.substring(0, lib.indexOf("."));
        try {
          System.loadLibrary(lib);
          result.put(lib, true);
        }
        catch (Throwable t) {
          result.put(lib, false);
        }
        if (m_OutputProgress)
          System.out.println(lib + ": " + result.get(lib));
      }

      // starts with "lib"?
      if (lib.startsWith("lib")) {
        lib = lib.substring(3);
        try {
          System.loadLibrary(lib);
          result.put(lib, true);
        }
        catch (Throwable t) {
          result.put(lib, false);
        }
        if (m_OutputProgress)
          System.out.println(lib + ": " + result.get(lib));
      }
    }

    return result;
  }

  /**
   * Test the libraries supplied as arguments (outputs progress to stdout).
   *
   * @param args	the libraries to test
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("\nUsage: " + Main.class.getName() + " [lib1, ...]\n");
      return;
    }

    Main main = new Main();
    main.setOutputProgress(true);
    main.testLoading(args);
  }
}
