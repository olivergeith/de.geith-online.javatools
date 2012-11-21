package de.og.orgdatenreader;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.og.orgdatenreader.infra.durchgangsknoten.DURCHGANGSKNOTEN;
import de.og.orgdatenreader.infra.durchgangsknoten.Infrastruktur;
import de.og.orgdatenreader.infra.durchgangsknoten.Tabelle;

public class DGKNReader {
  /**
   * @param args
   * @throws Exception
   */
  public static void main(final String[] args) throws Exception {
    // Package
    final JAXBContext jc = JAXBContext.newInstance("de.og.orgdatenreader.infra.durchgangsknoten");
    final Unmarshaller unmarshaller = jc.createUnmarshaller();

    final Infrastruktur infra =
        (Infrastruktur) unmarshaller.unmarshal(new File("./xml/iserv_00156_DURCHGANGSKNOTEN.xml"));
    final Tabelle tab = infra.getTabelle();
    final List<DURCHGANGSKNOTEN> list = tab.getDURCHGANGSKNOTEN();

    for (final DURCHGANGSKNOTEN obj : list) {
      System.out.println("Durchgangsknoten: " + obj.getSPLKNKEY() + "\t " + obj.getFZMPGLEISBEZ());
    }
  }
}
