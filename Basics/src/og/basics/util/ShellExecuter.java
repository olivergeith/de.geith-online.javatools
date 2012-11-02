//******************************************************************************
//
//     Copyright (c) 2012 by Funkwerk Information Technologies GmbH, 24145 Kiel
//
// All rights reserved. The reproduction, distribution and utilisation of this document
// as well as the communication of its contents to others without explicit authorisation
// is prohibited. Offenders will be held liable for the payment of damages.
// All rights reserved in the event of the grant of a patent, utility model or design.
// (DIN ISO 16016:2002-05, Section 5.1)
//
// Alle Rechte vorbehalten. Weitergabe sowie Vervielfaeltigung dieses Dokuments,
// Verwertung und Mitteilung seines Inhalts sind verboten, soweit nicht ausdruecklich
// gestattet. Zuwiderhandlungen verpflichten zu Schadenersatz. Alle Rechte fuer den
// Fall der Patent-, Geschmacks- und Gebrauchsmustererteilung vorbehalten. 
// (DIN ISO 16016:2002-05, Abschnitt 5.1)
//
//******************************************************************************

package og.basics.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets the Zip-files for Orgdata and Infra from LeiDisSK via shell script which
 * is located in directory $RCOM
 * 
 * @author geith
 * 
 */
public class ShellExecuter {

	/**
	 * Constructor
	 * 
	 */
	public ShellExecuter() {
		super();
	}

	/**
	 * Starts shell command and puts all output of it on System.out...
	 * 
	 * @param cmd
	 *            the execution command
	 * @return true if execCode is zero
	 */
	public boolean exec(final List<String> cmd) {

		System.out.println("Executing " + cmd + " ...");
		Process getterProcess = null;
		int exitStatus = 0;
		// declaring br outside the try(), to close it in finally()
		BufferedReader br = null;
		try {
			// NOTEST: execution of shell script is tested separately
			// creation of shell process
			final ProcessBuilder processBuilder = new ProcessBuilder(cmd);
			// merge stdout, stderr of process
			processBuilder.redirectErrorStream(true);
			getterProcess = processBuilder.start();

			// reading output from shell process
			final InputStream is = getterProcess.getInputStream();
			final InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			br.close();
		} catch (final IOException ioe) {
			System.out.println("Error during execution of " + cmd + ": " + ioe.getMessage());
			return false;
		} finally {
			if (getterProcess != null) {
				try {
					// waiting for exit status of process
					exitStatus = getterProcess.waitFor();
					// missing these was causing the mass amounts of open
					// 'files'
					getterProcess.getInputStream().close();
					getterProcess.getOutputStream().close();
					getterProcess.getErrorStream().close();
					// Closing if BufferedReader (Closes the InputStreamReader
					// as well)
					if (br != null) {
						br.close();
					}

					if (exitStatus > 0) {
						System.out.println("Error during execution of " + cmd + ": Exit code " + exitStatus);
						return false;
					}
				} catch (final IOException ioe) {
					System.out.println("Error during execution of " + cmd + ": " + ioe.getMessage());
					return false;
				} catch (final InterruptedException ire) {
					System.out.println("Error during execution of " + cmd + ": " + ire.getMessage());
					return false;
				}
			}
		}
		System.out.println("Execution " + cmd + " successfully completed");
		return true;
	}

	/**
	 * This is just a demo methode to show you how to use the ShellExecuter
	 * 
	 * @return
	 */
	public boolean ecexuteDemo() {
		final List<String> cmd = new ArrayList<String>();
		cmd.add("./test.bat");
		cmd.add("INFRA");
		cmd.add("param1");
		cmd.add("param2");
		return exec(cmd);
	}

}
