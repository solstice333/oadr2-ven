//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.04.24 at 12:26:39 PM PDT 
//


package org.enernoc.open.oadr2.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


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
 *         &lt;element ref="{http://docs.oasis-open.org/ns/energyinterop/201110/payloads}eiRequestEvent"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eiRequestEvent"
})
@XmlRootElement(name = "oadrRequestEvent", namespace = "http://openadr.org/oadr-2.0a/2012/07")
@Entity(name = "OadrRequestEvent")
@Table(name = "OADRREQUESTEVENT")
@Inheritance(strategy = InheritanceType.JOINED)
public class OadrRequestEvent implements Serializable, Equals, HashCode, ToString
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://docs.oasis-open.org/ns/energyinterop/201110/payloads", required = true)
    protected EiRequestEvent eiRequestEvent;
    @XmlTransient
    protected Long hjid;

    /**
     * Default no-arg constructor
     * 
     */
    public OadrRequestEvent() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public OadrRequestEvent(final EiRequestEvent eiRequestEvent) {
        this.eiRequestEvent = eiRequestEvent;
    }

    /**
     * Gets the value of the eiRequestEvent property.
     * 
     * @return
     *     possible object is
     *     {@link EiRequestEvent }
     *     
     */
    @ManyToOne(targetEntity = EiRequestEvent.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "EIREQUESTEVENT_OADRREQUESTEV_0")
    public EiRequestEvent getEiRequestEvent() {
        return eiRequestEvent;
    }

    /**
     * Sets the value of the eiRequestEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link EiRequestEvent }
     *     
     */
    public void setEiRequestEvent(EiRequestEvent value) {
        this.eiRequestEvent = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            EiRequestEvent theEiRequestEvent;
            theEiRequestEvent = this.getEiRequestEvent();
            strategy.appendField(locator, this, "eiRequestEvent", buffer, theEiRequestEvent);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof OadrRequestEvent)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final OadrRequestEvent that = ((OadrRequestEvent) object);
        {
            EiRequestEvent lhsEiRequestEvent;
            lhsEiRequestEvent = this.getEiRequestEvent();
            EiRequestEvent rhsEiRequestEvent;
            rhsEiRequestEvent = that.getEiRequestEvent();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "eiRequestEvent", lhsEiRequestEvent), LocatorUtils.property(thatLocator, "eiRequestEvent", rhsEiRequestEvent), lhsEiRequestEvent, rhsEiRequestEvent)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            EiRequestEvent theEiRequestEvent;
            theEiRequestEvent = this.getEiRequestEvent();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "eiRequestEvent", theEiRequestEvent), currentHashCode, theEiRequestEvent);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public OadrRequestEvent withEiRequestEvent(EiRequestEvent value) {
        setEiRequestEvent(value);
        return this;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "Hjid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

}
