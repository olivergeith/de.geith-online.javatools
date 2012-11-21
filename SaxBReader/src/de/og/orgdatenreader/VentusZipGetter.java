package de.og.orgdatenreader;

import java.io.File;
import java.io.IOException;

/**
 * Gets the Zip-files for Orgdata and Infra from Leidissk via Skript in $RCOM
 * 
 * @author geith
 * 
 */
public class VentusZipGetter {

  /**
   * die Leida-S-Version die im Ventus-Telegramm stand
   */
  private final int dasVersion;

  /**
   * Constructor
   * 
   * @param dasVersion
   *          die Leida-S-Version zu der die Daten besorgt werden sollen
   */
  public VentusZipGetter(final int dasVersion) {
    super();
    this.dasVersion = dasVersion;
  }

  /**
   * @return true wenn Daten erfolgreich übertragen
   * 
   */
  public boolean getInfra() {

    final String rcomDir = System.getenv("RCOM");
    if (rcomDir.length() == 0) {
      return false;
    }
    final String cmd = rcomDir + File.pathSeparator + "org_infra_getter.sh INFRA " + dasVersion;

    try {
      Runtime.getRuntime().exec(cmd);
    } catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
