package es.udc.ws.isd060.runfic.model.RunFicServiceService;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class RunFicServiceServiceFactory {

    private final static String CLASS_NAME_PARAMETER = "MovieServiceFactory.className";
    private static RunFicServiceService service = null;

    private RunFicServiceServiceFactory (){

    }

    public static RunFicServiceService getInstance () {
        try {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (RunFicServiceService) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static RunFicServiceService getService() {

        if (service == null) {
            service = getInstance();
        }
        return service;
    }
}
