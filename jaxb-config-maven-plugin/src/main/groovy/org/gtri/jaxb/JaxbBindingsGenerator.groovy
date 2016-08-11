package org.gtri.jaxb

class JaxbBindingsGenerator {

    static void writeJaxbBindings(File bindingsFile, IEPDDirectory iepd) {
        bindingsFile << """<?xml version="1.0"?>

<!--
  This file was generated by the GTRI JAXB Bindings Project, ${Calendar.getInstance().getTime()}
-->
<jaxb:bindings
        xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/jaxb http://java.sun.com/xml/ns/jaxb/bindingschema_2_0.xsd"
        jaxb:extensionBindingPrefixes="xjc"
        version="2.1">

    ${generateBindings(iepd)}

</jaxb:bindings>
"""
    }


    static String generateBindings(IEPDDirectory iepd){
        StringWriter writer = new StringWriter();


        for( SchemaInfo schemaInfo : iepd.schemas ){
            File schemaFile = schemaInfo.file;
            String relativePath = schemaFile.canonicalPath.replace(iepd.getBase().canonicalPath + File.separator + "xsd", "../xsd");
            LogHolder.getLog().debug("Writing binding for: "+relativePath);

            writer << """

    <jaxb:bindings schemaLocation="${relativePath}" node="//xsd:schema">
        <jaxb:schemaBindings>
            <jaxb:package name="${iepd.uriToPackageMapping.get(schemaInfo.targetNamespace)}" />
        </jaxb:schemaBindings>
    </jaxb:bindings>

"""

        }//end each schema Info

        return writer.toString();
    }//end generateBindings()



}
