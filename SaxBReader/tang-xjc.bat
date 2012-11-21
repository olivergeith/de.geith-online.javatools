java -jar trang.jar xml/flis_org_156_geschaeftspartner.xml 	xsd/geschaeftspartner.xsd
java -jar trang.jar xml/flis_org_156_orgt_bediener.xml 		xsd/bediener.xsd
java -jar trang.jar xml/flis_org_156_orgt_farben.xml 		xsd/farben.xsd
java -jar trang.jar xml/flis_org_156_orgt_symbolische_farben.xml 		xsd/symbolische_farben.xsd
java -jar trang.jar xml/flis_org_156_orgt_strichdefinitionen.xml 		xsd/strichdefinitionen.xsd


xjc -d src_gen -p de.og.orgdatenreader.orgd.geschaeftspartner xsd/geschaeftspartner.xsd
xjc -d src_gen -p de.og.orgdatenreader.orgd.bediener xsd/bediener.xsd
xjc -d src_gen -p de.og.orgdatenreader.orgd.bediener2 xsd/bediener2.xsd
xjc -d src_gen -p de.og.orgdatenreader.orgd.farben xsd/farben.xsd
xjc -d src_gen -p de.og.orgdatenreader.orgd.sybolischefarben xsd/symbolische_farben.xsd
xjc -d src_gen -p de.og.orgdatenreader.orgd.strichdefinitionen xsd/strichdefinitionen.xsd


java -jar trang.jar xml/iserv_00156_BETRIEBSSTELLEN.xml 		xsd/bestriebsstellen.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.bestriebsstellen   xsd/bestriebsstellen.xsd

java -jar trang.jar xml/iserv_00156_STRECKEN.xml 		xsd/strecken.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.strecken   xsd/strecken.xsd

java -jar trang.jar xml/iserv_00156_STRECKENABSCHNITTE.xml 		xsd/streckenabschnitte.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.streckenabschnitte xsd/streckenabschnitte.xsd

java -jar trang.jar xml/iserv_00156_SPURPLANKNOTEN.xml 		xsd/spurplanknoten.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.spurplanknoten xsd/spurplanknoten.xsd

java -jar trang.jar xml/iserv_00156_ABSCHNITTSGRENZKNOTEN.xml 		xsd/abschnittsgrenzknoten.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.abschnittsgrenzknoten xsd/abschnittsgrenzknoten.xsd

java -jar trang.jar xml/iserv_00156_DURCHGANGSKNOTEN.xml 		xsd/durchgangsknoten.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.durchgangsknoten xsd/durchgangsknoten.xsd

java -jar trang.jar xml/iserv_00156_STRECKENABSCHNITTE.xml 		xsd/streckenabschnitte.xsd
xjc -d src_gen -p de.og.orgdatenreader.infra.streckenabschnitte xsd/streckenabschnitte.xsd