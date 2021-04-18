package com.example.proyecto_cine.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_cine.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        final TextView textView2 = root.findViewById(R.id.textView2);
        final TextView textView3 = root.findViewById(R.id.textView3);
        final TextView textView4 = root.findViewById(R.id.textView4);
        final TextView textView5 = root.findViewById(R.id.textView5);
        final TextView textView6 = root.findViewById(R.id.textView6);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("ACERCA DE NOSOTROS");
                textView2.setText("XXX es la empresa líder en exhibición cinematográfica en España con cerca de 22 millones de espectadores, y líder también en salas de exhibición con más de 500, repartidas en los más de 40 cines que tiene en las mejores localizaciones de las principales capitales de provincia, y sobre todo en Madrid y Barcelona. XXX apuesta por la tecnología y el confort a la hora de ofrecer a sus clientes la mejor experiencia cinematográfica, dos aspectos que hacen posible, entre otros, la instalación de asientos de lujo, pantallas inmersivas IMAX, ScreenX, Dolby Cinema e iSense exclusivas en España con la mejor calidad visual y de sonido, y la excelencia en la atención al cliente. \n" +
                        "\n" +
                        "XXX forma parte de Odeon & UCI Cinemas Group, que es el primer exhibidor cinematográfico del mercado con más de 115 millones de espectadores al año y 360 cines en 14 países de Europa. En 2016, el Grupo fue adquirido por AMC Theatres dando lugar a la mayor compañía de exhibición cinematográfica del mundo.");
                textView3.setText("CONDICIONES DE COMPRA");
                textView4.setText("XXX Cines informa a sus clientes que la empresa no se hace responsable del cumplimiento o resultado" +
                        "de transacciones, promociones y/o en general de operaciones realizadas a través de aplicaciones, sitios" +
                        "o plataformas no oficiales de XXX Cines. Cualquier transacción realizada por otras plataformas no" +
                        "autorizadas no serán válidas. Aplicaciones, sitios y plataformas oficiales: www.XXXcines.es y" +
                        "aplicaciones oficiales para Android. Para hacer válidos los precios aplicables a senior (mayores de" +
                        "60 años), jóvenes (con posesión de carnet joven o estudiante) y niños (con edades comprendidas entre" +
                        "los 3 y 13 años ambos incluidos) en cualquiera de estos casos deberán mostrar identificación que lo" +
                        "acredite. Los niños menores de 3 años no pagan si no ocupan butaca, salvo en la Sala Junior donde toda" +
                        "persona que esté en la sala deberá contar con entrada. El precio de miércoles no aplica en días festivos," +
                        "por lo que se aplicará el precio vigente más alto del fin de semana correspondiente a cada formato" +
                        "(funciones 2D, 3D). En días festivos se aplica el precio vigente más alto del fin de semana correspondiente" +
                        "a cada formato. El precio para sesiones especiales o eventos especiales, correspondiente a cada formato" +
                        "(funciones 2D, 3D) será el publicado y determinado por XXX Cines. En algunas películas no se aplican" +
                        "precios MovieYELMO ni precios promocionales, consultar títulos participantes en taquilla de tu cine. Todos" +
                        "los precios Incluyen Impuestos. \n" +
                        "Así mismo, YELMO CINES informa a sus clientes de que en cumplimiento de políticas internas, no se" +
                        "podrán realizar cambios ni devoluciones una vez finalizada la compra.\n" +
                        "YELMO CINES se reserva el derecho de variar, los precios de sus productos en función del horario, día" +
                        "de la semana y/o momento de la temporada, según considere.");
                textView5.setText("EXCLUSIÓN DE GARANTÍAS Y DE RESPONSABILIDAD");
                textView6.setText("El contenido del presente sitio web es de carácter general y tiene una finalidad meramente informativa, sin que se garantice plenamente el acceso a todos los contenidos, ni su exhaustividad, corrección, vigencia o actualidad, ni su idoneidad o utilidad para un objetivo específico.\n" +
                        "EL PROPIETARIO DE LA WEB excluye, hasta donde permite el ordenamiento jurídico, cualquier responsabilidad por los daños y perjuicios de toda naturaleza derivados de:\n" +
                        "a) La imposibilidad de acceso al sitio web o la falta de veracidad, exactitud, exhaustividad y/o actualidad de los contenidos, así como la existencia de vicios y defectos de toda clase de los contenidos transmitidos, difundidos, almacenados, puestos a disposición, a los que se haya accedido a través del sitio web o de los servicios que se ofrecen.\n" +
                        "b) La presencia de virus o de otros elementos en los contenidos que puedan producir alteraciones en los sistemas informáticos, documentos electrónicos o datos de los usuarios.\n" +
                        "c) El incumplimiento de las leyes, la buena fe, el orden público, los usos del tráfico y el presente aviso legal como consecuencia del uso incorrecto del sitio web. En particular, y a modo ejemplificativo, EL PROPIETARIO DE LA WEB no se hace responsable de las actuaciones de terceros que vulneren derechos de propiedad intelectual e industrial, secretos empresariales, derechos al honor, a la intimidad personal y familiar y a la propia imagen, así como la normativa en materia de competencia desleal y publicidad ilícita.\n" +
                        "Asimismo, EL PROPIETARIO DE LA WEB declina cualquier responsabilidad respecto a la información que se halle fuera de esta web y cuyo contenido no sea gestionada directamente por nuestro webmaster. La función de los links que aparecen en esta web es la de informar al usuario sobre la existencia de otras fuentes susceptibles de ampliar los contenidos que ofrece este sitio web y facilitar la compra de entradas. EL PROPIETARIO DE LA WEB no garantiza ni se responsabiliza del funcionamiento o accesibilidad de los sitios enlazados, por lo que tampoco será responsable del resultado obtenido. EL PROPIETARIO DE LA WEB no se responsabiliza del establecimiento de hipervínculos por parte de terceros.");
            }
        });
        return root;
    }
}