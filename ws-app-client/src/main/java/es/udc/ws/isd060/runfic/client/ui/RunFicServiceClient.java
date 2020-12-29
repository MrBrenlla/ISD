package es.udc.ws.isd060.runfic.client.ui;

import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.ClientRunFicServiceFactory;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.client.responses.ConsoleOutput;
import es.udc.ws.isd060.runfic.client.responses.InvalidArgumentException;
import es.udc.ws.isd060.runfic.client.responses.OperationalErrorException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class RunFicServiceClient {

    public static void  main(String[] args) {

            if (args.length == 0) {
                printUsageAndExit();
            }
            ClientRunFicService clientRunFicService =
                    ClientRunFicServiceFactory.getService();
            if ("-a".equalsIgnoreCase(args[0])) {
                //**************************************************************************************************
                //****************************************** Yago *************************************************
                //**************************************************************************************************

                validateArgs(args, 6, new int[] {3, 5});

                // [add] RunFicServiceClient -a String<ciudadCelebracion> String<descripcion> Float<precioInscripcion> LocalDateTime<fechaCelebracion> Integer<plazasDisponibles>
                // [add] RunFicServiceClient -a String<ciudadCelebracion> String<descripcion> Float<precioInscripcion> LocalDateTime<fechaCelebracion> Integer<plazasDisponibles>

                try {
                    Long idCarrera = clientRunFicService.addCarrera(new ClientCarreraDto(null,
                            args[1], args[2], Float.valueOf(args[3]),
                            LocalDateTime.parse(args[4]), Integer.valueOf(args[5])));//0

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
            } else if ("-i".equalsIgnoreCase(args[0])) {
                validateArgs(args, 4, new int[]{1});

                // [inscribirse] RunFicServiceClient -i Long<IdCarrera> String<email> String<tarjeta>

                try {
                    Long idIncripcion = clientRunFicService.addInscripcion(new ClientInscripcionDto(null, null, Long.valueOf(args[1]), args[2], args[3], null, false));
                    System.out.println("Inscripcion " + idIncripcion + " created sucessfully");

                } catch (NumberFormatException | InputValidationException ex) {
                    ex.printStackTrace(System.err);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
                //**************************************************************************************************
                //****************************************** Yago *************************************************
                //**************************************************************************************************
            } else if ("-r".equalsIgnoreCase(args[0])) {

                validateArgs(args, 2, new int[]{1});

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
                //**************************************************************************************************
                //****************************************** Yago *************************************************
                //**************************************************************************************************
            } else if ("-f".equalsIgnoreCase(args[0])) {

                validateArgs(args, 3, new int[]{});

                // [find-ByDateandCity] RunFicServiceClient -f LocalDateTime<fechaCelebracion> String<ciudadCelebracion>

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
                                ", plazasDisponibles: " + carreraDto.getPlazasDisponibles()+
                                ", plazasLibres: " + carreraDto.getPlazasLibres());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }

                //**************************************************************************************************
                //****************************************** Brais *************************************************
                //**************************************************************************************************
                //findInscripcion

            } else if ("-fi".equalsIgnoreCase(args[0])) {

                validateArgs(args, 2, new int[]{});

                // [findInscripcion] RunFicServiceClient -fi String<email>

                try {
                    List<ClientInscripcionDto> ins = clientRunFicService.findIscripcion(args[1]);
                    System.out.println("Found " + ins.size() +
                            " Inscriptions(s) with email '" + args[1] + "'");
                    for (int i = 0; i < ins.size(); i++) {
                        ClientInscripcionDto inscripcionDto = ins.get(i);
                        System.out.println("Id: " + inscripcionDto.getIdInscripcion() +
                                ", IdCarrera: " + inscripcionDto.getIdCarrera() +
                                ", Dorsal: " + inscripcionDto.getDorsal() +
                                ", Tarjrta: " + inscripcionDto.getTarjeta() +
                                ", fecha de inscripción: " + inscripcionDto.getFechaInscripcion() +
                                ", Recogido: " + inscripcionDto.isRecogido());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }


                //**************************************************************************************************
                //****************************************** Carlos *************************************************
                //**************************************************************************************************
            } else if ("-fr".equalsIgnoreCase(args[0])) {
                // -findRace <raceId>
                // CF : public Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException;
                validateArgs(args, 1, new int[]{});
                try {
                    executeFindRace(args, clientRunFicService);
                } catch (OperationalErrorException | NumberFormatException e) {
                    ((ConsoleOutput) e).consoleOutput();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if ("-R".equalsIgnoreCase(args[0])) {
                //  -deliverNumber <id|code> <creditCardNumber>
                // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
                validateArgs(args, 2, new int[]{});
                try {
                    executeDeliverNumber(args, clientRunFicService);
                } catch (OperationalErrorException | NumberFormatException e) {
                    ((ConsoleOutput) e).consoleOutput();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // TODO Mejorar respuesta ( No es necesario para la práctica)
        System.out.println("    OPERACION REALIZADA : "+args[0]+"\n");

        }

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    private static void executeDeliverNumber(String[] args , ClientRunFicService clientRunFicService)
            throws InvalidArgumentException, OperationalErrorException {
        long codReserva ;
        String numTarjeta ;

        try {
            // Intentamos parsear el código de la reserva
            codReserva = Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("codReserva", args[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Intentamos obtener el Numero de la tarjeta
            numTarjeta=validateNumTarjeta(args[2]);
        } catch ( InputValidationException e){
            throw new InvalidArgumentException("numTarjeta",args[2]);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Intentamos llamar a recogerDorsal
            clientRunFicService.recogerDorsal(codReserva, numTarjeta);
        }  catch (InputValidationException | OperationalErrorException e) {
            throw new OperationalErrorException(e,args);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void executeFindRace(String[] args, ClientRunFicService clientRunFicService)
            throws InvalidArgumentException, OperationalErrorException {
        long idCarrera ;

        try {
            // Intentamos parsear el id de la carrera
            idCarrera = Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("idCarrera", args[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Intentamos llamar a recogerDorsal
            clientRunFicService.findCarrera(idCarrera);
        }  catch (InstanceNotFoundException e) {
            throw new OperationalErrorException(e,args);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

    }



    //**************************************************************************************************
    //**************************************************************************************************
    //**************************************************************************************************

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

    public static  String  validateNumTarjeta (String numTarjeta) throws InputValidationException {
        if (numTarjeta.length() != 16) throw new InputValidationException("Tarxeta erronea");
        else return numTarjeta;
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        // TODO ARREGLAR USAGE
        System.err.println("Usage:\n" +
                "    [add]RunFicServiceClient -a <ciudadCelebracion> <descripcion> <precioInscripcion> <fechaCelebracion> <plazasDisponibles>\n" +
                "    [remove] RunFicServiceClient -r <idInscripcion>\n" +
                "    [find]   RunFicServiceClient -f <fechaCelebracion> <ciudadCelebracion>\n)" +
                " [findRace] RunFicServiceClient -fr <raceId>\n "+
                " [deliverNumber] RunFicServiceClient -R <id|code> <creditCardNumber>\n ");
    }

}
