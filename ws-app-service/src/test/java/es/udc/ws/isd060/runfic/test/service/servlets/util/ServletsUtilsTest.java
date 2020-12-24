package es.udc.ws.isd060.runfic.test.service.servlets.util;

import es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils.CARRERA_SUBPATH;
import static es.udc.ws.isd060.runfic.service.restservice.servlets.util.ServletUtils.INSCRIPCION_SUBPATH;
import static org.junit.jupiter.api.Assertions.*;

public class ServletsUtilsTest {

    @BeforeAll
    public static void init() {
    }

    @Test
    public void testValidatePathValidPath(){


        ServletUtils.validateSubpath("1",1);

        ServletUtils.validateSubpath("1/",1);

        ServletUtils.validateSubpath("1/2",1);
        ServletUtils.validateSubpath("1/2",2);

        ServletUtils.validateSubpath("1/2/",1);
        ServletUtils.validateSubpath("1/2/",2);

        ServletUtils.validateSubpath("1/2/3",1);
        ServletUtils.validateSubpath("1/2/3",2);
        ServletUtils.validateSubpath("1/2/3",3);

        ServletUtils.validateSubpath("1/2/3/",1);
        ServletUtils.validateSubpath("1/2/3/",2);
        ServletUtils.validateSubpath("1/2/3/",3);

    }

/*
    @Test
    public void testValidatePathInvalidPath(){

        // TODO ARREGLAR [/,//,///,etc.] PERO NO ES NECESARIO PARA LA PRÁCTICA (CONFIRMADO POR PROFESOR)

        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(null,1);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(null,2);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(null,3);});

        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("",1);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("",2);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("",3);});

        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(" ",1);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(" ",2);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath(" ",3);});

        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("//",1);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("//",2);});
        assertThrows(RuntimeException.class, () -> { ServletUtils.validateSubpath("//",3);});

    }
*/
    @Test
    public void testDetermineSubpathTypePostInscripcion() {


        String randomCrap = "RandomCrapppppppppppppp";

        // POST http://XXX/ws-runfic-service/inscripcion
        // POST_SUBPATH_TYPE_ADDINSCRIPCION
      assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH)==
        ServletUtils.POST_SUBPATH_TYPE_ADDINSCRIPCION);

        // POST http://XXX/ws-runfic-service/inscripcion/
        // POST_SUBPATH_TYPE_ADDINSCRIPCION
      assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH+"/")==
                ServletUtils.POST_SUBPATH_TYPE_ADDINSCRIPCION);

        // POST http://XXX/ws-runfic-service/inscripcion/recogerdorsal
        // POST_SUBPATH_TYPE_RECOGERDORSAL
      assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH+"/"
        +ServletUtils.POST_SUBPATH_RECOGERDORSAL)==ServletUtils.POST_SUBPATH_TYPE_RECOGERDORSAL);

        // POST http://XXX/ws-runfic-service/inscripcion/recogerdorsal/
        // POST_SUBPATH_TYPE_RECOGERDORSAL
       assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH+"/"
                +ServletUtils.POST_SUBPATH_RECOGERDORSAL+"/")==ServletUtils.POST_SUBPATH_TYPE_RECOGERDORSAL);

        // POST http://XXX/ws-runfic-service/inscripcion/XXX
        // POST_SUBPATH_TYPE_NULL
        assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH+"/"+randomCrap)==
                ServletUtils.POST_SUBPATH_TYPE_NULL);

        // POST http://XXX/ws-runfic-service/inscripcion/recogerdorsal/XXX
        // POST_SUBPATH_TYPE_NULL
        assertTrue(ServletUtils.determineSubpathTypePostInscripcion("/"+ServletUtils.INSCRIPCION_SUBPATH+"/"
                +ServletUtils.POST_SUBPATH_RECOGERDORSAL+"/"+randomCrap)==ServletUtils.POST_SUBPATH_TYPE_NULL);

    }





    @Test
    public  void testIsDesiredPath (){
       String path1 = "";
       String path2 = "/";
       String path3 = "XD";
       String path10 = "/"+ CARRERA_SUBPATH; // CARRERA OK
       String path11 = "/carrer";
       String path12 = "/"+CARRERA_SUBPATH+"asas";
       String path13 =  CARRERA_SUBPATH;
       String path20 = "/"+INSCRIPCION_SUBPATH; // INSCRIPCION OK
       String path21 = "/inscrip";
       String path22 = "/"+INSCRIPCION_SUBPATH+"sss";
       String path23 =  INSCRIPCION_SUBPATH;

       assertFalse(ServletUtils.isDesiredPath(path1,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path1,INSCRIPCION_SUBPATH));

       assertFalse(ServletUtils.isDesiredPath(path2,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path2,INSCRIPCION_SUBPATH));

       assertFalse(ServletUtils.isDesiredPath(path3,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path3,INSCRIPCION_SUBPATH));

       // 10 -> CARRERA OK
       assertTrue(ServletUtils.isDesiredPath(path10,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path10,INSCRIPCION_SUBPATH));

       assertFalse(ServletUtils.isDesiredPath(path11,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path11,INSCRIPCION_SUBPATH));

       assertFalse(ServletUtils.isDesiredPath(path12,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path12,INSCRIPCION_SUBPATH));

       assertFalse(ServletUtils.isDesiredPath(path13,CARRERA_SUBPATH));
       assertFalse(ServletUtils.isDesiredPath(path13,INSCRIPCION_SUBPATH));

        // 20 -> INSCRIPCION OK
        assertFalse(ServletUtils.isDesiredPath(path20,CARRERA_SUBPATH));
        assertTrue(ServletUtils.isDesiredPath(path20,INSCRIPCION_SUBPATH));

        assertFalse(ServletUtils.isDesiredPath(path21,CARRERA_SUBPATH));
        assertFalse(ServletUtils.isDesiredPath(path21,INSCRIPCION_SUBPATH));

        assertFalse(ServletUtils.isDesiredPath(path22,CARRERA_SUBPATH));
        assertFalse(ServletUtils.isDesiredPath(path22,INSCRIPCION_SUBPATH));

        assertFalse(ServletUtils.isDesiredPath(path23,CARRERA_SUBPATH));
        assertFalse(ServletUtils.isDesiredPath(path23,INSCRIPCION_SUBPATH));


    }




}
