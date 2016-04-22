package ecinterface.adc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdcServicesResult" type="{http://adc.ecinterface/}NGEC" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "adcServicesResult" })
@XmlRootElement(name = "AdcServicesResponse")
public class AdcServicesResponse {

	@XmlElement(name = "AdcServicesResult")
	protected NGEC adcServicesResult;

	/**
	 * Gets the value of the adcServicesResult property.
	 * 
	 * @return possible object is {@link NGEC }
	 * 
	 */
	public NGEC getAdcServicesResult() {
		return adcServicesResult;
	}

	/**
	 * Sets the value of the adcServicesResult property.
	 * 
	 * @param value
	 *            allowed object is {@link NGEC }
	 * 
	 */
	public void setAdcServicesResult(NGEC value) {
		this.adcServicesResult = value;
	}

}
