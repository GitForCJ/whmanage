package ecinterface.adc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Response complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RspCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RspDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", propOrder = { "rspCode", "rspDesc" })
public class Response {

	@XmlElement(name = "RspCode")
	protected String rspCode;
	@XmlElement(name = "RspDesc")
	protected String rspDesc;

	/**
	 * Gets the value of the rspCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRspCode() {
		return rspCode;
	}

	/**
	 * Sets the value of the rspCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRspCode(String value) {
		this.rspCode = value;
	}

	/**
	 * Gets the value of the rspDesc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRspDesc() {
		return rspDesc;
	}

	/**
	 * Sets the value of the rspDesc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRspDesc(String value) {
		this.rspDesc = value;
	}

}
