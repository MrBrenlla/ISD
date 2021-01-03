package es.udc.ws.isd060.runfic.client.ui;

import es.udc.ws.isd060.runfic.client.service.ClientRunFicService;
import es.udc.ws.isd060.runfic.client.service.ClientRunFicServiceFactory;
import es.udc.ws.isd060.runfic.client.service.dto.ClientCarreraDto;
import es.udc.ws.isd060.runfic.client.service.dto.ClientInscripcionDto;
import es.udc.ws.isd060.runfic.client.responses.ConsoleOutput;
import es.udc.ws.isd060.runfic.client.responses.InvalidArgumentException;
import es.udc.ws.isd060.runfic.client.responses.OperationalErrorException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.CarreraYaCelebradaException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.DorsalHaSidoRecogidoException;
import es.udc.ws.isd060.runfic.model.RunFicService.exceptions.NumTarjetaIncorrectoException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;

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
            } else if ("-fl".equalsIgnoreCase(args[0])) {
                //**************************************************************************************************
                //****************************************** Yago *************************************************
                //**************************************************************************************************

                validateArgs(args, 3, new int[]{});

                // [find-ByDateandCity] RunFicServiceClient -fl LocalDateTime<fechaCelebracion> String<ciudadCelebracion>

                try {
                    if(args[2] == null || args[2].trim().isEmpty()) //Mandatory CiudadCelebracion
                    {
                        try {
                            throw new InputValidationException("CiudadCelebración cannot be null");
                        } catch (InputValidationException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
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
                    ex.printStackTrace();
                }


                //**************************************************************************************************
                //****************************************** Carlos *************************************************
                //**************************************************************************************************
            } else if ("-fr".equalsIgnoreCase(args[0])) {
                // -findRace <raceId>
                // CF : public Carrera findCarrera(Long idCarrera) throws InstanceNotFoundException;
                //validateArgs(args, 1, new int[]{});
                try {
                    executeFindRace(args, clientRunFicService);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ("-R".equalsIgnoreCase(args[0])) {
                //  -deliverNumber <id|code> <creditCardNumber>
                // CF : public Inscripcion recogerDorsal ( Integer codReserva , String numTarjeta );
                //validateArgs(args, 2, new int[]{});
                try {
                    executeDeliverNumber(args, clientRunFicService);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    //**************************************************************************************************
    //****************************************** Carlos *************************************************
    //**************************************************************************************************

    private final static long NULL_ID = -1;

    private static void executeDeliverNumber(String[] args , ClientRunFicService clientRunFicService)
            throws InputValidationException, InstanceNotFoundException, CarreraYaCelebradaException, DorsalHaSidoRecogidoException, NumTarjetaIncorrectoException {
        long codReserva = RunFicServiceClient.NULL_ID ;
        String numTarjeta ;

        try {
            // Intentamos parsear el código de la reserva
            codReserva = Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            printUsageAndExit();
        }

        try {
            // Intentamos obtener el Numero de la tarjeta
            numTarjeta=validateNumTarjeta(args[2]);
        } catch ( Exception e){
            throw e;
        }

        try {
            // Intentamos llamar a recogerDorsal
            ClientInscripcionDto inscripcionDto = clientRunFicService.recogerDorsal(codReserva, numTarjeta);
            System.out.println("El dorsal "+inscripcionDto.getDorsal()+" ha sido recogido\n");
        }  catch (InstanceNotFoundException | InputValidationException | CarreraYaCelebradaException
                | DorsalHaSidoRecogidoException | NumTarjetaIncorrectoException e) {
            throw e;
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void executeFindRace(String[] args, ClientRunFicService clientRunFicService)
            throws  InstanceNotFoundException {
        long idCarrera = RunFicServiceClient.NULL_ID ;

        // TODO DEVOLVER PLAZAS LIBRES
        // TODO INSTANCE NOT FOUND
        try {
            // Intentamos parsear el id de la carrera
            idCarrera = Long.valueOf(args[1]);
        } catch (NumberFormatException e) {
            printUsageAndExit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // Intentamos llamar a recogerDorsal
            ClientCarreraDto carrera = clientRunFicService.findCarrera(idCarrera);
            System.out.println("Plazas Disponibles:"+carrera.getPlazasLibres()+
                    " para la carrera con id : "+carrera.getIdCarrera()+"\n");
        }  catch (InstanceNotFoundException e) {
            throw e;
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
                "    [find]   RunFicServiceClient -fl <fechaCelebracion> <ciudadCelebracion>\n)" +
                " [findRace] RunFicServiceClient -fr <raceId>\n "+
                " [deliverNumber] RunFicServiceClient -R <id|code> <creditCardNumber>\n ");
    }

}
