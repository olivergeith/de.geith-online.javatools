//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.21 at 11:56:06 AM MESZ 
//


package de.og.orgdatenreader.infra.durchgangsknoten;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Tabelle"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AktivierungsDatum" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="DatenQuelle" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="GenDatum" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="GlobalVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tabelle"
})
@XmlRootElement(name = "Infrastruktur")
public class Infrastruktur {

    @XmlElement(name = "Tabelle", required = true)
    protected Tabelle tabelle;
    @XmlAttribute(name = "AktivierungsDatum", required = true)
    protected BigInteger aktivierungsDatum;
    @XmlAttribute(name = "DatenQuelle", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String datenQuelle;
    @XmlAttribute(name = "GenDatum", required = true)
    protected BigInteger genDatum;
    @XmlAttribute(name = "GlobalVersion", required = true)
    protected BigInteger globalVersion;

    /**
     * Gets the value of the tabelle property.
     * 
     * @return
     *     possible object is
     *     {@link Tabelle }
     *     
     */
    public Tabelle getTabelle() {
        return tabelle;
    }

    /**
     * Sets the value of the tabelle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tabelle }
     *     
     */
    public void setTabelle(Tabelle value) {
        this.tabelle = value;
    }

    /**
     * Gets the value of the aktivierungsDatum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAktivierungsDatum() {
        return aktivierungsDatum;
    }

    /**
     * Sets the value of the aktivierungsDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAktivierungsDatum(BigInteger value) {
        this.aktivierungsDatum = value;
    }

    /**
     * Gets the value of the datenQuelle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatenQuelle() {
        return datenQuelle;
    }

    /**
     * Sets the value of the datenQuelle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatenQuelle(String value) {
        this.datenQuelle = value;
    }

    /**
     * Gets the value of the genDatum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGenDatum() {
        return genDatum;
    }

    /**
     * Sets the value of the genDatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGenDatum(BigInteger value) {
        this.genDatum = value;
    }

    /**
     * Gets the value of the globalVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGlobalVersion() {
        return globalVersion;
    }

    /**
     * Sets the value of the globalVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGlobalVersion(BigInteger value) {
        this.globalVersion = value;
    }

}
