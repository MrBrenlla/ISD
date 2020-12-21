package es.udc.isd060.runfic.model.RunFicService;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class RunFicServiceFactory {

    private final static String CLASS_NAME_PARAMETER = "RunFicServiceFactory.className";
    private static RunFicService service = null;

    private RunFicServiceFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static RunFicService getInstance() {
        try {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (RunFicService) serviceClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized static RunFicService getService() {

        if (service == null) {
            service = getInstance();
        }
        return service;

    }
}