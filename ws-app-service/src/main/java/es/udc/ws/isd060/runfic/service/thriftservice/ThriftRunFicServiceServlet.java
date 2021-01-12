package es.udc.ws.isd060.runfic.service.thriftservice;

import es.udc.ws.isd060.runfic.thrift.ThriftRunFicService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

public class ThriftRunFicServiceServlet extends TServlet {

    public ThriftRunFicServiceServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {

        return new ThriftRunFicService.Processor<ThriftRunFicService.Iface>(
            new ThriftRunFicServiceImpl());

    }

    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

}
