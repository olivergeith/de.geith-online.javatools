//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.21 at 11:55:59 AM MESZ 
//


package de.og.orgdatenreader.orgd.geschaeftspartner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="PARTNER_EXTERN_KEY" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="PARTNER_KEY" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="PARTNER_NAME40" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="PARTNER_NR" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Format")
public class Format {

    @XmlAttribute(name = "PARTNER_EXTERN_KEY", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String partnerexternkey;
    @XmlAttribute(name = "PARTNER_KEY", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String partnerkey;
    @XmlAttribute(name = "PARTNER_NAME40", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String partnername40;
    @XmlAttribute(name = "PARTNER_NR", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String partnernr;

    /**
     * Gets the value of the partnerexternkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNEREXTERNKEY() {
        return partnerexternkey;
    }

    /**
     * Sets the value of the partnerexternkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNEREXTERNKEY(String value) {
        this.partnerexternkey = value;
    }

    /**
     * Gets the value of the partnerkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNERKEY() {
        return partnerkey;
    }

    /**
     * Sets the value of the partnerkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNERKEY(String value) {
        this.partnerkey = value;
    }

    /**
     * Gets the value of the partnername40 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNERNAME40() {
        return partnername40;
    }

    /**
     * Sets the value of the partnername40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNERNAME40(String value) {
        this.partnername40 = value;
    }

    /**
     * Gets the value of the partnernr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARTNERNR() {
        return partnernr;
    }

    /**
     * Sets the value of the partnernr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARTNERNR(String value) {
        this.partnernr = value;
    }

}