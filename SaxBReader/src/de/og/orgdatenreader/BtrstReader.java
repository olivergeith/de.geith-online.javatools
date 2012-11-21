package de.og.orgdatenreader;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.og.orgdatenreader.infra.bestriebsstellen.BETRIEBSSTELLEN;
import de.og.orgdatenreader.infra.bestriebsstellen.Infrastruktur;
import de.og.orgdatenreader.infra.bestriebsstellen.Tabelle;

public class BtrstReader {
  /**
   * @param args
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
    // Package
    final JAXBContext jc = JAXBContext.newInstance("de.og.orgdatenreader.infra.bestriebsstellen");
    final Unmarshaller unmarshaller = jc.createUnmarshaller();

    final Infrastruktur infra =
        (Infrastruktur) unmarshaller.unmarshal(new File("./xml/iserv_00156_BETRIEBSSTELLEN.xml"));
    final Tabelle tab = infra.getTabelle();
    final List<BETRIEBSSTELLEN> data = tab.getBETRIEBSSTELLEN();
    final long start = System.currentTimeMillis();
    int i = 0;
    for (final BETRIEBSSTELLEN obj : data) {
      i++;
      System.out.println(i + ". Btrst: " + obj.getBTRSTDS100() + "\t " + obj.getBTRSTNAME40());

    }
    final long end = System.currentTimeMillis();
    final long diff = end - start;
    System.out.println("Done in " + diff + " ms");

  }
}
