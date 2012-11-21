package de.og.orgdatenreader;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import de.og.orgdatenreader.orgd.bediener.Infrastruktur;
import de.og.orgdatenreader.orgd.bediener.ORGTBEDIENER;
import de.og.orgdatenreader.orgd.bediener.Tabelle;

public class BedienerReader {
  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    // Package
    JAXBContext jc = JAXBContext.newInstance("de.og.orgdatenreader.orgd.bediener");
    Unmarshaller unmarshaller = jc.createUnmarshaller();

    Infrastruktur infra = (Infrastruktur) unmarshaller.unmarshal(new File("./xml/flis_org_156_orgt_bediener.xml"));
    Tabelle tab = infra.getTabelle();
    List<ORGTBEDIENER> bediener = tab.getORGTBEDIENER();

    for (ORGTBEDIENER bed : bediener) {
      System.out.println("Bediener: " + bed.getBEDKENNUNG() + "\t " + bed.getBEDPASSWORT());
    }
  }
}
