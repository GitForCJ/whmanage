/**
 * TFInternalServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wlt.webm.business.bean.webserviceclient;

public class TFInternalServiceLocator extends org.apache.axis.client.Service implements com.wlt.webm.business.bean.webserviceclient.TFInternalService {

    public TFInternalServiceLocator() {
    }


    public TFInternalServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TFInternalServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for edsmpService
    private java.lang.String edsmpService_address = "http://221.179.129.230/nationalsvc/ec/edsmpService";//"http://221.179.129.230/test_nationalsvc/ec/edsmpService";

    public java.lang.String getedsmpServiceAddress() {
        return edsmpService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String edsmpServiceWSDDServiceName = "edsmpService";

    public java.lang.String getedsmpServiceWSDDServiceName() {
        return edsmpServiceWSDDServiceName;
    }

    public void setedsmpServiceWSDDServiceName(java.lang.String name) {
        edsmpServiceWSDDServiceName = name;
    }

    public com.wlt.webm.business.bean.webserviceclient.TFInternal getedsmpService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(edsmpService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getedsmpService(endpoint);
    }

    public com.wlt.webm.business.bean.webserviceclient.TFInternal getedsmpService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.wlt.webm.business.bean.webserviceclient.EdsmpServiceSoapBindingStub _stub = new com.wlt.webm.business.bean.webserviceclient.EdsmpServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getedsmpServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setedsmpServiceEndpointAddress(java.lang.String address) {
        edsmpService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.wlt.webm.business.bean.webserviceclient.TFInternal.class.isAssignableFrom(serviceEndpointInterface)) {
                com.wlt.webm.business.bean.webserviceclient.EdsmpServiceSoapBindingStub _stub = new com.wlt.webm.business.bean.webserviceclient.EdsmpServiceSoapBindingStub(new java.net.URL(edsmpService_address), this);
                _stub.setPortName(getedsmpServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("edsmpService".equals(inputPortName)) {
            return getedsmpService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://221.179.129.230/test_nationalsvc/ec/edsmpService", "TFInternalService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://221.179.129.230/test_nationalsvc/ec/edsmpService", "edsmpService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("edsmpService".equals(portName)) {
            setedsmpServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
