package es.udc.ws.isd060.runfic.client.ui;

import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.ClientRunFicServiceFactory;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class RunFicServiceClient {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientRunFicService clientRunFicService =
                ClientRunFicServiceFactory.getService();
        if("-a".equalsIgnoreCase(args[0])) {
            //**************************************************************************************************
            //****************************************** Yago *************************************************
            //**************************************************************************************************

            validateArgs(args, 7, new int[] {3, 5, 6});

            // [add] RunFicServiceClient -a String<ciudadCelebracion> String<descripcion> Float<precioInscripcion> LocalDateTime<fechaCelebracion> Integer<plazasDisponibles> Integer<plazasOcupadas>

            try {
                Long idCarrera = clientRunFicService.addCarrera(new ClientCarreraDto(null,
                        args[1], args[2], Float.valueOf(args[3]),
                        LocalDateTime.parse(args[4]), Integer.valueOf(args[5]), Integer.valueOf(args[6])));

                System.out.println("Carrera " + idCarrera + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

            //**************************************************************************************************
            //****************************************** Brais *************************************************
            //**************************************************************************************************
            //addInscripcion

        } else if("-r".equalsIgnoreCase(args[0])) {
            //**************************************************************************************************
            //****************************************** Yago *************************************************
            //**************************************************************************************************
            validateArgs(args, 2, new int[] {1});

            // [remove] RunFicServiceClient -r Long<idInscripcion>

            try {
                clientRunFicService.removeInscripcion(Long.parseLong(args[1]));

                System.out.println("Inscripcion with id " + args[1] +
                        " removed sucessfully");

            } catch (NumberFormatException | InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-u".equalsIgnoreCase(args[0])) {
            //UPDATE
        } else if("-f".equalsIgnoreCase(args[0])) {
            //**************************************************************************************************
            //****************************************** Yago *************************************************
            //**************************************************************************************************
            validateArgs(args, 3, new int[] {});

            // [find]-ByDateandCity RunFicServiceClient -f LocalDateTime<fechaCelebracion> String<ciudadCelebracion>

            try {
                List<ClientCarreraDto> carreras = clientRunFicService.findCarrera(LocalDateTime.parse(args[1]),args[2]);
                System.out.println("Found " + carreras.size() +
                        " carrera(s) with fechaCelebracion '" + args[1] + "' and ciudadCelebracion '"+args[2]+"'");
                for (int i = 0; i < carreras.size(); i++) {
                    ClientCarreraDto carreraDto = carreras.get(i);
                    System.out.println("Id: " + carreraDto.getIdCarrera() +
                            ", ciudadCelebracion: " + carreraDto.getCiudadCelebracion() +
                            ", descripcion: " + carreraDto.getDescripcion() +
                            ", precioInscripcion: " + carreraDto.getPrecioInscripcion() +
                            ", fechaCelebracion: " + carreraDto.getFechaCelebracion() +
                            ", plazasDisponibles: " + carreraDto.getPlazasDisponibles() +
                            ", plazasOcupadas: " + carreraDto.getPlazasOcupadas());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

            //**************************************************************************************************
            //****************************************** Brais *************************************************
            //**************************************************************************************************
            //findInscripcion
        }
        else if("-g".equalsIgnoreCase(args[0])) {
            //**************************************************************************************************
            //****************************************** Carlos *************************************************
            //**************************************************************************************************
        }

    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [add]RunFicServiceClient -a <ciudadCelebracion> <descripcion> <precioInscripcion> <fechaCelebracion> <plazasDisponibles> <plazasOcupadas>\n" +
                "    [remove] RunFicServiceClient -r <idInscripcion>\n" +
                "    [find]   RunFicServiceClient -f <fechaCelebracion> <ciudadCelebracion>\n");
    }

}
