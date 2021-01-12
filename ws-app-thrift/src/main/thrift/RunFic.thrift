namespace java es.udc.ws.isd060.runfic.thrift

struct ThriftInscripcionDto {
    1: i64 inscripcionId;
    2: i64 carreraId;
    3: string tarjeta;
    4: string email;
    5: i32 dorsal;
    6: bool recogido;
    7: string fechaInscripcion;
}

exception ThriftInputValidationException {
    1: string message
}
exception ThriftFueraDePlazo {
    1: string message
}
exception ThriftSinPlazas {
    1: string message
}
exception ThriftUsuarioInscrito {
    1: string message
}
exception ThriftCarreraInexistente {
    1: string message
}


service ThriftRunFicService {

   i64 addInscripcion(1: ThriftInscripcionDto inscripcionDto) throws (1: ThriftInputValidationException e, 2: ThriftFueraDePlazo ee,
        3: ThriftSinPlazas eee, 4: ThriftUsuarioInscrito eeee, 5: ThriftCarreraInexistente eeeee)

   list<ThriftInscripcionDto> findInscripcions(1: string email)

}